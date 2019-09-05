package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.domain.payex.response.OrderResponse;
import com.payex.vas.demo.repository.OrderRepository;
import com.payex.vas.demo.repository.external.VasMultiPayApiRepository;
import com.payex.vas.demo.util.JsonUtil;
import com.payex.vas.demo.util.error.BadRequestException;
import com.payex.vas.demo.util.error.NotFoundException;
import com.payex.vas.demo.util.helper.OrderConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiPayService {
    private final OrderRepository orderRepository;
    private final VasMultiPayApiRepository vasMultiPayApiRepository;

    public OrderResponse createOrder(OrderRequest request, String agreementMerchantId) {
        request.setPaymentTransmissionDateTime(OffsetDateTime.now());
        request.setPaymentExpireDateTime(OffsetDateTime.now().plusDays(2L));
        var multiPayOrder = OrderConverter.buildOrder(request);
        orderRepository.save(multiPayOrder);
        return vasMultiPayApiRepository.createOrder(request, agreementMerchantId);
    }

    public OrderRequest getOrder(Long orderId, String agreementMerchantId) {
        // return vasMultiPayApiRepository.getOrder(orderId, agreementMerchantId); currently only saving orders locally so we return the request
        throwExceptionIfOrderNotExist(orderId);
        var order = orderRepository.getOne(orderId);
        return OrderConverter.convertToRequest(order);

    }

    public OrderResponse cancelOrder(Long orderId, String agreementMerchantId) {
        //var order = vasMultiPayApiRepository.cancelOrder(orderId, agreementMerchantId);
        orderRepository.deleteById(orderId);
        return null;
    }

    public List<OrderRequest> listOrder() { // Returns request because we just dump the request into local DB and use that to populate frontend views.
        var orderList = orderRepository.findAll();
        List<OrderRequest> requestList = new ArrayList<>();
        for (var order: orderList) {
            var response = JsonUtil.mapToObject(order.getData(), OrderRequest.class);
            if (response == null) response = new OrderRequest();
            response.setOrderId(order.getId().toString());
            requestList.add(response);
        }
        return requestList;
    }

    private void throwExceptionIfOrderNotExist(Long oid) {
        if (oid == null)
            throw new BadRequestException("Order id was null");
        if (!orderRepository.existsById(oid))
            throw new NotFoundException("Order not found for id: " + oid);
    }
}

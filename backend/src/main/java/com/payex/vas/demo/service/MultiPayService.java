package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.domain.entities.PaymentInstrument;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Merchant;
import com.payex.vas.demo.domain.payex.request.*;
import com.payex.vas.demo.domain.payex.response.OrderResponse;
import com.payex.vas.demo.domain.payex.response.PaymentAccountResponse;
import com.payex.vas.demo.repository.OrderRepository;
import com.payex.vas.demo.repository.PaymentInstrumentRepository;
import com.payex.vas.demo.repository.PaymentOperationRepository;
import com.payex.vas.demo.repository.SimulatedMerchantRepository;
import com.payex.vas.demo.repository.external.VasMultiPayApiRepository;
import com.payex.vas.demo.service.helper.GenericPaymentResponseBuilder;
import com.payex.vas.demo.service.helper.PaymentOperationBuilder;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiPayService {

    private final PaymentOperationRepository paymentOperationRepository;
    private final PaymentInstrumentRepository paymentInstrumentRepository;
    private final SimulatedMerchantRepository simulatedMerchantRepository;
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
        var order = vasMultiPayApiRepository.cancelOrder(orderId, agreementMerchantId);
        orderRepository.deleteById(orderId);
        return order;
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

    public PaymentAccountResponse balance(PaymentInstrument paymentInstrument, String agreementId) {

        var balanceRequest = BalanceRequest.builder()
            .accountIdentifier(buildAccountIdentifier(paymentInstrument))
            .build();

        return vasMultiPayApiRepository.balance(balanceRequest, agreementId);
    }

    public GenericPaymentResponse deposit(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasMultiPayApiRepository.deposit(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Deposit", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse credit(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasMultiPayApiRepository.credit(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Credit", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse authorize(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasMultiPayApiRepository.authorize(paymentRequest,paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Authorize", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse purchase(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasMultiPayApiRepository.purchase(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Purchase", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse capture(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var transactionRequest = buildTransactionRequest("Capture", orgPaymentOperation);
        var paymentResponse = vasMultiPayApiRepository.capture(transactionRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Capture", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse reversal(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var transactionRequest = buildTransactionRequest("Reversal", orgPaymentOperation);
        var paymentResponse = vasMultiPayApiRepository.reversal(transactionRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Reversal", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        updateStateOnOriginalPaymentOperation(orgPaymentOperation, "REVERSED");

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse cancel(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var operationRequest = buildOperationRequest(orgPaymentOperation, merchant);
        var paymentResponse = vasMultiPayApiRepository.cancel(operationRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Cancel", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        updateStateOnOriginalPaymentOperation(orgPaymentOperation, "CANCELED");

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    private void updateStateOnOriginalPaymentOperation(PaymentOperation orgPaymentOperation, String state) {
        orgPaymentOperation.setState(state);
        paymentOperationRepository.save(orgPaymentOperation);
    }

    public List<GenericPaymentResponse> listPaymentsForPaymentInstrument(Long paymentInstrumentId) {
        var paymentList = paymentOperationRepository.findTop100ByPaymentInstrumentIdOrderByCreatedDesc(paymentInstrumentId);
        return paymentList.stream()
            .map(GenericPaymentResponseBuilder::convertToGenericPaymentResponse)
            .collect(Collectors.toList());
    }


    private void throwExceptionIfOrderNotExist(Long oid) {
        if (oid == null)
            throw new BadRequestException("Order id was null");
        if (!orderRepository.existsById(oid))
            throw new NotFoundException("Order not found for id: " + oid);
    }
    private SimulatedMerchant findMerchant(Long merchantId) {
        return simulatedMerchantRepository.findById(merchantId).orElseThrow(() -> new BadRequestException("Unable to find Merchant by id: " + merchantId));
    }

    private PaymentInstrument findPaymentInstrument(Long paymentInstrumentId) {
        return paymentInstrumentRepository.findById(paymentInstrumentId).orElseThrow(() -> new BadRequestException("Unable to find PaymentInstrument by id: " + paymentInstrumentId));
    }

    private PaymentOperation findPaymentOperation(Long findPaymentOperation) {
        return paymentOperationRepository.findById(findPaymentOperation).orElseThrow(() -> new BadRequestException("Unable to find PaymentOperation by id: " + findPaymentOperation));
    }

    private TransactionRequest buildTransactionRequest(String operation, PaymentOperation orgRequest) {
        return TransactionRequest.builder()
            .amount(orgRequest.getAmount())
            .description(String.format("%s of %s with id: '%d'", operation, orgRequest.getTransactionType(), orgRequest.getId()))
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .build();
    }

    private OperationRequest buildOperationRequest(PaymentOperation orgRequest, SimulatedMerchant merchant) {
        return OperationRequest.builder()
            .description(String.format("Cancel of %s with id: '%d'", orgRequest.getTransactionType(), orgRequest.getId()))
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .build();
    }

    private PaymentRequest buildPaymentRequest(GenericPaymentRequest request, PaymentInstrument paymentInstrument, SimulatedMerchant merchant) {
        return PaymentRequest.builder()
            .amount(request.getAmount())
            .description(request.getDescription())
            .currency(merchant.getCurrencyIso().getValue())
            .paymentOrderRef(UUID.randomUUID().toString())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .accountIdentifier(buildAccountIdentifier(paymentInstrument))
            .build();
    }

    private AccountIdentifier buildAccountIdentifier(PaymentInstrument paymentInstrument) {
        return AccountIdentifier.builder()
            .accountId(paymentInstrument.getExternalAccountId())
            .accountKey(paymentInstrument.getPan())
            .cvc(paymentInstrument.getCvc())
            .build();
    }
}

package com.payex.vas.demo.util.helper;

import com.payex.vas.demo.domain.entities.Order;
import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.util.JsonUtil;

public class OrderConverter {
    public static Order buildOrder(OrderRequest orderRequest){
        Order multiPayOrder = new Order();
        multiPayOrder.setData(JsonUtil.mapToString(orderRequest));


        return multiPayOrder;
    }

    public static OrderRequest convertToRequest(Order order) {
        var request = JsonUtil.mapToObject(order.getData(), OrderRequest.class);
        if (request == null ) // shouldn't happen
            request = new OrderRequest();
        request.setOrderId(order.getId().toString());
        return request;
    }
}

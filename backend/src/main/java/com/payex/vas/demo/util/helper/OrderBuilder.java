package com.payex.vas.demo.util.helper;

import com.payex.vas.demo.domain.entities.MultipayOrder;
import com.payex.vas.demo.domain.payex.request.OrderRequest;
import com.payex.vas.demo.util.JsonUtil;

public class OrderBuilder {
    public static MultipayOrder buildOrder(OrderRequest orderRequest){
        MultipayOrder multiPayOrder = new MultipayOrder();
        multiPayOrder.setData(JsonUtil.mapToString(orderRequest));


        return multiPayOrder;
    }
}

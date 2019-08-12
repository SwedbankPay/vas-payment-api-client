package com.payex.vas.demo.domain.payex.request;

import com.payex.vas.demo.domain.entities.Product;
import com.payex.vas.demo.domain.payex.base.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends AbstractRequest {
    private String orderId;
    private String currency;
    private Long amount;
    private String description;
    private String orderRef;
    private String paymentTransactionRef;
    private String paymentContractId;
    private Boolean repeat;
    private OffsetDateTime paymentExpireDateTime;
    private OffsetDateTime paymentTransmissionDateTime;
    private BigDecimal preliminaryInvoiceFee;
    private BigDecimal preliminaryInvoiceFeeTax;
    private PaymentMethods paymentMethod;
    private Merchant merchant;
    private ShippingInformation shippingInformation;
    private PrivateCustomerIdentifier privateCustomerIdentifier;
    private CorporateCustomerIdentifier corporateCustomerIdentifier;
    private List<Product> products;
}

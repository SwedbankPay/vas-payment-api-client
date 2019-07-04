package com.payex.vas.demo.domain.payex.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payex.vas.demo.domain.payex.base.*;
import com.payex.vas.demo.util.PaymentMethods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends AbstractRequest {

    @ApiModelProperty(
        name = "orderId",
        notes = "OrderRequest ID, created by PayEx when order is created"
    )
    private String orderId;

    @ApiModelProperty(
        name = "currency",
        notes = "Currency for Payment",
        example = "NOK",
        required = true)
    private String currency;

    @ApiModelProperty(
        name = "amount",
        notes = "Total amount of Payment (in cents), ie. 100Kr -> 10000",
        example = "10000",
        required = true)
    private Long amount;

    @ApiModelProperty(
        name = "description",
        notes = "Payment description",
        allowEmptyValue = true)
    private String description;

    @ApiModelProperty(
        name = "orderRef",
        required = true,
        notes = "Unique ID to bind 2-phase transactions",
        example = "UUID")
    private String orderRef;

    @ApiModelProperty(
        name = "paymentTransactionRef",
        notes = "EDI:referenceID",
        example = "UUID",
        required = true
    )
    private String paymentTransactionRef;

    @ApiModelProperty(
        name = "paymentContractId",
        notes = "EDI:ContractId",
        example = "UUID",
        required = true
    )
    private String paymentContractId;

    @ApiModelProperty(
        name = "repeat",
        notes = "Notifies this is a repeat message",
        allowEmptyValue = true)
    private Boolean repeat;

    @ApiModelProperty(
        name = "paymentExpireDateTime",
        notes = "format yyyy-MM-ddTHH:mm:ss default 48 timer"
    )
    private ZonedDateTime paymentExpireDateTime;

    @ApiModelProperty(
        name = "paymentTransmissionDateTime",
        notes = "When the payment was initiated at terminal/ECR."
    )
    private ZonedDateTime paymentTransmissionDateTime;

    @ApiModelProperty(
        name = "preliminaryInvoiceFee",
        notes = "decimal"
    )
    private BigDecimal preliminaryInvoiceFee;

    @ApiModelProperty(
        name = "preliminaryInvoiceFeeTax",
        notes = "decimal"
    )
    private BigDecimal preliminaryInvoiceFeeTax;

    @ApiModelProperty(
        name = "paymentMethod",
        notes = "payment method [ALL, INVOICE, ONLINE]"
    )
    private PaymentMethods paymentMethods;

    @JsonProperty(value = "merchant", required = true)
    private Merchant merchant;

    @JsonProperty(value = "shippingInformation")
    private ShippingInformation shippingInformation;

    @JsonProperty(value = "privateCustomerIdentifier")
    private PrivateCustomerIdentifier privateCustomerIdentifier;

    @JsonProperty(value = "corporateCustomerIdentifier")
    private CorporateCustomerIdentifier corporateCustomerIdentifier;

    @JsonProperty(value = "products")
    private List<Product> products;
}

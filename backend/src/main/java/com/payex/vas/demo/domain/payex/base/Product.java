package com.payex.vas.demo.domain.payex.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class Product {

    @ApiModelProperty(
        name = "productOrderId",
        required = true
    )
    private Long productOrderId;

    @ApiModelProperty(
        name = "productId",
        notes = "Used to identify a product",
        example = "001",
        required = true)
    private String productId;

    @ApiModelProperty(
        name = "description",
        notes = "Optional product description",
        example = "1x banana",
        allowEmptyValue = true)
    private String description;

    @ApiModelProperty(
        name = "quantity",
        notes = "Number of product units sold (both integer and decimal numbers supported)",
        example = "13.37",
        required = true)
    private BigDecimal quantity;

    @ApiModelProperty(
        name = "amount",
        notes = "Monetary value of purchased product (in cents)",
        example = "1337",
        required = true)
    private Long amount;

    @ApiModelProperty(notes = "Type of measurement, L=Litre, U=Unit, G=Grams This may refer to the number of packs, number of bottles etc., O=present, this denotes that there is no measurement.",
        name = "unitOfMeasure",
        example = "L",
        allowableValues = "L,U,g,O",
        required = true)
    private String unitOfMeasure;

    @ApiModelProperty(
        name = "vatAmount",
        notes = "Monetary value of vat-amount for purchased product (in cents)",
        example = "337",
        allowEmptyValue = true)
    private Long vatAmount;

    @Min(0)
    @Max(25)
    @ApiModelProperty(
        name = "vatRate",
        notes = "Vat-rate for purchased product (both integer and decimal numbers supported)",
        example = "25.0",
        allowEmptyValue = true)
    private BigDecimal vatRate;

    @ApiModelProperty(
        name = "pdfContent",
        allowEmptyValue = true
    )
    private byte[] pdfContent;

}

CREATE TABLE payment_instrument
(
    id                  BIGSERIAL,
    type                varchar(255) not null,
    external_account_id varchar(255) not null UNIQUE,
    pan                 varchar(19)  not null,
    cvc                 varchar(3)   not null,
    PRIMARY KEY (id)
);


CREATE TABLE payment_operation
(
    id                      BIGSERIAL,
    payment_instrument_id   BIGINT       not null,
    merchant_id             BIGINT       not null,
    transaction_type        varchar(255) not null,
    state                   varchar(255) not null,
    amount                  BIGINT       not null,
    external_payment_id     varchar(255) not null,
    external_account_id     varchar(255) not null,
    payment_order_ref       varchar(255) not null,
    payment_transaction_ref varchar(255) not null,
    created                 TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    json_response           TEXT         not null,
    PRIMARY KEY (id)
);

CREATE TABLE merchant
(
    id            BIGSERIAL,
    merchant_name varchar(255) not null,
    agreement_id  varchar(255) not null,
    currency_iso  varchar(3)   not null,
    seller_name   varchar(255),
    seller_id     varchar(255),
    terminal_id   varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE multipay_products
(
    product_id        BIGSERIAL,
    product_order_id  BIGSERIAL not null,
    name              varchar(64) not null,
    amount            NUMERIC(19,2) not null,
    description       varchar(255),
    quantity          NUMERIC(19,2) not null,
    unit_of_measure   varchar(5) not null,
    vat_amount        NUMERIC(19,0) not null,
    vat_rate          NUMERIC(5,2) not null,
    PRIMARY KEY (product_id)
);

CREATE TABLE multipay_order
(
    id      BIGSERIAL,
    data    TEXT,
    PRIMARY KEY(id)
);





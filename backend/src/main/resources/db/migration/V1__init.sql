CREATE TABLE payment_account
(
    id                  BIGSERIAL,
    external_account_id varchar(255) not null UNIQUE,
    pan                 varchar(19)  not null,
    cvc                 varchar(3)   not null,
    PRIMARY KEY (id)
);


CREATE TABLE payment_operation
(
    id                      BIGSERIAL,
    payment_account_id      BIGINT       not null,
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
    PRIMARY KEY (id)
);


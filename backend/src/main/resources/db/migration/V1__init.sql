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
    product_order_id  BIGSERIAL UNIQUE not null,
    name              varchar(64) not null,
    amount            NUMERIC(19,2) not null,
    description       varchar(255),
    quantity          NUMERIC(19,2) not null,
    unit_of_measure   varchar(5) not null,
    vat_amount        NUMERIC(19,0) not null,
    vat_rate          NUMERIC(5,2) not null,
    PRIMARY KEY (product_id)
);

CREATE TABLE product_order_ref
(
    order_ref_id         BIGSERIAL,
    order_id             BIGSERIAL,
    product_order_id     BIGSERIAL REFERENCES multipay_products(product_order_id),
    PRIMARY KEY (order_ref_id)
);

CREATE TABLE shipping_information
(
    shipping_information_id     BIGSERIAL UNIQUE,
    shipping_addressee          varchar(255),
    shipping_street_address     varchar (255),
    shipping_co_address         varchar(255),
    shipping_postal_code        varchar(64),
    shipping_city               varchar(64),
    shipping_country_code       varchar(64),
    PRIMARY KEY (shipping_information_id)
);

CREATE TABLE address
(
    address_id                  BIGSERIAL,
    street_address              varchar(64),
    co_address                  varchar(64),
    city                        varchar(64),
    postal_code                 varchar(64),
    country_code                varchar(32),
    billing_street_addressee    varchar(64),
    billing_street_address      varchar(64),
    billing_co_address          varchar(64),
    billing_postal_code         varchar(32),
    billing_city                varchar(64),
    billing_country_code        varchar(32),
    PRIMARY KEY (address_id)
);

CREATE TABLE multipay_account_identifier
(
    multipay_account_identifier_id          BIGSERIAL,
    account_id                              varchar(64),
    address_id                              BIGSERIAL REFERENCES address(address_id),
    contact_email                           varchar(32),
    contact_phone_country_code              varchar(32),
    language_code                           varchar(32),
    contact_phone                           varchar(32),
    PRIMARY KEY (multipay_account_identifier_id)
);

CREATE TABLE private_customer_identifier
(
    private_customer_id             BIGSERIAL UNIQUE,
    customer_first_name             varchar(64),
    customer_last_name              varchar(64),
    ssn                             varchar(32),
    multipay_account_identifier_id  BIGSERIAL REFERENCES multipay_account_identifier(multipay_account_identifier_id),
    PRIMARY KEY (private_customer_id )
);

CREATE TABLE corporate_customer_identifier
(
    corporate_customer_id           BIGSERIAL UNIQUE,
    company_name                    varchar(64),
    contact_first_name              varchar(64),
    contact_last_name               varchar(64),
    vat_registration_number         varchar(32),
    multipay_account_identifier_id  BIGSERIAL REFERENCES multipay_account_identifier(multipay_account_identifier_id),
    PRIMARY KEY (corporate_customer_id)
);

CREATE TABLE multipay_order
(
    order_id                        BIGSERIAL,
    currency                        varchar(5) not null,
    amount                          BIGINT not null,
    orderref                        varchar(64),
    description                     varchar(255),
    payment_transaction_ref         varchar(64),
    payment_contract_id             varchar (64),
    payment_expire_date_time        timestamp,
    payment_transmission_date_time  timestamp,
    preliminary_invoice_fee         NUMERIC(19,2),
    preliminary_invoice_fee_tax     NUMERIC(19,2),
    payment_methods                 varchar(8),
    merchant_id                     BIGSERIAL REFERENCES merchant(id),
    shipping_information_id         BIGSERIAL REFERENCES shipping_information(shipping_information_id),
    private_customer_id             BIGSERIAL REFERENCES private_customer_identifier(private_customer_id),
    corporate_customer_id           BIGSERIAL REFERENCES corporate_customer_identifier(corporate_customer_id),
    product_id                      BIGSERIAL REFERENCES product_order_ref(order_ref_id),
    PRIMARY KEY (order_id)
);





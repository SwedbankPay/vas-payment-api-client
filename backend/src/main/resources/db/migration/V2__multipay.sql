CREATE TABLE product
(
    product_id        BIGSERIAL,
    product_order_id  BIGSERIAL,
    name              varchar(64) not null,
    amount            NUMERIC(19,2) not null,
    description       varchar(255),
    quantity          NUMERIC(19,2) not null,
    unit_of_measure   varchar(5) not null,
    vat_amount        NUMERIC(19,2) not null,
    vat_rate          NUMERIC(5,2) not null,
    PRIMARY KEY (product_id)
);

CREATE TABLE multipay_order
(
    id      BIGSERIAL,
    data    TEXT,
    PRIMARY KEY(id)
);


ALTER TABLE merchant
    ADD COLUMN seller_name varchar(255),
ADD COLUMN seller_id varchar(255),
ADD COLUMN terminal_id varchar(255);

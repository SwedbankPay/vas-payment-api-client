ALTER TABLE payment_instrument
    ADD COLUMN balance BIGINT default 0,
    ADD COLUMN last_balance_sync TIMESTAMP WITH TIME ZONE;

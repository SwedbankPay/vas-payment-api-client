-- Accounts
insert into payment_instrument (type, external_account_id, pan, cvc)
values ('PREPAID_CARD', 'PP-2053658', '6394570000011800092', '915');
insert into payment_instrument (type, external_account_id, pan, cvc)
values ('CREDIT_CARD', 'CC-324458', '7013369000000000000', '123');

-- Merchants
insert into merchant (merchant_name, agreement_id, currency_iso)
values ('Vas Payment API test merchant PP', 'SystemtestGC', 'NOK');
insert into merchant (merchant_name, agreement_id, currency_iso)
values ('Vas Payment API test merchant CC', 'SystemtestCC', 'NOK');

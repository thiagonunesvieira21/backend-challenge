DELETE FROM tb03_order_item;
DELETE FROM tb02_order;
INSERT INTO tb02_order(nu_order, nu_store, co_status, dh_confirmation,  no_street, co_zip_code, no_city, no_state) VALUES(100, 20, 'P', null, 'street1', '123456', 'city 1', 'go');
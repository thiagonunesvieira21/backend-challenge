drop table tb01_store if exists;
create table tb01_store (
    nu_store bigint not null,
    no_city varchar(255),
    no_store varchar(255),
    no_state varchar(255),
    no_street varchar(255),
    co_zip_code varchar(255),
    primary key (nu_store)
);

drop table tb02_order if exists;
create table tb02_order (
  	nu_order bigint not null,
   	nu_store bigint not null,
    co_status varchar(255),
    dh_confirmation TIMESTAMP,
    no_street varchar(255),
    co_zip_code varchar(255),
    no_city varchar(255),
    no_state varchar(255),
    primary key (nu_order)
);

DELETE FROM tb01_store;
DELETE FROM tb02_order;
DELETE FROM tb04_payment;
INSERT INTO tb01_store(nu_store, no_store, no_street, co_zip_code, no_city, no_state) VALUES(20, 'strore1', 'street1', '123456', 'city 1', 'go');

INSERT INTO tb02_order(nu_order, nu_store, co_status, dh_confirmation,  no_street, co_zip_code, no_city, no_state) VALUES(100, 20, 'P', null, 'street1', '123456', 'city 1', 'go');
INSERT INTO tb02_order(nu_order, nu_store, co_status, dh_confirmation,  no_street, co_zip_code, no_city, no_state) VALUES(101, 20, 'P', null, 'street1', '123456', 'city 1', 'go');

INSERT INTO tb04_payment(co_payment, co_credit_card, co_status, dh_payment, nu_order) VALUES('ABC123EFG', '012345678910111213AB', 'A', null, 101);

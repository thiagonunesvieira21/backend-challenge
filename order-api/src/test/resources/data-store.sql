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

INSERT INTO tb01_store(nu_store, no_store, no_street, co_zip_code, no_city, no_state) VALUES(20, 'strore1', 'street1', '123456', 'city 1', 'go');
create database countries;

use countries;
create table country(
	id INT auto_increment NOT NULL,
    country_name varchar(100),
    capital varchar(100),
    PRIMARY KEY(id)
);

drop table country;

select * from country;
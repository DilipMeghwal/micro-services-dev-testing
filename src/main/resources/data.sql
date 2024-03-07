drop table country;

create table country(
	id INT auto_increment NOT NULL,
    country_name varchar(100),
    capital varchar(100),
    PRIMARY KEY(id)
);

insert into country(id, country_name, capital) values(1, 'India', 'New Delhi');
insert into country(id, country_name, capital) values(2, 'UK', 'London');
insert into country(id, country_name, capital) values(3, 'Germany', 'Berlin');
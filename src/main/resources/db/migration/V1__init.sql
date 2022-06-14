-- create table products
-- (
--     id    bigserial primary key,
--     title varchar(255),
--     price int
-- );
-- insert into products (title, price)
-- values ('Bread', 25),
--        ('Milk', 80),
--        ('Cheese', 450);

create table categories
(
    id    bigserial primary key,
    title varchar(255)
);
insert into categories (title)
values ('Food');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    category_id bigint references categories (id)
);
insert into products (title, price, category_id)
values ('Батон нарезной', 56, 1),
       ('Сыр костромской', 567, 1),
       ('Свинина лопатка', 340, 1),
       ('Фарш домашний', 299, 1),
       ('Котлеты московские', 164, 1),
       ('Печенье овсяное', 120, 1),
       ('Яблоки сезонные', 89, 1),
       ('Булочка с творогом', 55, 1),
       ('Блинчики', 144, 1),
       ('Иогурт с вишней', 51, 1),
       ('Молоко 1л', 99, 1),
       ('Сметана 180г', 89, 1),
       ('Масло сладко-сливочное 180г', 198, 1),
       ('Кефир обезжиренный 400г', 65, 1),
       ('Творог 180г', 72, 1),
       ('Сушка с маком', 69, 1),
       ('Филе трески 400г', 405, 1),
       ('Клубника 250г', 250, 1),
       ('Апельсины', 169, 1),
       ('Лимоны 2 шт', 89, 1),
       ('Виноград', 259, 1);
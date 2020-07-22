SET FOREIGN_KEY_CHECKS = 0;

drop table user;
create table user (
    id int not null auto_increment,
    created_date datetime,
    full_name varchar(255),
    email varchar(255),
    password varchar(255),
    primary key (id)
);

drop table role;
create table role (
    id int not null auto_increment,
    created_date datetime,
    name varchar(127),
    primary key (id)
);

drop table user_role;
create table user_role (
  id int not null auto_increment,
  user_id int,
  role_id int,
  primary key (id)
);

drop table product_image ;
create table product_image (
    id int not null auto_increment,
    created_date datetime,
    url varchar(255),
    product_id int,
    primary key (id),
    foreign key (product_id) references product(id)
);

drop table product;
create table product (
    id int not null auto_increment,
    created_date datetime,
    created_by int,
    name varchar(100),
    description varchar(100),
    amount decimal(18, 2),
    quantity int,
    category_id int,
    primary key (id),
    foreign key (category_id) references category(id)
);

drop table category;
create table category (
    id int not null auto_increment,
    created_date datetime,
    name varchar(100),
    description varchar(100),
    primary key (id)
);


SET FOREIGN_KEY_CHECKS = 1;

delete user_role;
delete user;
delete role;

insert into user (id, created_date, full_name, email, password) values (1, '2020-07-12 18:43:05', 'Jane Doe', 'admin@admin', '$2a$10$io.64gQ.pIdpI0bTy79lx.GZrKDpkdKP5jbykkuxbTbJSUDSyOZt6');
insert into user (id, created_date, full_name, email, password) values (2, '2020-07-12 18:43:05', 'John Doe', 'test1@test', '$2a$10$io.64gQ.pIdpI0bTy79lx.GZrKDpkdKP5jbykkuxbTbJSUDSyOZt6');


insert into role (id, created_date, name) values (1, '2020-07-12 18:42:24', 'ADMIN');
insert into role (id, created_date, name) values (2, '2020-07-12 18:42:33', 'EDITOR');
insert into role (id, created_date, name) values (3, '2020-07-12 18:42:36', 'BASIC');

insert into user_role (id, user_id, role_id) values (1, 1, 1);
insert into user_role (id, user_id, role_id) values (2, 2, 3);

commit;
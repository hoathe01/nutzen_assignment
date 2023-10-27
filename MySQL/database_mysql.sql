# DROP DATABASE if exists nutzen;
CREATE DATABASE nutzen;
USE nutzen;

CREATE TABLE user
(
    id       int auto_increment,
    username varchar(100),
    email    varchar(255),
    password varchar(255),
    id_role  int,
    PRIMARY KEY (id)
);
CREATE TABLE role
(
    id   int auto_increment,
    name varchar(30),
    PRIMARY KEY (id)

);

ALTER TABLE user
    ADD CONSTRAINT fk_id_role_user FOREIGN KEY (id_role) references role (id);
INSERT INTO role (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

# password: 1234
INSERT INTO user (username, email, password, id_role)
VALUES ('ragna', 'ragna@gmail.com', '$2a$12$m785NDJBvMiaTmBJFwSDkuWJ6lZQ2LXwsmbelY5Y6aZlH/RaOqU7m', 1),
       ('jonh', 'john@gmail.com', '$2a$12$C7dc6d9Bxjb0LAemyXAd7erIXRWglIAW1o1dmnLuLsypyggK1HiNC', 2)

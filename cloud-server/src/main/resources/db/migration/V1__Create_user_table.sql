CREATE SEQUENCE users_id_seq;
CREATE TABLE users (
    user_id INT NOT NULL DEFAULT NEXTVAL('users_id_seq'),
    login VARCHAR(150) NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(500) NOT NULL
);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    telephone   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
    id              INT NOT NULL AUTO_INCREMENT,
    type            VARCHAR(255) NOT NULL,
    customer_id     INT NOT NULL,

    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
    id              INT NOT NULL AUTO_INCREMENT,
    account_id      INT NOT NULL,
    amount          INT,

    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS logs;
CREATE TABLE logs (
    info        VARCHAR(512) NOT NULL,
    timestmp    DATETIME DEFAULT NOW()
);
DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
    ID           VARCHAR(36) NOT NULL,
    NAME         VARCHAR(100) NOT NULL,
    EMAIL        VARCHAR(120) NOT NULL,
    PASSWORD     VARCHAR(100) NOT NULL,
    IS_ACTIVE    TINYINT NOT NULL,
    TOKEN        VARCHAR(250) NOT NULL,
    LAST_LOGIN   DATETIME DEFAULT CURRENT_TIMESTAMP(),
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    MODIFIED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (ID),
    UNIQUE KEY EMAIL_UNIQUE (EMAIL)
);

DROP TABLE IF EXISTS PHONE;

CREATE TABLE PHONE
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID      VARCHAR(36) NOT NULL,
    NUMBER       VARCHAR(15) NOT NULL,
    CITY_CODE    VARCHAR(6) NOT NULL,
    COUNTRY_CODE VARCHAR(6) NOT NULL,
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    MODIFIED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);
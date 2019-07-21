DROP DATABASE IF EXISTS dev_airline_reservation;
CREATE DATABASE dev_airline_reservation;

USE dev_airline_reservation; 

CREATE TABLE User (
    uID INT AUTO_INCREMENT,
    fname VARCHAR(30) NOT NULL,
    lname VARCHAR(50) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    birthDate DATE NOT NULL,
    age INT,
    isAdmin BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (uID)
); 

DROP TRIGGER IF EXISTS userAgeTrigger;

CREATE TRIGGER userAgeTrigger
BEFORE INSERT ON User
FOR EACH ROW
SET NEW.age = CURRENT_DATE - NEW.birthDate; 

INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Riley", "Chetwood", "rc@gmail.com", "pass123", "1993-7-02", true);
INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Luz", "Hernandez", "lh@gmail.com", "pass321", "1994-06-28", false);
INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Harry", "Potter", "hp@yahoo.com", "YerAWizard", "1980-07-31", false);
DROP DATABASE IF EXISTS dev_airline_reservation;
CREATE DATABASE dev_airline_reservation;

USE dev_airline_reservation; 

---------------------TABLES-------------------------

DROP TABLE IF EXISTS User;
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

DROP TABLE IF EXISTS Airline;
CREATE TABLE Airline (
	alID INT AUTO_INCREMENT,
	companyName VARCHAR(50) NOT NULL,
	hq VARCHAR(50) NOT NULL,
	founded DATE NOT NULL,
	icao CHAR(3) NOT NULL UNIQUE,
	PRIMARY KEY (alID)
);

DROP TABLE IF EXISTS Plane;
CREATE TABLE Plane (
	pID INT AUTO_INCREMENT,
	manufacturer VARCHAR(50) NOT NULL,
	model VARCHAR(50) NOT NULL UNIQUE,
	capacity SMALLINT NOT NULL,
	PRIMARY KEY (pID)
);

DROP TABLE IF EXISTS Fleet;
CREATE TABLE Fleet (
	alID INT,
	pID INT,
	numberInFleet SMALLINT NOT NULL DEFAULT 0,
	FOREIGN KEY (alID) REFERENCES Airline(alID) ON DELETE CASCADE, 
	FOREIGN KEY (pID) REFERENCES Plane(pID) ON DELETE CASCADE,
	PRIMARY KEY (alID, pID)
);

DROP TALBE IF EXIST Country;
CREATE TABLE Country (
	name VARCHAR(100) NOT NULL,
	alphaTwoCode CHAR(2) NOT NULL UNIQUE,
	PRIMARY KEY (name)
);

DROP TALBE IF EXISTS Airport;
CREATE TABLE Airport (
	apID INT AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	city VARCHAR(50) NOT NULL,
	country VARCHAR(100) NOT NULL,
	iata CHAR(3),
	icao CHAR(4),
	lat DECIMAL(9, 6) NOT NULL,
	lng DECIMAL(9, 6) NOT NULL,
	altitude INT,
	tzOffSet TINYINT NOT NULL,
	dst CHAR(1),
	tz VARCHAR(50),
	type CHAR(7) NOT NULL,
	source CHAR(11) NOT NULL,
	FOREIGN KEY (country) REFERENCES Country(name),
	PRIMARY KEY (apID)
);

---------------------TRIGGERS-------------------------

DROP TRIGGER IF EXISTS userAgeTrigger;
CREATE TRIGGER userAgeTrigger
BEFORE INSERT ON User
FOR EACH ROW
SET NEW.age = CURRENT_DATE - NEW.birthDate; 

---------------------PROCEDURES-------------------------

---------------------INSERTS-------------------------

INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Riley", "Chetwood", "rc@gmail.com", "pass123", "1993-7-02", true);
INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Luz", "Hernandez", "lh@gmail.com", "pass321", "1994-06-28", false);
INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Harry", "Potter", "hp@yahoo.com", "YerAWizard", "1980-07-31", false);
INSERT INTO USER (fname, lname, email, password, birthDate, isAdmin) VALUES ("Frodo", "Baggins", "fb@yahoo.com", "HobbitsToIsengrad", "1929-07-31", false);

INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Alaska Airlines", "ASA", "Seatle", "1932-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Allegiant Air", "AAY", " Las Vegas", "1997-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("American Airlines", "AAL", "Dallas", "1926-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Delta Airlines", "DAL", "Atlanta", "1924-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Frontier Airlines", "FFT", "Denver", "1994-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Hawaiian Airlines", "HAL", "Honolulu", "1929-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("JetBlue Airways", "JBU", "New York", "1998-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Southwest Airlines", "SWA", "Dallas", "1967-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Spirit Airlines", "NKS", "Fort Lauderdale", "1980-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("Sun Country Airlines", "SCX", "St. Paul", "1982-01-01");
INSERT INTO Airline (companyName, icao, hq, founded) VALUES ("United Airlines", "UAL", "Chicago", "1926-01-01");

INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Airbus", "A220", 141);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Airbus", "A320", 206);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Airbus", "A330", 287);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Airbus", "A350", 366);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Airbus", "A380", 544);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Boeing", "737 NG", 188);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Boeing", "737 MAX", 188);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Boeing", "747-8", 410);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Boeing", "777", 400);
INSERT INTO Plane (manufacturer, model, capacity) VALUES ("Boeing", "787", 330);

LOAD DATA INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\CS157AFinalProject\\cs157aFinal\\src\\main\\resources\\Airline-Reservation\\plane.txt' into table plane;
LOAD DATA INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\CS157AFinalProject\\cs157aFinal\\src\\main\\resources\\Airline-Reservation\\country.txt' into table country;
LOAD DATA INFILE 'C:\\Users\\riley\\OneDrive\\Desktop\\CS157AFinalProject\\cs157aFinal\\src\\main\\resources\\Airline-Reservation\\airport.txt' into table airport;


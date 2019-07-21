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
	country VARCHAR(50) NOT NULL,
	iata CHAR(3),
	icao CHAR(4),
	lat DECIMAL(9, 6) NOT NULL,
	lng DECIMAL(9, 6) NOT NULL,
	altitude INT,
	dst CHAR(1),
	tz VARCHAR(50),
	type CHAR(7) NOT NULL,
	source CHAR(11) NOT NULL,
	FOREIGN KEY (country) REFERENCES Country(name) ON UPDATE CASCADE,
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

INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Afghanistan','AF')
,('Albania','AL')
,('Algeria','AG')
,('American Samoa','AQ')
,('Angola','AO')
,('Anguilla','AV')
,('Antarctica','AY')
,('Antigua and Barbuda','AC')
,('Argentina','AR')
,('Armenia','AM')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Ashmore and Cartier Islands','AT')
,('Australia','AS')
,('Austria','AU')
,('Azerbaijan','AJ')
,('Bahamas','BF')
,('Bahrain','BA')
,('Baker Island','FQ')
,('Bangladesh','BG')
,('Barbados','BB')
,('Bassas da India','BS')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Belarus','BO')
,('Belgium','BE')
,('Belize','BH')
,('Benin','BN')
,('Bermuda','BD')
,('Bhutan','BT')
,('Bolivia','BL')
,('Bosnia and Herzegovina','BK')
,('Botswana','BC')
,('Bouvet Island','BV')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Brazil','BR')
,('British Indian Ocean Territory','IO')
,('British Virgin Islands','VI')
,('Brunei','BX')
,('Bulgaria','BU')
,('Burkina Faso','UV')
,('Burma','BM')
,('Burundi','BY')
,('Cambodia','CB')
,('Cameroon','CM')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Canada','CA')
,('Cape Verde','CV')
,('Cayman Islands','CJ')
,('Central African Republic','CT')
,('Chad','CD')
,('Chile','CI')
,('China','CH')
,('Christmas Island','KT')
,('Clipperton Island','IP')
,('Cocos (Keeling) Islands','CK')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Colombia','CO')
,('Comoros','CN')
,('Congo (Brazzaville)','CG')
,('Congo (Kinshasa)','CF')
,('Cook Islands','CW')
,('Coral Sea Islands','CR')
,('Costa Rica','CS')
,('Cote d''Ivoire','IV')
,('Croatia','HR')
,('Cuba','CU')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Cyprus','CY')
,('Czech Republic','EZ')
,('Denmark','DA')
,('Djibouti','DJ')
,('Dominica','DO')
,('Dominican Republic','DR')
,('Ecuador','EC')
,('Egypt','EG')
,('El Salvador','ES')
,('Equatorial Guinea','EK')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Eritrea','ER')
,('Estonia','EN')
,('Ethiopia','ET')
,('Europa Island','EU')
,('Falkland Islands','FK')
,('Faroe Islands','FO')
,('Fiji','FJ')
,('Finland','FI')
,('France','FR')
,('French Guiana','FG')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('French Polynesia','FP')
,('French Southern and Antarctic Lands','FS')
,('Gabon','GB')
,('Gambia','GA')
,('Gaza Strip','GZ')
,('Georgia','GG')
,('Germany','GM')
,('Ghana','GH')
,('Gibraltar','GI')
,('Glorioso Islands','GO')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Greece','GR')
,('Greenland','GL')
,('Grenada','GJ')
,('Guadeloupe','GP')
,('Guam','GQ')
,('Guatemala','GT')
,('Guernsey','GK')
,('Guinea','GV')
,('Guinea-Bissau','PU')
,('Guyana','GY')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Haiti','HA')
,('Heard Island and McDonald Islands','HM')
,('Honduras','HO')
,('Hong Kong','HK')
,('Howland Island','HQ')
,('Hungary','HU')
,('Iceland','IC')
,('India','IN')
,('Indonesia','ID')
,('Iran','IR')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Iraq','IZ')
,('Ireland','EI')
,('Isle of Man','IM')
,('Israel','IS')
,('Italy','IT')
,('Jamaica','JM')
,('Jan Mayen','JN')
,('Japan','JA')
,('Jarvis Island','DQ')
,('Jersey','JE')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Johnston Atoll','JQ')
,('Jordan','JO')
,('Juan de Nova Island','JU')
,('Kazakhstan','KZ')
,('Kenya','KE')
,('Kingman Reef','KQ')
,('Kiribati','KR')
,('Kuwait','KU')
,('Kyrgyzstan','KG')
,('Laos','LA')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Latvia','LG')
,('Lebanon','LE')
,('Lesotho','LT')
,('Liberia','LI')
,('Libya','LY')
,('Lithuania','LH')
,('Luxembourg','LU')
,('Macau','MC')
,('Macedonia','MK')
,('Madagascar','MA')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Malawi','MI')
,('Malaysia','MY')
,('Maldives','MV')
,('Mali','ML')
,('Malta','MT')
,('Marshall Islands','RM')
,('Martinique','MB')
,('Mauritania','MR')
,('Mauritius','MP')
,('Mayotte','MF')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Mexico','MX')
,('Micronesia','FM')
,('Midway Islands','MQ')
,('Moldova','MD')
,('Monaco','MN')
,('Mongolia','MG')
,('Montenegro','MJ')
,('Montserrat','MH')
,('Morocco','MO')
,('Mozambique','MZ')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Namibia','WA')
,('Nauru','NR')
,('Navassa Island','BQ')
,('Nepal','NP')
,('Netherlands','NL')
,('Netherlands Antilles','NT')
,('New Caledonia','NC')
,('New Zealand','NZ')
,('Nicaragua','NU')
,('Niger','NG')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Nigeria','NI')
,('Niue','NE')
,('Norfolk Island','NF')
,('North Korea','KN')
,('Northern Mariana Islands','CQ')
,('Norway','NO')
,('Oman','MU')
,('Pakistan','PK')
,('Palau','PS')
,('Palmyra Atoll','LQ')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Panama','PM')
,('Papua New Guinea','PP')
,('Paracel Islands','PF')
,('Paraguay','PA')
,('Peru','PE')
,('Philippines','RP')
,('Pitcairn Islands','PC')
,('Poland','PL')
,('Portugal','PO')
,('Puerto Rico','RQ')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Qatar','QA')
,('Reunion','RE')
,('Romania','RO')
,('Russia','RS')
,('Rwanda','RW')
,('Saint Helena','SH')
,('Saint Kitts and Nevis','SC')
,('Saint Lucia','ST')
,('Saint Pierre and Miquelon','SB')
,('Saint Vincent and the Grenadines','VC')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Samoa','WS')
,('Sao Tome and Principe','TP')
,('Saudi Arabia','SA')
,('Senegal','SG')
,('Serbia','RB')
,('Seychelles','SE')
,('Sierra Leone','SL')
,('Singapore','SN')
,('Slovakia','LO')
,('Slovenia','SI')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Solomon Islands','BP')
,('Somalia','SO')
,('South Africa','SF')
,('South Georgia and the Islands','SX')
,('South Korea','KS')
,('South Sudan','SS')
,('Spain','SP')
,('Spratly Islands','PG')
,('Sri Lanka','CE')
,('Sudan','SU')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Suriname','NS')
,('Svalbard','SV')
,('Swaziland','WZ')
,('Sweden','SW')
,('Switzerland','SZ')
,('Syria','SY')
,('Taiwan','TW')
,('Tajikistan','TI')
,('Tanzania','TZ')
,('Thailand','TH')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Timor-Leste','TT')
,('Togo','TO')
,('Tokelau','TL')
,('Tonga','TN')
,('Trinidad and Tobago','TD')
,('Tromelin Island','TE')
,('Tunisia','TS')
,('Turkey','TU')
,('Turkmenistan','TX')
,('Turks and Caicos Islands','TK')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Tuvalu','TV')
,('Uganda','UG')
,('Ukraine','UP')
,('United Arab Emirates','AE')
,('United Kingdom','UK')
,('United States','US')
,('Uruguay','UY')
,('Uzbekistan','UZ')
,('Vanuatu','NH')
,('Venezuela','VE')
;
INSERT INTO dev_airline_reservation.country (name,`alphaTwoCode`) VALUES 
('Vietnam','VM')
,('Virgin Islands','VQ')
,('Wake Island','WQ')
,('Wallis and Futuna','WF')
,('West Bank','WE')
,('Western Sahara','WI')
,('Yemen','YM')
,('Zambia','ZA')
,('Zimbabwe','ZI')
;


CREATE database bank_krwi;
USE bank_krwi;

CREATE TABLE IF NOT EXISTS dawca (
  id INT AUTO_INCREMENT PRIMARY KEY,
  imie VARCHAR(45) NULL,
  nazwisko VARCHAR(45) NULL,
  data_urodzenia date NULL,
  numer_telefonu VARCHAR(9) NULL
  );

CREATE TABLE IF NOT EXISTS bank (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nazwa VARCHAR(45) NULL,
  adres VARCHAR(45) NULL,
  numer_telefonu VARCHAR(9) NULL
);

CREATE TABLE IF NOT EXISTS krew (
  id INT AUTO_INCREMENT PRIMARY KEY,
  grupa_krwi ENUM('0-','0+','A+', 'B+', 'AB+','A-', 'B-', 'AB-') NULL,
  dawca_id INT NOT NULL,
  FOREIGN KEY (dawca_id) REFERENCES dawca(id),
  bank_id INT NOT NULL,
  FOREIGN KEY (bank_id) REFERENCES bank(id),
  data_pobrania date
 );
 
CREATE TABLE IF NOT EXISTS magazyn (
id INT AUTO_INCREMENT PRIMARY KEY,
bank_id INT NOT NULL,
FOREIGN KEY (bank_id) REFERENCES bank(id),
nazwa VARCHAR(45) NULL,
grupa_krwi ENUM('0-','0+','A+', 'B+', 'AB+','A-', 'B-', 'AB-') NULL,
ilosc int null,
stan enum('brak potrzeb','stan optymalny','stan średni','pilnie potrzebna')
 );
 
 CREATE TABLE IF NOT EXISTS krew_archiwum (
  id INT AUTO_INCREMENT PRIMARY KEY,
  grupa_krwi ENUM('A+', 'B+', 'AB+', '0+','A-', 'B-', 'AB-', '0-') NULL,
  dawca_id INT NOT NULL,
  FOREIGN KEY (dawca_id) REFERENCES dawca(id),
  data_pobrania date,
  data_archiwizacji date,
  status varchar(30)
 );

insert into dawca(id,imie,nazwisko,data_urodzenia,numer_telefonu)
values
(1,'John','Doe','1999-01-01','123456781'),
(2,'Jane','Doe','1998-02-02','123456782'),
(3,'Jan','Kowalski','1997-03-03','123456783'),
(4,'Janina','Kowalska','1996-04-04','123456784'),
(5,'Anna','Nowak','1995-05-05','123456785'),
(6,'Adam','Nowak','1994-06-06','123456786'),
(7,'James','Bond','1993-07-07','123456787'),
(8,'Wiola','Wisniewska','1992-08-08','123456788'),
(9,'Waldemar','Wisniewski','1991-09-09','123456789'),
(10,'Beata','Kowalczyk','1990-10-10','123456710'),
(11,'Borys','Kowalczyk','1989-11-11','123456711'),
(12,'Maria','Mazowiecka','1988-12-12','123456712'),
(13,'Robin','Hood','1987-02-28','123456713'),
(14,'Wanda','Vision','1986-10-10','123456714'),
(15,'John','Krasinski','1985-11-11','123456715'),
(16,'Dwight','Shrute','1984-12-12','123456716'),
(17,'Michael','Scott','1983-02-28','123456717'),
(18,'BJ','Nowak','1982-10-10','123456718'),
(19,'Kelly','Kapoor','1981-11-11','123456719'),
(20,'Angela','Kinsley','1980-12-12','123456720'),
(21,'Oscar','Martinez','1979-12-12','123456721'),
(22,'Ricky','Gervais','1978-02-28','123456722');

insert into bank(id,nazwa,adres,numer_telefonu)
values
(1,	'RCKiK we Wrocławiu',	'ul. Czerwonego Krzyża 5/9', '713715810'),
(2,	'RCKiK w Gdańsku',	'ul. Hoene Wrońskiego 4', '585204010'),
(3,	'RCKiK w Krakowie',	'ul. Rzeźnicza 11', '122618820');

insert into krew(id,grupa_krwi,dawca_id,bank_id,data_pobrania)
values
(1,'A+',1,1,'2020-01-22'),
(2,'A+',2,1,'2020-02-01'),
(3,'AB-',3,1,'2020-02-02'),
(4,'AB-',4,1,'2020-02-02'),
(5,'A+',5,2,'2020-02-03'),
(6,'AB+',6,2,'2020-02-03'),
(7,'B-',7,2,'2020-02-04'),
(8,'AB-',8,2,'2020-01-25'),
(9,'A-',9,2,'2020-01-26'),
(10,'A-',10,2,'2020-01-25'),
(11,'0+',11,3,'2020-02-10'),
(12,'0+',12,3,'2020-01-26'),
(13,'A+',13,3,'2020-02-05'),
(14,'A+',14,3,'2020-02-08'),
(15,'A+',15,3,'2020-02-07'),
(16,'AB-',16,3,'2020-02-05'),
(17,'AB-',17,3,'2020-02-08'),
(18,'A+',18,3,'2020-01-29'),
(19,'AB+',19,3,'2020-02-08'),
(20,'AB+',20,3,'2020-02-07'),
(21,'0-',21,3,'2020-02-05'),
(22,'0-',22,3,'2020-02-08');

insert into krew_archiwum(id,grupa_krwi,dawca_id,data_pobrania,data_archiwizacji,status)
values
(1,'A+',1,'2018-05-05','2019-09-16','utylizacja'),
(2,'AB-',2,'2019-12-21','2020-01-01','transfuzja'),
(3,'A+',3,'2019-12-19','2019-12-01','transfuzja'),
(4,'AB+',4,'2017-01-01','2019-09-16','utylizacja');


select * from dawca;
select * from bank;
select * from krew;
select * from krew_archiwum;


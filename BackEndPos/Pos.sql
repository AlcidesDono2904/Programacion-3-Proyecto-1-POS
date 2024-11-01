CREATE DATABASE Pos;

use Pos;

create table Categoria (
       id  varchar(10)  not null,
       nombre varchar(30) not null,
       Primary Key (id)         
     );

create table Producto (
       codigo varchar(10)  not null,
       descripcion varchar(30) not null,
	   unidadMedida varchar(20),
	   precioUnitario float,
          existencias int,
	   categoria varchar(10),
       Primary Key (codigo)         
     );

CREATE TABLE Cliente (
       id varchar(10) not null,
       nombre varchar(30) not null,
       telefono varchar(8),
       email varchar(40),
       descuento float,
       Primary Key (id)         
     );

CREATE TABLE Cajero (
       id varchar(10)  not null,
       nombre varchar(30) not null,
       Primary Key (id)         
     );

CREATE TABLE Linea (
       codigo int not null AUTO_INCREMENT,
       producto varchar(10) not null,
       cantidad int not null,
       descuento double,
       factura int,
       Primary Key (codigo)         
     );

CREATE TABLE Factura (
       codigo  int not null AUTO_INCREMENT,
       fecha date not null,
       cliente varchar(10),
       cajero varchar(10),
       Primary Key (codigo)
);

CREATE TABLE Usuario (
        codigo varchar(10) not null,
        clave varchar(10) not null,
        PRIMARY KEY (codigo)
);

ALTER TABLE Producto ADD Foreign Key (categoria) REFERENCES Categoria(id); 
ALTER TABLE Linea ADD Foreign Key (factura) REFERENCES Factura(codigo);
ALTER TABLE Linea ADD Foreign Key (producto) REFERENCES Producto(codigo);
ALTER TABLE Factura ADD Foreign Key (cliente) REFERENCES Cliente(id);
ALTER TABLE Factura ADD Foreign Key (cajero) REFERENCES Cajero(id);

insert into Categoria (id ,nombre) values('001','Aguas');
insert into Categoria (id ,nombre) values('002','Dulces');
insert into Categoria (id ,nombre) values('003','Aceites');
insert into Categoria (id ,nombre) values('004','Vinos');

insert into Producto values('001','Producto 1','medida1','1900.0',50,'001');
insert into Producto values('002','Producto 2','medida2','2900.0',40,'002');
insert into Producto values('003','Producto 3','medida3','3900.0',25,'003');
insert into Producto values('004','Producto 4','medida4','4900.0',10,'004');  

insert into Cliente values('001','Cliente 1','88001111','email1@gmail.com',0.0);
insert into Cliente values('002','Cliente 2','88002222','email2@gmail.com',20.0);
insert into Cliente values('003','Cliente 3','88003333','email3@gmail.com',30.0);

insert into Cajero values('001','Cajero 1');
insert into Cajero values('002','Cajero 2');
insert into Cajero values('003','Cajero 3');

insert into Factura (fecha, cliente, cajero) values('2020-01-01','001','001');
insert into Factura (fecha, cliente, cajero)values('2020-01-02','002','002');
insert into Factura (fecha, cliente, cajero)values('2020-01-03','003','003');

insert into Linea (producto,cantidad,descuento,factura)values('001','10','0.0', 1);
insert into Linea (producto,cantidad,descuento,factura)values('002','20','0.0', 2);
insert into Linea (producto,cantidad,descuento,factura)values('002','10','10.0', 3);

insert into Usuario values ('admin','root');
insert into Usuario values ('u1','root');
insert into Usuario values ('u2','root');

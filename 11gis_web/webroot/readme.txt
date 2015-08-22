创建数据库
id	type	name	email password	gender	hometown	address	birthday	cellphone	home_phone	direction	description	photo
varchar(40)	varchar(20)	varchar(20)	varchar(20)	varchar(4)	varchar(20)	varchar(50)	date	varchar(20)	varchar(20)	varchar(20)	varchar(100)	varchar(50)

创建库
CREATE DATABASE 11GIS_web CHARACTER SET utf8 COLLATE utf8_general_ci ;

创建表
create table users(
	id varchar(40) primary key,
	type varchar(20) NOT NULL,
	email varchar(40) NOT NULL UNIQUE,
	name varchar(20) NOT NULL,
	password varchar(20) NOT NULL,
	gender varchar(4) NOT NULL,
	hometown varchar(20) NOT NULL,
	address varchar(50) NOT NULL,
	birthday date NOT NULL,
	cellphone varchar(20) NOT NULL,
	home_phone varchar(20) ,
	direction varchar(20) NOT NULL,
	description varchar(100) ,
	photo varchar(50) 
)CHARACTER SET utf8 COLLATE utf8_general_ci;


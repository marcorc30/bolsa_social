create table moradia_area(
	id integer identity(1,1) primary key,
	area varchar(10),
	visivel bit,
	ordem integer
)

create table moradia_condicao(
	id integer identity(1,1) primary key,
	condicao varchar(50),
	visivel bit,
	ordem integer
)

create table moradia_imovel(
	id integer identity(1,1) primary key,
	imovel varchar(50),
	visivel bit,
	ordem integer
)
create table moradia_tipo(
	id integer identity(1,1) primary key,
	tipo varchar(50),
	visivel bit,
	ordem integer
)

create table moradia_resposta(
	id integer identity(1,1) primary key,
	resposta varchar(10),
	visivel bit,
	ordem integer
)



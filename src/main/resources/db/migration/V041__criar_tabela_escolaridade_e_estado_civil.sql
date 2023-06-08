create table escolaridade (
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer
)


create table estado_civil (
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer
)

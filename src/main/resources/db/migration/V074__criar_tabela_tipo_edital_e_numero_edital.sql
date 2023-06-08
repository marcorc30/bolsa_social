create table tipo_edital (
	id integer identity(1,1) primary key,
	descricao varchar(50),
	ordem integer,
	visivel bit

)


create table numero_edital (
	id integer identity(1,1) primary key,
	descricao varchar(50),
	ordem integer,
	visivel bit

)
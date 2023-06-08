create table parentesco (
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer

)


create table situacao_responsavel(
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer

)

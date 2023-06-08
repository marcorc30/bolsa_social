create table tipo_despesa(
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer
)


create table percentual_parecer(
	id integer identity(1,1) primary key,
	percentual varchar(50),
	descricao varchar(100),
	ordem integer,
	visivel bit
)

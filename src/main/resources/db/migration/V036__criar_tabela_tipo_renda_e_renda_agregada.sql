create table tipo_renda (
	id integer identity(1,1) primary key,
	nome varchar(100),
	visivel bit,
	ordem integer

)


create table renda_agregada(
	id integer identity(1,1) primary key,
	tipo_renda_id integer,
	valor decimal(5,2),
	solicitacao_id integer,

	foreign key (tipo_renda_id) references tipo_renda(id),
	foreign key (solicitacao_id) references solicitacao (id)


)

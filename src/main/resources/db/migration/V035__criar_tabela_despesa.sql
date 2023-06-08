create table despesa(

	id integer identity(1,1) primary key,
	tipo_despesa_id integer,
	valor decimal(5,2),
	solicitacao_id integer,

	foreign key (tipo_despesa_id) references tipo_despesa (id),
	foreign key (solicitacao_id) references solicitacao (id)
	)

create table solicitacao_beneficio(

	id integer identity(1,1) primary key,
	tipo_programa_governamental_id integer,
	valor decimal(5,2),
	solicitacao_id integer,

	foreign key (tipo_programa_governamental_id) references tipo_programa_governamental (id),
	foreign key (solicitacao_id) references solicitacao (id)
)

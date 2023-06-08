create table analise_cibs (

	id integer primary key,
	concessao varchar(50),
	percentual_id integer,
	parcela_inicial varchar(50),
	parcela_final varchar(50),
	observacao varchar(2000),
	solicitacao_id integer,

	foreign key (percentual_id) references percentual_parecer (id),
	foreign key (solicitacao_id) references solicitacao (id)

	)

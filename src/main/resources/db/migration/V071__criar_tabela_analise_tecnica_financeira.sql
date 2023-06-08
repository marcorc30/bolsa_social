create table analise_tecnica_financeira (
	id integer primary key,
	percentual_id integer,
	renda_bruta decimal(10,5),
	renda_percapita decimal(10,5),
	numero_familiares integer,
	observacao varchar(2000),
	solicitacao_id integer,
	
	foreign key (percentual_id) references percentual_parecer (id)
)

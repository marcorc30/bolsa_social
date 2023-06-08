alter table candidato
	add solicitacao_id integer

alter table candidato
	add foreign key (solicitacao_id) references solicitacao (id)	
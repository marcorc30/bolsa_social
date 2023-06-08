alter table solicitacao
 add candidato_id integer

alter table solicitacao
	add foreign key (candidato_id) references candidato (id) 
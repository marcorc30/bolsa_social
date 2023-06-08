alter table detalhes_processo_seletivo
  add motivo_alteracao_id integer

alter table detalhes_processo_seletivo
	add foreign key (motivo_alteracao_id) references motivo_alteracao (id)

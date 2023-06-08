alter table detalhes_processo_seletivo
  add turno_id integer

alter table detalhes_processo_seletivo
	add foreign key (turno_id) references turno (id)

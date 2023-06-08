alter table candidato
	add bolsa_anterior_resposta_id integer
	
	
alter table candidato
	add foreign key (bolsa_anterior_resposta_id) references bolsa_anterior_resposta (id)
	
alter table candidato
	add descricao_deficiencia varchar(60)
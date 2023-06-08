alter table candidato
	drop column nacionalidade
	
alter table candidato
 add nacionalidade_id integer

alter table candidato
	add foreign key (nacionalidade_id) references nacionalidade (id) 
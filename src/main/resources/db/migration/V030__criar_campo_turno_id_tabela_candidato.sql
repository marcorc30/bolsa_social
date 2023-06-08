alter table candidato
	drop column turno
	
alter table candidato
 add turno_id integer

alter table candidato
	add foreign key (turno_id) references turno (id) 
alter table candidato
	drop column tipo_transporte
	
alter table candidato
	drop column tipo_instituicao
	
alter table candidato
 add tipo_transporte_id integer

alter table candidato
 add tipo_instituicao_id integer

 
 
alter table candidato
	add foreign key (tipo_transporte_id) references tipo_transporte (id) 
	
	
alter table candidato
	add foreign key (tipo_instituicao_id) references tipo_instituicao (id) 	
	
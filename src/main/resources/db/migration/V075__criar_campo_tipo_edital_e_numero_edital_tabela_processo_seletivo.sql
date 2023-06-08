alter table processo_seletivo
	add tipo_edital_id integer
	
alter table processo_seletivo
	add numero_edital_id integer	
	
alter table processo_seletivo
	add foreign key (tipo_edital_id) references tipo_edital (id)
	
alter table processo_seletivo
	add foreign key (numero_edital_id) references numero_edital (id)	
alter table funcionario
	add assistente bit
	
alter table processo_seletivo
	add funcionario_id integer
	
alter table processo_seletivo
	add foreign key (funcionario_id) references funcionario (id)
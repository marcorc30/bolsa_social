create table solicitacao (
	id integer identity(1,1),
	ano integer,
	protocolo varchar(20),
	unidade_id integer,
	processo_seletivo_id integer,
	detalhes_processo_seletivo_id integer,
	data_solicitacao datetime,
	usuario_id integer,
	primary key (id),
	foreign key (unidade_id) references unidade (id),
	foreign key (processo_seletivo_id) references processo_seletivo (id),
	foreign key (detalhes_processo_seletivo_id) references detalhes_processo_seletivo (id),
	foreign key (usuario_id) references usuario (id) 
	 

)

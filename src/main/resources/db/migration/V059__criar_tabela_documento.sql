create table documento(
	
	id integer identity(1,1) primary key,
	componente_familiar_id integer,
	grupo_documento varchar(100),
	descricao varchar(100),
	nome_arquivo varchar(100),
	solicitacao_id integer
	
	foreign key (solicitacao_id) references solicitacao (id),
	foreign key (componente_familiar_id) references solicitacao_composicao_familiar (id)
	
)	 

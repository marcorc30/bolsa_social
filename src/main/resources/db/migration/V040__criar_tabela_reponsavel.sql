create table responsavel(
	
	id integer identity(1,1) primary key,
	nome varchar(100),
	cpf varchar(14),
	data_nascimento date,
	celular varchar(20),
	rg varchar(50),
	orgao_expedidor varchar(20),
	data_emissao date,
	parentesco_id integer,
	situacao_responsavel_id integer,
	solicitacao_id integer,

	foreign key (parentesco_id) references parentesco (id),
	foreign key (situacao_responsavel_id) references situacao_responsavel (id),
	foreign key (solicitacao_id) references solicitacao (id)

)
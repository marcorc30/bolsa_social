create table responsavel_financeiro (
	id integer identity(1,1) primary key,
	nome varchar(100),
	cpf varchar(100),
	data_nascimento date,
	celular varchar(15),
	telefone varchar(15),
	nacionalidade_id integer,
	naturalidade varchar(50),
	profissao varchar(100),
	email varchar(100),
	rg varchar(50),
	orgao_emissor varchar(50),
	data_emissao date,
	solicitacao_id integer,

	foreign key (nacionalidade_id) references nacionalidade (id),
	foreign key (solicitacao_id) references solicitacao (id)

)

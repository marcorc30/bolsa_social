create table tipo_documento_comum (

	id integer identity(1,1) primary key,
	descricao varchar(100),
	ativo bit,
	ordem integer

)

create table documento_comum (
	id integer identity(1,1) primary key,
	tipo_documento_comum_id integer,
	descricao varchar(100),
	nome_arquivo varchar(100),
	solicitacao_id integer,

	foreign key (tipo_documento_comum_id) references tipo_documento_comum (id),
	foreign key (solicitacao_id) references solicitacao (id)
)

create table solicitacao_bolsa_irmao(
	id integer identity(1,1) primary key,
	nome varchar(100),
	solicitacao_id integer,

	foreign key (solicitacao_id) references solicitacao (id)


)

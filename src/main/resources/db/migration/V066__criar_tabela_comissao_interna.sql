create table comissao_interna(
	id integer identity(1,1) primary key,
	funcionario_id integer,
	funcao_id integer,
	processo_seletivo_id integer,
	ativo bit,

	foreign key (funcionario_id) references funcionario (id),
	foreign key (funcao_id) references funcao (id),
	foreign key (processo_seletivo_id) references processo_seletivo (id)


 )
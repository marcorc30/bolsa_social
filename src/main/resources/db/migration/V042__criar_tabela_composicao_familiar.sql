create table solicitacao_composicao_familiar (
	id integer identity(1,1) primary key,
	nome varchar(100),
	parentesco_id integer,
	data_nascimento date,
	estado_civil_id integer,
	escolaridade_id integer,
	ocupacao varchar(100),
	salario decimal(5,2),
	solicitacao_id integer,


	foreign key (parentesco_id) references parentesco (id),
	foreign key (estado_civil_id) references estado_civil (id),
	foreign key (escolaridade_id) references escolaridade (id),
	foreign key (solicitacao_id) references solicitacao (id)

)

create table situacao_habitacional(
	id integer identity(1,1) primary key,
	moradia_area_id integer,
	moradia_tipo_id integer,
	moradia_imovel_id integer,
	moradia_condicao_id integer,
	moradia_outros_moradores_id integer,
	moraria_tem_vinculo_familiar_id integer default 0,
	quantidade_comodos integer,
	solicitacao_id integer,

	foreign key (moradia_area_id) references moradia_area (id),
	foreign key (moradia_tipo_id) references moradia_tipo (id),
	foreign key (moradia_imovel_id) references moradia_imovel (id),
	foreign key (moradia_condicao_id) references moradia_condicao (id),
	foreign key (moradia_outros_moradores_id) references moradia_resposta (id),
	foreign key (moraria_tem_vinculo_familiar_id) references moradia_resposta (id),
	foreign key (solicitacao_id) references solicitacao (id)

)
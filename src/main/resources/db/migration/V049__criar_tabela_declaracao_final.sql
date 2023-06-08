
create table declaracao_final (

	id integer identity(1,1) primary key,
	irresposta_id integer,
	inscrito_cad_unico bit,
	solicitacao_id integer,

	foreign key (irresposta_id) references irresposta (id),
	foreign key (solicitacao_id) references solicitacao (id)

)
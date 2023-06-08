create table detalhes_processo_seletivo (

	id integer identity(1,1) not null,
	quantidade_bolsas_50 integer,
	quantidade_bolsas_100 integer,
	serie integer,
	processo_seletivo_id integer,
	
	primary key (id),
	foreign key (processo_seletivo_id) references processo_seletivo (id)
	
)
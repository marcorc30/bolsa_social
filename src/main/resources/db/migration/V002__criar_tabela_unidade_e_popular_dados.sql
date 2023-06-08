create table unidade (
	id integer identity(1,1) not null, 
	codigo integer not null,
	uf varchar(2) not null,
	nome varchar(100) not null,
	cidade varchar(50) not null,
	sede_id integer,
	primary key (id),
	foreign key (sede_id) references sede (id)
	
)
	

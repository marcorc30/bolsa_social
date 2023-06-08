create table nivel (
	id integer not null primary key,
	descricao varchar(50) not null
)

create table serie (
	id integer not null,
	descricao varchar(50) not null,
	nivel_id integer,
	
	primary key(id),
	foreign key (nivel_id) references nivel (id)
)





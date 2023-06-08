create table unidade_serie(
	id_unidade integer not null,
	id_serie integer not null,
	primary key (id_unidade, id_serie),
	foreign key (id_unidade) references unidade(id),
	foreign key (id_serie) references serie(id)

)
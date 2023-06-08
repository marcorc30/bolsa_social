create table series_habilitadas_processo(
	id integer primary key identity(1,1) not null,
	processo_id integer not null,
	serie_id integer not null,
	
	foreign key (processo_id) references processo_seletivo(id),
	foreign key (serie_id) references serie(id)
	)

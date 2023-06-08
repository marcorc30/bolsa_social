create table usuario_unidade(
	id_usuario integer not null,
	id_unidade integer not null,
	primary key (id_usuario, id_unidade),
	foreign key (id_usuario) references usuario(id),
	foreign key (id_unidade) references unidade(id)

)
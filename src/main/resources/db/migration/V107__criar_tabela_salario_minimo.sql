CREATE TABLE salario_minimo(
	id integer identity(1,1) primary key,
	data_cadastro smalldatetime,
	valor_mes decimal(10, 5),
	valor_dia decimal(10, 5),
	valor_hora decimal(10, 5),
	data_vigencia_ini date,
	base_legal varchar(250) NULL,
	atual bit NULL
)


alter table processo_seletivo
	add salario_minimo_id integer
	
alter table processo_seletivo
	add foreign key (salario_minimo_id) references salario_minimo (id)
	
	

	
create table solicitacao_bolsa_veiculo(
	id integer identity(1,1) primary key,
	modelo varchar(100),
	ano_fabricacao varchar(4),
	solicitacao_id integer,
	
	foreign key (solicitacao_id) references solicitacao (id)  

)

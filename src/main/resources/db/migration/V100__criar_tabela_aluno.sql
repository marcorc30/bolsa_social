CREATE TABLE aluno(
	matricula varchar(15) primary key,
	nome varchar(100) NOT NULL,
	data_nascimento datetime,
	email varchar(100),
	telefone varchar(113),
	celular varchar(113),
	cpf varchar(14),
	cep varchar(9),
	endereco varchar(202),
	cidade varchar(60),
	bairro varchar(72),
	estado varchar(10)
)

ALTER TABLE usuario ADD
	data_criacao date,
	data_alteracao date 

ALTER TABLE processo_seletivo ADD
	data_criacao date,
	data_alteracao date 

ALTER TABLE solicitacao ADD
	data_criacao date,
	data_alteracao date

ALTER TABLE funcionario ADD
	data_criacao date,
	data_alteracao date


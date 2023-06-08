CREATE TABLE funcao(
	id INTEGER identity(1,1) primary key,
	descricao VARCHAR(100),
	ordem INTEGER,
	visivel BIT

)

CREATE TABLE funcionario(
	id INTEGER identity(1,1) primary key,
	nome VARCHAR(100),
	ordem INTEGER,
	visivel BIT
		
	)


CREATE TABLE unidade_funcionario(
   id_unidade INTEGER NOT NULL,
   id_funcionario INTEGER NOT NULL,
   PRIMARY KEY (id_unidade, id_funcionario),
   FOREIGN KEY (id_unidade) REFERENCES unidade(id),
   FOREIGN KEY (id_funcionario) REFERENCES funcionario(id)

)

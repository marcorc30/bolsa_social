create table candidato (

		id integer identity(1,1) primary key,
		nome varchar(100) not null,
		nacionalidade varchar(50),
		naturalidade varchar(50),
		data_nascimento datetime,
		email varchar(100) not null,
		telefone varchar(50),
		celular varchar(50),
		cpf varchar(14),
		rg varchar(50),
		rg_orgao varchar(20),
		rg_data_emissao datetime,
		sexo varchar(10) not null,
		cep varchar(10) not null,
		endereco varchar(100) not null,
		cidade varchar(50) not null,
		bairro varchar(50) not null,
		estado varchar(2) not null,
		tipo_instituicao integer,
		mora_perto_escola bit,
		possui_irmaos_colegio bit,
		possui_deficiencia bit,
		possui_desconto_escola_origem bit

)

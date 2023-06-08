create table processo_seletivo (

	id integer identity(1,1) not null,
	ano integer not null,
	descricao varchar(100) not null,
	unidade integer not null,
	divulgacao_edital datetime,
	preenchimento_cadastro_inicio datetime,
	preenchimento_cadastro_fim datetime,
	analise_perfil_inicio datetime,
	analise_perfil_fim datetime,
	validacao_comissao_interna_inicio datetime,
	validacao_comissao_interna_fim datetime,
	resultado_inicio datetime,
	resultado_fim datetime,
	efetivacao_concessao_bolsa_inicio datetime,
	efetivacao_concessao_bolsa_fim datetime,
	
	primary key (id)
	

)
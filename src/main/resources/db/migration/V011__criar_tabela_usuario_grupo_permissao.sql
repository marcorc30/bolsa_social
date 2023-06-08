CREATE TABLE usuario(
   id INTEGER identity(1,1) PRIMARY KEY,
   cpf VARCHAR(11) NOT NULL,
   email VARCHAR(100) NOT NULL,
   nome VARCHAR(50) NOT NULL,
   senha VARCHAR(20) NOT NULL,
   celular VARCHAR(20) NOT NULL,
   ativo bit 
   
)

CREATE TABLE grupo(
   id INTEGER identity(1,1) PRIMARY KEY,
   nome VARCHAR(50) NOT NULL
)


CREATE TABLE permissao(
   id INTEGER identity(1,1) PRIMARY KEY,
   nome VARCHAR(50) NOT NULL

)


CREATE TABLE usuario_grupo(
   id_usuario INTEGER NOT NULL,
   id_grupo INTEGER NOT NULL,
   PRIMARY KEY (id_usuario, id_grupo),
   FOREIGN KEY (id_usuario) REFERENCES usuario(id),
   FOREIGN KEY (id_grupo) REFERENCES grupo(id)

)

CREATE TABLE grupo_permissao(
    id_grupo INTEGER NOT NULL,
    id_permissao INTEGER NOT NULL,
    PRIMARY KEY (id_grupo, id_permissao),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    FOREIGN KEY (id_permissao) REFERENCES permissao(id)
    
)

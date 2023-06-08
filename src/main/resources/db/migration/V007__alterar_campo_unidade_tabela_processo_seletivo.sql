alter table processo_seletivo drop column unidade
alter table processo_seletivo add unidade_id integer
alter table processo_seletivo add foreign key (unidade_id) references unidade (id)

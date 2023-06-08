alter table detalhes_processo_seletivo drop column serie
alter table detalhes_processo_seletivo add serie_id integer
alter table detalhes_processo_seletivo add foreign key (serie_id) references serie (id)

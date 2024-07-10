alter table consultas add activo tinyint;
update consultas set activo = 1;

alter table consultas add motivo varchar(100) not null;
update consultas set motivo = "NA";
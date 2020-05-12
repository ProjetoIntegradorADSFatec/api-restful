# API Spring Boot PostgreSQL

[![Docker](https://img.shields.io/badge/docker-latest-green)](https://www.docker.com/)
[![Postgres](https://img.shields.io/badge/postgres-latest-green)](https://www.postgresql.org/)

Scripts em PSQL para criar as tabelas de feições para as buscas de áreas de interesse para o [Sistema Web](https://github.com/ProjetoIntegradorADSFatec/web-gis) de visualização com migração de dados para um banco PostgreSQL e executar em um container docker.

Assim como a configuração do ambiente de desenvolvimento utilizando o gerenciador de container Docker com amplo uso da extensão Postgis para o processamento de operações geográficas.

## Criação do container docker PostgreSQL
```
## Download do repositório oficial
$ docker pull mdillon/postgis

## Execução do container com uma instância do Postgresql
$ docker run --name postgresql -p 5480:5432 -e POSTGRES_PASSWORD=postgres -d mdillon/postgis
```

## Crontainer docker database
> Criação do container docker PGAdmin4 (Interface Gráfica)
```
## Download do repositório oficial
$ docker pull dpage/pgadmin4

## Execução do container com uma instância do PGAdmin
$ docker run --name pgadmin4 -p 16543:80 -e PGADMIN_DEFAULT_EMAIL,PGADMIN_DEFAULT_PASSWORD=abner.anjos@fatec.sp.gov.br,postgres -d dpage/pgadmin4
```

> Criação do container docker PostgreSQL e PGAdmin4 (Interface Gráfica) por default com o Docker Compose
```
$ docker compose up -d postgresql pgadmin4
```

**Obs.:** Não esqueça de cadastrar o servidor de banco de dados PostgreSQL para o uso da interface gráfica PGAdmin4 com as credenciais necessárias

## Ambiente PSQL
```
## Instalação do ambiente psql CLI interface da linha de comando
$ sudo apt install postgresql
```

## Migração dos dados
```
## Criação do banco de dados para a migração
$ createdb -h 0.0.0.0 -p 5480 -U postgres shapes "Camada de feições para a definição de talhões"

## Alterando as permissões (exemplo)
$ sudo chmod 777 -R ../db

## Criação do usuario Postgres para a API e migração dos dados em csv
$ psql -h 0.0.0.0 -p 5480 -U postgres -d shapes -f create-tables.sql
```

## Gerenciamento do Banco de dados
```
## Entrar no banco de dados digitar a senha cadastrada para entrar
$ psql -h 0.0.0.0 -p 5480 -U api_restful -d shapes

## Verificar se as tabelas e a extensão foram criadas
shapes=> SELECT table_name FROM information_schema.tables WHERE table_schema='public';
    table_name
-------------------
 raster_columns
 raster_overviews
 municipios
 geography_columns
 geometry_columns
 spatial_ref_sys
(6 rows)
```

> Após a instalação do ambiente de desenvolvimento em seu navegador acesse o pgAdmin4 com o endereço `16543`.

<p align = "center">
  <img width = "600px" src = "../docs/assets/pgAdmin4-print.png">
</p>

> **Obs.:** Colocar as credenciais do arquivo `docker-compose.yml` configuradas acima.

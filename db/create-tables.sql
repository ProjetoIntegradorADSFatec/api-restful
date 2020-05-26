-- Ativar a extensão PostGIS

CREATE EXTENSION postgis;

-- Criação de usuário para o banco de dados

CREATE USER api_restful WITH PASSWORD 'api_spring_restful';

-- Criação da tabela para o cadastro de municípios

CREATE TABLE public.municipios
(
    fid bigint NOT NULL,
    fid_1 bigint,
    sprarea double precision,
    geocodigo character varying(7) COLLATE pg_catalog."default",
    nome1 character varying(33) COLLATE pg_catalog."default",
    uf character varying(2) COLLATE pg_catalog."default",
    id_uf character varying(2) COLLATE pg_catalog."default",
    regiao character varying(12) COLLATE pg_catalog."default",
    mesoregiao character varying(34) COLLATE pg_catalog."default",
    microregia character varying(36) COLLATE pg_catalog."default",
    latitude double precision,
    longitude double precision,
    sede character varying(7) COLLATE pg_catalog."default",
    ogr_geometry geometry(MultiPolygon,4618),
    CONSTRAINT municipios_fid_pk PRIMARY KEY (fid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE INDEX municipios_ogr_geometry_idx
    ON public.municipios USING gist
    (ogr_geometry)
    TABLESPACE pg_default;

ALTER TABLE public.municipios
    OWNER to api_restful;

-- Copiando os dados dos municípios para o banco de dados a partir de um arquivo em csv

\COPY public.municipios FROM 'csv/municipios.csv' DELIMITER ';' CSV HEADER;

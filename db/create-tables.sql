-- Ativar a extensão PostGIS

CREATE EXTENSION postgis;

-- Criação de usuário para o banco de dados

CREATE USER api_restful WITH PASSWORD 'api_spring_restful';

-- Criação de tabela para o controle de usuários

CREATE TABLE public.users
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to api_restful;

-- Criação da tabela para o controle de autorizações

CREATE TABLE public.user_authorizations
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    type character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT user_authorizations_pkey PRIMARY KEY (id),
    CONSTRAINT fkgek2c3qxjc3g29pk0gaqkythi FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.user_authorizations
    OWNER to api_restful;

-- Copiando os dados do usuário admin para o banco de dados a partir de um arquivo em csv

\COPY public.users FROM 'csv/users.csv' DELIMITER ';' CSV HEADER;
\COPY public.user_authorizations FROM 'csv/user_authorizations.csv' DELIMITER ';' CSV HEADER;

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

-- Catálogo de imagens

CREATE TABLE public.catalog
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    band character varying(255) COLLATE pg_catalog."default",
    date_time character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    image character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT catalog_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.catalog
    OWNER to api_restful;

-- Tabela de coodenadas

CREATE TABLE public.coordinate
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    projection character varying(255) COLLATE pg_catalog."default",
    catalog_id bigint,
    CONSTRAINT coordinate_pkey PRIMARY KEY (id),
    CONSTRAINT fkb367l8kyh47ckla8j3on78wlu FOREIGN KEY (catalog_id)
        REFERENCES public.catalog (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.coordinate
    OWNER to api_restful;

-- Copiando o catálogo de imagens com controle de coordenadas externo PostGIS

\COPY public.catalog FROM 'csv/catalog.csv' DELIMITER ';' CSV HEADER;
\COPY public.coordinate FROM 'csv/coordinate.csv' DELIMITER ';' CSV HEADER;

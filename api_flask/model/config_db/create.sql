CREATE TABLE public.sere_images
(
    id bigint NOT NULL,
    latitude double precision,
    longitude double precision,
    font character varying(30) COLLATE pg_catalog."default",
    band character varying(10) COLLATE pg_catalog."default",
    image_date timestamp(3) with time zone,
    image_url character varying(900) COLLATE pg_catalog."default",
    CONSTRAINT sere_images_id_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

\COPY public.sere_images FROM '/home/a/Desktop/api-restful/api_flask/model/config_db/data.csv' DELIMITER ';' CSV HEADER;
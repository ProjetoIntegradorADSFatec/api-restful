# API Spring Boot RESTful

[![Java](https://img.shields.io/badge/java-8-green)](https://www.java.com/)
[![SpringBoot](https://img.shields.io/badge/spring-latest-green)](https://spring.io/)
[![Gradle](https://img.shields.io/badge/gradle-5+-green)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/docker-latest-green)](https://www.docker.com/)
[![Postgres](https://img.shields.io/badge/postgres-latest-green)](https://www.postgresql.org/)
[![GeoServer](https://img.shields.io/badge/geoserver-latest-green)](http://geoserver.org/)

Projeto Integrador entre o sexto período da Faculdade de Tecnologia de São José dos Campos, Professor Jessen Vidal de *Análise e Desenvolvimento de Sistemas* e a empresa [*Visiona*](http://www.visionaespacial.com.br/), para qual nos forneceu o problema da identificação de talhões em imagens de sensoriamento remoto.

Para o funcionamento da API Spring Boot é necessário os seguintes requisitos:

- Uma instância do banco de dados [PostgreSQL/PostGIS](./db) em execução e configurado;
- Um servidor de mapas [Geoserver](./geoserver) em execução e configurado;

# Projeto

Este projeto consiste em:

1. Desenvolvimento de uma API RESTful para consulta de dados georreferenciados em um banco de dados PostGIS;
2. Com dados multitemporais, utilizar-se de inteligência artificial para identificar talhões em uma área de interesse.

# Ferramentas

- CI

    É necessário uma máquina virtual, esta servirá para o software Jenkins que executará os processos de Integração Contínua.

    * Se a máquina for local, utilize o ngrok, um serviço gratuito de tunelamento sem configuração de firewall ou port forwarding.

    Agora no Jenkins deve-se configurar a *pipeline* de testes nos seguintes passos:

    1. *Clone*: Para buscar o repositório com os novos dados;
    2. *Environment*: Instala as dependências do projeto;

    Configuar Webhook no GitHUB para o *endpoint* do Jenkins (*URL do ngrok se foi utilizado*) e ativar a opção:
    * GitHub hook trigger for GITScm polling

---

- CD

    `a definir processo de deploy.`

# Instalação e execução da aplicação

## Instalação para o ambiente de desenvolvimento
**Obs.:** Necessário instalação do [gradle 5+](https://gradle.org/).
```
$ gradle bootRun
```

## Execução da aplicação em micro serviços
**Obs.:** Não esqueça de mudar o endereço *`IP (localhost)`* do banco de dados no arquivo *`application.properties`* para o endereço real do servidor PostgreSQL do seu computador.
```
## Gerar o arquivo executável `.jar` utilizando o Gradle 5+
$ gradle build

## Construir a imagem docker com base no `Dockerfile`
$ docker build -t spring-restful .

## Executar o container localmente
$ docker run --name spring-api-restful -p 4040:8080 -d spring-restful
```

> Após o comando `docker run` digite o seguinte `docker ps` para listar os serviços  em execução pelo seu docker instalado, a resposta deve ser a seguinte:

~~~shell
$ docker ps
CONTAINER ID        IMAGE                  COMMAND                  CREATED             STATUS              PORTS                            NAMES
a06cb1ccf107        spring-restful         "java -jar /app.jar"     25 hours ago        Up 25 hours         0.0.0.0:4040->8080/tcp           spring-api-restful
~~~

> Em seu navegador digite o seguinte link `localhost:8080/cache/list` e verá a seguinte resposta:

~~~json
[
    {
        "id" : 16324,
        "name" : "clip_20170612T083546_Sigma0_VH_db.tif",
        "description" : "clip_20170612T083546_Sigma0_VH Sentinel 1",
        "band" : "band VH polarization effect",
        "link" : "http://www.dpi.inpe.br/agricultural-database/lem/dados/cenas/Sentinel1/20170612_S1A/clip_20170612T083546_Sigma0_VH_db.tif"
    },
    ...
]
~~~

# Referências
 - [Visiona](http://www.visionaespacial.com.br/);
 - [Jenkins](https://jenkins.io/);
 - [ngrok](https://ngrok.com/);

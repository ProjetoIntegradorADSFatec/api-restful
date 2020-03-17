# API-RESTful

![Python](https://img.shields.io/badge/python-3.8-green)
![Flask](https://img.shields.io/badge/flask-latest-green)

Projeto Integrador entre o sexto período da Faculdade de Tecnologia de São José dos Campos, Professor Jessen Vidal de *Análise e Desenvolvimento de Sistemas* e a empresa *Visiona*, para qual nos forneceu o problema da identificação de talhões em imagens de sensoriamento remoto.


### Projeto

Este projeto consiste em:
1. Desenvolvimento de uma API RESTful para consulta de dados georreferenciados em um banco de dados PostGIS;
2. Com dados multitemporais, utilizar-se de inteligência artificial para identificar talhões em uma área de interesse.


### Ferramentas

- CI

    É necessário uma máquina virtual, esta servirá para o software Jenkins que executará os processos de Integração Contínua.

    * Se a máquina for local, utilize o ngrok, um serviço gratuito de tunelamento sem configuração de firewall ou port forwarding.

    Agora no Jenkins deve-se configurar a *pipeline* de testes nos seguintes passos:

    1. *Clone*: Para buscar o repositório com os novos dados;
    2. *Install*: Instala as dependências do projeto;
    3. *Tests*: Executa os testes via `gradle test`.

    Configuar Webhook no GitHUB para o *endpoint* do Jenkins (*URL do ngrok se foi utilizado*) e ativar a opção:
    * GitHub hook trigger for GITScm polling

---

- CD
`a definir processo de deploy.`


### Referências
 - [Visiona](http://www.visionaespacial.com.br/);
 - [Spring Boot](https://spring.io/projects/spring-boot);
 - [Jenkins](https://jenkins.io/);
 - [ngrok](https://ngrok.com/).



# spring-boot-sample
Projeto sample utilizando Spring Boot

Requisitos necessários para execução da aplicação:

	- Java 8
	- Maven (Última versão)
	- Docker
	- Docker-compose(Última versão)

O projeto é composto por quatro subprojetos, são eles:
	1) spring-boot-repo - Repositório está guardado os arquivos de configuração.
	2) spring-rabbitmq-consumer - Aplicação responsável por consumir as mensagens da fila no RabbitMQ para enviar emails.
	3) spring-boot-sample - Aplicação principal onde estão disponibilizados os serviços de Login e Usuário.
	4) spring-config-server - Aplicação que disponibiliza remotamente as informações de configuração para os projetos 2 e 3 
	com os perfis development e production.	

Foram mantidas as configurações de porta padrão para todos os serviços, conforme abaixo:
	Redis - 6379
	RabbitMQ - 5672
	Postgres - 5432
	Config-server - 8888	

Para executar a aplicação basta executar o comando *sudo docker-compose up*

A aplicação principal estará disponível no endereço http://localhost:8080;

A documentação da api encontra-se disponível no endereço /docs relativo ao path raiz da aplicação
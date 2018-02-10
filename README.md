# spring-boot-sample
Projeto sample utilizando Spring Boot

## Pré-requisitos para funcionamento da aplicação:
* Java 8
* Maven (Última versão)
* Docker
* Docker-compose(Última versão)

## Dependências
Todas as dependências são gerenciadas pelo maven, portanto estão listadas no arquivo pom.xml na raiz assim como nos respectivos módulos.

## Estrutura do projeto maven
O projeto é composto por quatro módulos, são eles:
* spring-boot-repo - Repositório remoto onde está os arquivos de configuração.
* spring-rabbitmq-consumer - Aplicação responsável por consumir as mensagens da fila no RabbitMQ para enviar emails.
* spring-boot-sample - Aplicação principal onde estão disponibilizados os serviços de Login e Usuário.
* spring-config-server - Aplicação que disponibiliza remotamente as informações de configuração para os projetos 2 e 3 
	com os perfis development e production.	

## Infraestrutura
Foram mantidas as configurações de porta padrão para todos os serviços, conforme abaixo:
* Aplicação:8080
* Redis:6379
* RabbitMQ:5672
* Postgres:5432
* Config-server:8888	

## Execução
Para rodar a aplicação basta executar o comando **mvn package** na raiz e depois executar **sudo docker-compose up -d**.
A aplicação principal estará disponível no endereço http://localhost:8080. O usuario **jossa** com a senha **1234** está cadastrado na base e pode ser utilizado para testes.

## Documentação
A documentação dos recursos disponíveis via api REST encontra-se disponível no endereço **/docs/index.html** relativo ao path raiz da aplicação.

## Estrutura de pacotes da aplicação principal
Dentro do diretório padrão do maven *src/main/java* temos

* com.ia.dell.springbootsample.controller: Todos os recursos disponíveis via api encontra-se nesse pacote. As rotas são prefixidas por **/api/{versao}/{nomeDoRecursoNoPlural}**.

* com.ia.dell.springbootsample.config: Contém as classes de configuração do Spring.

* com.ia.dell.springbootsample.exception: Contém as classes de Exceção ou tratamento de exceções.

* com.ia.dell.springbootsample.model: Contém as entidades que representam o modelo da aplicação.

* com.ia.dell.springbootsample.repository: Contém as classes repositório de modelos da aplicação.

* com.ia.dell.springbootsample.service: Contém as interfaces que representam os serviços.

* com.ia.dell.springbootsample.service.impl: Contém as implementações das interfaces de serviços descritas no item anterior. 

Dentro do diretório padrão do maven *src/main/resources* temos

* application.properties: contém as propriedades da aplicação que não mudam de acordo com o ambiente(desenvolvimento ou produção).

* bootstrap.properties: arquivo utilizado para que o Spring Cloud Config possa carregar as configurações do servidor remoto de configuração. 

Dentro do diretório padrão do maven *src/test/java* temos

* com.ia.dell.springbootsample.controller: Classes de teste dos recursos da aplicação utilizando o RESTDocs do spring para geração de documentação.

* com.ia.dell.springbootsample.mother: Classes que implementam o padrão Mother de criação de objetos mock para teste.

* com.ia.dell.springbootsample.repository: Classes de teste de repositório que utiliza banco em memória para agilidade nos testes de integração com o banco.

## Configuração
Todas as configurações de componentes internos da aplicação estão no repositório remoto do git disponível na url https://github.com/joceliootavio/spring-boot-repo
As configurações de serviços externos e implantação do projeto estão descritos no arquivo **docker-compose.yml** na raiz do projeto.

## Problemas comuns

1. Erro de porta já em uso
... Descrição: Quando já existe serviços rodando nas portas utilizadas pelos containers da máquina onde está sendo implantada a aplicação, ocorre erro de portas já em uso.
... Solução: Alterar a porta externa dos containers ou parar os serviços da máquina antes de inicializar os containers.

2. Container *app* ou *rabbitmq-consumer* sobe mas depois cai.
... Descrição: O docker não aguarda a conclusão do start do serviço de configuração remoto *config-server* e já inicia os outros dois containers que dependem dele.
... Solução: Executar depois do comando *docker-compose up* o comando de start específico de cada container

## Thiago Nunes Vieira

https://github.com/thiagonunesvieira21/backend-challenge.git

# Invillia recruitment challenge

		O projeto invilia foi separado em 5 módulos para atender as princípios de arquitetura de microsserviços. Cada módulo contém o seu próprio Dockerfile o que permite o isolando dos ambientes de cada projeto. 
	
		A solução apresentada permite o gerenciar e a localização dos microsserviços por meio do netflix-eureka.
		
		A solução possibilita uma alta escalabilidade graça ao docker e balanceamento de carga 	realizado com o netflix-zuul.
		
		Na solução não foi um mecanismo para tolerância a falha além do zuul load balancing com 	maior tempo seria possível utilizar o Hystrix para implementar o padrão Circuit Breaker.

## Estrutura do Projeto:


	├── invillia			#Maven pom project
	│   ├── eureka-server		#Maven module project para gerenciar o status e a localização dos Microservices
	│   ├── keycloak		#Pasta contendo a image jboss/keycloak:1.9.1.Final e o import do realm de segurança
	│   ├── order-api		#Maven module microservice project Order
	│   ├── payment-api		#Maven module microservice project Payment
	│   ├── store-api		#Maven module microservice project Store
	│   └── zull-server		#Maven module project para load balancing
	│
	└── docker-compose.yml	#Docker compose

## Instruções para compilar e excutar o projeto:
		
# Requer o docker instalado

		>> docker-compose up

# Keycloak
	user: admin
	password: invillia
	

## RESTful service(s) URIs:

* MicroService API **Store**:

				GET: 	'/api/v1/store/{storeId}' - Buscar store pelo ID
				GET:  '/api/v1/store' - Buscar store por filtros
				POST: '/api/v1/store' - Serviço responsável por cadastrar a Store
				PUT:  '/api/v1/store/{storeId}' - Serviço responsável por atualizar o store

* MicroService API **Order**:

				GET:  '/api/v1/order/{orderId}' - Buscar order pelo ID
				GET:  '/api/v1/order' - Buscar order por filtros
				POST: '/api/v1/order' - Serviço responsável por cadastrar a Order
				PUT:  '/api/v1/order/cancelOrder/{orderId}' - Serviço responsável por cancelar a order
				PUT:  '/api/v1/order/cancelOrderItem/{orderItemId}' - Serviço responsável por cancelar a orderItem

* MicroService API **Payment**:

				GET:  '/api/v1/payment/checkExpired/{orderId}' - Verifica se pelo ID da Order se o pagamento está vencido
				POST: '/api/v1/payment' - Serviço responsável por cadastrar o Payment


## Descrição da implementação:

* Asynchronous 
	
	Não foi implementado nenhum método/serviço assíncrono. 
	 
* Database
	
	Postgres - usando uma imagem docker (postgres:10.4). As configurações do banco estão no docker-compose.yml:
		 docker-postgres:
		    image: postgres:10.4
		    environment:
		      - POSTGRES_DB=invillia
		      - POSTGRES_USER=postgres
		      - POSTGRES_PASSWORD=invillia
		      
	H2 - utilizado somente para os teste de integração e unitários.
	
* AWS
	
	Serviço EC2 instância ubuntu 18.04. Utilização do software Putty para acessar a instância via SSH. 
	
* Docker	 -> Instalação na instância ubuntu 18.04 via console com comandos:
			 
	 >> sudo apt-get update
	 >> sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
	 >> curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add –
	 >> sudo add-apt-repository deb [arch=amd64] https://download.docker.com/linux/ubuntu  $(lsb_release -cs)  stable
	 >> sudo apt-get update	
	 >> sudo apt-get install docker-ce
		 

* Security 

	Segurança baseada em oauth2 com Keycloak com client confidential. Configuração anotando a classe de configuração com @EnableOAuth2Client e sobrescrevendo o método OAuth2ProtectedResourceDetails 	e criando o ClientCredentialsResourceDetails	informando o clientId, clientSecret e accessTokenUri   

* Swagger

	Utilização do maven dependency:
		
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>${springfox.version}</version>
		</dependency>
		
	A configuração do swagger com classe de configuração Spring. Configuração anotando com @EnableSwagger2 e habilitando o botão de segurança para geração do Token para segurançã.
	
	MicroService API Store:
		http://{server-host}:8083/swagger-ui.html

	MicroService API Order:
		http://{server-host}:8084/swagger-ui.html		

	MicroService API Payment:
		http://{server-host}:8085/swagger-ui.html
	
* Clean Code

	Para um código limpo foi empregado os princípios SOLID. Foi realizada uma única passagem para refatoração em todo o código. Em uma segunda passagem eu gostaria de mover todas as classes comuns para um novo módulo e importa-lo com uma dependência maven, exemplo as classes:
		InvalidRequestException;
		GenericService; e 
		BaseController. 

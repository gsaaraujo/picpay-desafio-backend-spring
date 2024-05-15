Implementação do [desafio picpay backend](https://github.com/PicPay/picpay-desafio-backend)

#### Design & Arquitetura
- Arquitetura hexagonal
- DDD (Entidade de domínio, objetos de valor e repositório)
- Message queue (RabbitMQ)
- DAO e Gateways
- Testes de unidade e integração

### Tecnologias
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring AMQP
- Docker


### Como executar

A api está containerizada, então você pode rodar o projeto usando devcontainer ou usando os comandos abaixo.

Subir os containers:
```
docker compose -f .devcontainer/compose.yml up -d --build
```

Acessar o container da api:
```
docker exec -it picpay-desafio-backend-api bash
```

Rodar a aplicação:
```
mvn spring-boot:run
```
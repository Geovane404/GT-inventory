# GT-inventory

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Geovane404/GT-inventory/blob/main/LICENSE) 

# Sobre o projeto

**GT-Inventory** é uma aplicação BackEnd de web services implementada com **Spring Boot, JPA, Testes, Validações e Segurança**. 
O Sistema disponibiliza APIs REST com CRUD completo de produtos, categorias e usuários. 

Na aplicação foram realizadas implementações de testes **Unitários** e **Integração** para validar os recursos disponibilizados pelo sitema. 

A aplicação possui validações utilizando a biblioteca do **Bean Validation** para validar algumas regras para manipulação de dados. O sistema conta ainda com implementação de segurança
utilizando o padrão **OAuth2** e o formato de token **JWT** onde, após a autenticação no sistema pelo usuário, o mesmo disponibiliza um **Token** que sera 
utilizado para realizar autorizações de acordo com o perfil do usuário logado. **Autorizações de rotas por perfil.**

## Modelo conceitual
![Modelo Conceitual](https://user-images.githubusercontent.com/65828907/207138168-8bf9d174-b749-45b2-a21a-8ba4df9e6ddd.png)

## Collection Postman
https://raw.githubusercontent.com/Geovane404/GT-inventory/main/GT-inventory.postman_collection.json

## Tecnologias utilizadas
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- H2
- Postman
## Como executar o projeto

Pré-requisitos: Java 11

```bash
# clonar repositório
git clone https://github.com/Geovane404/GT-inventory.git

# entrar na pasta do projeto back end
cd backend

# executar o projeto
./mvnw spring-boot:run
```

## Autor

Geovane Calheira da Silva

https://www.linkedin.com/in/geovane-cal-silva/

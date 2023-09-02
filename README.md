# University Rest API

O projeto é uma aplicação back-end com objetivo de construir uma APIs utilizando os frameworks [Spring Boot](https://projects.spring.io/spring-boot), [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) e [Spring Security](https://spring.io/projects/spring-security) 
para administrar alunos, professores e cursos de uma universidade.

## Tecnologias

- [Spring Boot](https://projects.spring.io/spring-boot) é uma ferramenta que simplifica a configuração e execução de aplicações Java stand-alone,  com conceitos de dependências “starters”, auto configuração e servlet container embutidos é proporcionado uma grande produtividade desde o start-up da aplicação até sua ida a produção.
 
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) é um framework já consolidado no mercado, que a partir da versão fornece mecanismos simplificados para a criação de APIs RESTful através de anotação, além disso, também possui recursos de serialização e deserialização de objetos de forma transparente 
 
- [Spring Security](https://spring.io/projects/spring-security) é um framework do projeto Spring que possui um sistema de autenticação e autorização de alto nível e altamente customizável para aplicações Java. O framework inclusive é a solução oficial para implementação de recursos de segurança em aplicações Spring Boot, porém ele também pode ser utilizado em conjunto com outras frameworks.

 
# Setup da aplicação (local)

## Pré-requisito

Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:
```
Docker 24.0.2
```

## Preparando ambiente

Para melhor utilização, é bom estar com o Docker rodando na máquina, já configurado. Após isso, vá até o repositório e utilize o seguinte comando (Em casos de duvida, pode ver meu repositorio com comandos de Docker: [REPOSITÓRIO](https://github.com/Manelitu/docker-study)):

```
 docker-compose up --build
```

Pronto. A aplicação está disponível em http://localhost:8080

Observação: Ao rodar e dar tudo certo, o Hibernate irá criar as relações automaticamente no nosso banco Postgres.
Para acessar ao banco, pode acessar o [PGADMIN](http://localhost:16543/) que será instalado também com docker.

Com as credenciais:
```
 login - admin@admin.com
 senha - admin
```

# Entidades (schemas)

## Users
    {
		"id": "2274aa31-049b-4863-91be-cfdc33e4fdc6",
		"name": "Emanuel Victor de Lima",
		"email": "admin@admin.com",
		"roles": "ADMIN",
		"active": true,
		"createdAt": [
			2023,
			8,
			29,
			23,
			43,
			58,
			971833000
		],
		"updatedAt": null,
		"deletedAt": null,
		"enabled": true,
		"authorities": [
			{
				"authority": "ROLE_ADMIN"
			}
		],
		"username": "admin@admin.com",
		"accountNonExpired": true,
		"accountNonLocked": true,
		"credentialsNonExpired": true
	}

## Disciplinas
    {
	"disciplineId": "1a04f789-170c-45df-83ac-68935471f621",
	"name": "Lógica de programação",
	"hours": 100,
	"description": "Como a maioria das atividades que fazemos no dia a dia, programar também possui modos diferentes de se fazer.",
    "active": true
    }

## Periodos
    {
		"periodId": "9c64bd24-7f7e-47a7-9aba-b31ea431a2d6",
		"period": 1,
		"course_uuid": "04d7da55-2c1d-47d6-96da-87ade35fbc70",
		"disciplines": Array de ENTIDADES DISCIPLINAS,
            "active": true
    }

## Cursos
    {
        "courseId": "c0daa0b4-d2b6-4824-987e-ce0af2ae259b",
        "name": "Administração",
        "description": "Administração é a ciência que se dedica ao estudo e gestão de organizações, sejam elas públicas ou privadas. Seu objetivo é promover a eficiência na gestão dos recursos.",
        "active": true,
        "periods": Array de ENTIDADES PERIODOS
    }


# APIs

O projeto disponibiliza algumas APIs em 5 contextos diferentes: Auth, Users, Courses, Disciplines e Periods, onde utilizam o padrão Rest de comunicação, produzindo e consumindo arquivos no formato JSON.

Segue abaixo as APIs disponíveis no projeto, e as permissões (ROLES):

### Roles
    ADMIN
    COORDINATOR
    PROFESSOR
    STUDENT

## Auth

### Request
         api/v1/auth/login (POST)
### Response
    {
    	"email": "user@admin.com",
    	"password": "admin",
    }
## Users

### Role obrigatória para esta rota
    ADMIN
### Headers
    Authorization: Bearer {token}

### Request
         api/v1/users (GET)
### Response
    Array of ENTIDADE USERS
### Request
    api/v1/users/{id} (GET)
### Response
    ENTIDADE USERS
### Request
    api/v1/users/{id} (DELETE)
### Response
    ENTIDADE USERS
### Request
    api/v1/users (POST)
### Body 
    {
    "name": "Emanuel Victor de Lima",
    "email": "user@admin.com",
    "password": "admin",
    "roles": "STUDENT"
    }	
### Response
     ENTIDADE USERS
### Request
    api/v1/users/{id} (PATCH)
### Body
        {
    	"name": "Emanuel Victor de Lima",
    	"email": "user@admin.com",
        }
### Response
     ENTIDADE USERS
## Courses
### Role obrigatória para esta rota
    COORDINATOR
### Headers
    Authorization: Bearer {token}
### Request
    api/v1/courses (GET)
### Response
    Array of ENTIDADE CURSOS
### Request
    api/v1/courses/{id} (GET)
### Response
    ENTIDADE CURSOS
### Request
    api/v1/courses (GET)
### Response
    ENTIDADE CURSOS
### Request
    api/v1/courses/{id} (DELETE)
### Response
    ENTIDADE CURSOS
### Request
    api/v1/courses (POST)
### Body
        {
            "name": "Ed. Fisica 2",
            "description": "Descrição de teste"
        }
### Response
    ENTIDADE CURSOS
### Request
    api/v1/courses/{id} (PATCH)
### Body
        {
            "name": "Ed. Fisica 2",
            "description": "Descrição de teste"
        }
### Response
    ENTIDADE CURSOS
## Disciplines
### Role obrigatória para esta rota
    COORDINATOR
### Headers
    Authorization: Bearer {token}
### Request
    api/v1/disciplines (GET)
### Response
    Array of ENTIDADE DISCIPLINAS
### Request
    api/v1/disciplines/{id} (DELETE)
### Response
    ENTIDADE DISCIPLINAS
### Request
    api/v1/disciplines/{id} (GET)
### Response
    ENTIDADE DISCIPLINAS
### Request
    api/v1/disciplines (POST)
### Body
    {
    	"name": "Lógica de programação",
    	"hours": 80,
            "description": "descrição",
    	"periodId": "9c64bd24-7f7e-47a7-9aba-b31ea431a2d6"
    }
### Response
    ENTIDADE DISCIPLINAS
### Request
    api/v1/disciplines/{id} (PATCH)
### Body
    {
    	"name": "Lógica de programação",
    	"hours": 80,
            "description": "descrição",
    }
### Response
    ENTIDADE DISCIPLINAS          

## Periods
### Role obrigatória para esta rota
    COORDINATOR
### Headers
    Authorization: Bearer {token}
### Request
    api/v1/period (GET)
### Response
    Array of ENTIDADE PERIODOS
### Request
    api/v1/period/{id} (DELETE)
### Response
    ENTIDADE PERIODOS
### Request
    api/v1/period/{id} (GET)
### Response
    ENTIDADE PERIODOS
### Request
    api/v1/period (POST)
### Body
    {
    	"period": "1ª semestre",
    	"courseId": "04d7da55-2c1d-47d6-96da-87ade35fbc70"
    }
### Response
    ENTIDADE PERIODOS
### Request
    api/v1/disciplines/{id} (PATCH)
### Body
    {
    	"period": "2ª semestre",
    }
### Response
    ENTIDADE PERIODOS

## Decisões do projeto

O uso de Spring Boot, Spring MVC e Spring Security com JWT em um projeto oferece diversas vantagens e recursos que contribuem para o desenvolvimento de aplicativos web seguros e eficientes. Aqui estão algumas razões pelas quais essas tecnologias são frequentemente escolhidas em conjunto:

1. **Spring Boot**: O Spring Boot é uma estrutura que simplifica a configuração e o desenvolvimento de aplicativos Spring. Ele fornece um ambiente de execução pré-configurado, permitindo que os desenvolvedores se concentrem no código do aplicativo em vez de configurações complexas. O Spring Boot é conhecido por sua facilidade de uso e produtividade, o que acelera o desenvolvimento de aplicativos.

2. **Spring MVC**: O Spring MVC é um módulo do Spring Framework que facilita o desenvolvimento de aplicativos web baseados em padrões MVC (Model-View-Controller). Ele oferece uma estrutura robusta para criar APIs RESTful e aplicativos da web, permitindo um controle eficiente das solicitações HTTP, roteamento e renderização de visualizações.

3. **Spring Security**: A segurança é uma preocupação fundamental em qualquer aplicativo web. O Spring Security é uma estrutura que oferece recursos abrangentes para autenticação e autorização. Ele facilita a implementação de autenticação de usuários, proteção contra ameaças comuns, como CSRF (Cross-Site Request Forgery) e XSS (Cross-Site Scripting), e controle de acesso baseado em papéis.

4. **JSON Web Tokens (JWT)**: JWT é um padrão aberto para representar informações entre duas partes de forma segura e compacta. Ele é frequentemente usado como um mecanismo de autenticação e autorização em aplicativos web e APIs RESTful. O uso de JWT com o Spring Security permite a autenticação baseada em tokens, o que é altamente escalável e adequado para aplicativos distribuídos.

Combinar Spring Boot, Spring MVC e Spring Security com JWT oferece uma base sólida para o desenvolvimento de aplicativos web modernos e seguros. Isso permite aos desenvolvedores criar rapidamente APIs RESTful seguras e aplicativos da web, além de controlar o acesso de usuários de forma flexível e escalável, tornando essas tecnologias uma escolha popular para uma ampla gama de projetos.

E, além do mais, outro motivo importante foi o estudo das tecnologias usadas tendo em vista o desafio de aprender/evolui-las em tempo hábil

## Planos para o projeto

- [x] Utilizar Springboot
- [x] Criar modelo e relacionamento do banco
- [x] Utilizar o postgres
- [x] Utilizar Hibernate JPA
- [x] Utilizar Spring Security com JWT
- [ ] Documentar com Swagger
- [x] Utilizar controle de Rotas por Roles
- [ ] Criar testes unitários e de integração
- [x] Utilizar Docker para rodar a aplicação e o banco de dados
- [ ] Fazer deploy da aplicação
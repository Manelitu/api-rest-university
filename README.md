# Visão geral

O projeto é uma aplicação back-end com objetivo de construir uma APIs utilizando os frameworks [Spring Boot](https://projects.spring.io/spring-boot), [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) e [Spring Security](https://spring.io/projects/spring-security) 
para administrar alunos, professores e cursos de uma universidade.

## Tecnologias

- [Spring Boot](https://projects.spring.io/spring-boot) é uma ferramenta que simplifica a configuração e execução de aplicações Java stand-alone,  com conceitos de dependências “starters”, auto configuração e servlet container embutidos é proporcionado uma grande produtividade desde o start-up da aplicação até sua ida a produção.
 
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) é um framework já consolidado no mercado, que a partir da versão fornece mecanismos simplificados para a criação de APIs RESTful através de anotação, além disso, também possui recursos de serialização e deserialização de objetos de forma transparente 
 
- [Spring Security](https://spring.io/projects/spring-security) é um framework do projeto Spring que possui um sistema de autenticação e autorização de alto nível e altamente customizável para aplicações Java. O framework inclusive é a solução oficial para implementação de recursos de segurança em aplicações Spring Boot, porém ele também pode ser utilizado em conjunto com outras frameworks.

 
# Setup da aplicação (local)

## Pré-requisito

Fazer o clone da aplicação em seu computador
```
(HTTPS) git clone https://github.com/Manelitu/api-rest-university.git
(SSH) git clone git@github.com:Manelitu/api-rest-university.git
```

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

# APIs

O projeto disponibiliza algumas APIs em 5 contextos diferentes: Auth, Users, Courses, Disciplines e Periods, onde utilizam o padrão Rest de comunicação, produzindo e consumindo arquivos no formato JSON.

Segue abaixo as APIs disponíveis no projeto:

#### Auth
 - api/v1/auth/login (POST)
     - Schema do body:
    ```
    {
    	"login": "user@admin.com",
    	"password": "admin",
    }
  A validação é feita pelo Spring Security e JWT onde será enviado um token para a camada de View e será utilizada para a validação de permissões no FRONT.
  
#### Users

 - api/v1/users (GET) 
 - api/v1/users/{id} (GET)
 - api/v1/users/{id} (DELETE)
 - api/v1/users/no-auth (POST) - não necessita de Autenticação (Caso de primeiro usuário para testes)
 - api/v1/users (POST)
     - Schema do body para ambos os (POST):
    ```
    {
    	"name": "Emanuel Victor de Lima",
    	"email": "user@admin.com",
    	"password": "admin",
    	"roles": "STUDENT"
    }

    No qual as roles podem ser:
    ADMIN
    COORDINATOR
    PROFESSOR
    STUDENT
    ```
  - api/v1/users/{id} (PATCH)
     -  Schema do body:
    ```
    {
    	"name": "Emanuel Victor de Lima",
    	"email": "user@admin.com",
    }
    ```

#### Courses

  - api/v1/courses (GET)
  - api/v1/courses/{id} (DELETE)
  - api/v1/courses/{id} (GET)
  - api/v1/courses (POST)
  - api/v1/courses/{id} (PATCH)
     -  Schema do body para ambos (POST) e (PATCH):
    ```
    {
    	"name": "Ed. Fisica",
    	"description": "Descrição"
    }
    ```
 
 #### Disciplines

  - api/v1/disciplines (GET)
  - api/v1/disciplines/{id} (DELETE)
  - api/v1/disciplines/{id} (GET)
  - api/v1/disciplines (POST)
     -  Schema do body:
    ```
    {
    	"name": "Lógica de programação",
    	"hours": 80,
      "description": "descrição",
    	"periodId": "9c64bd24-7f7e-47a7-9aba-b31ea431a2d6"
    }
    ```            
  - api/v1/disciplines/{id} (PATCH)
     -  Schema do body:
    ```
    {
    	"name": "Lógica de programação",
    	"hours": 80,
      "description": "descrição",
    }
    ```
    
 #### Disciplines

  - api/v1/period (GET)
  - api/v1/period/{id} (DELETE)
  - api/v1/period/{id} (GET)
  - api/v1/period (POST)
     -  Schema do body:
    ```
    {
    	"period": "1ª semestre",
    	"courseId": "04d7da55-2c1d-47d6-96da-87ade35fbc70"
    }
    ```            
  - api/v1/disciplines/{id} (PATCH)
     -  Schema do body:
    ```
    {
    	"period": "2ª semestre",
    }
    ```


    ## Decisões do projeto

    O uso de Spring Boot, Spring MVC e Spring Security com JWT em um projeto oferece diversas vantagens e recursos que contribuem para o desenvolvimento de aplicativos web seguros e eficientes. Aqui estão algumas razões pelas quais essas tecnologias são frequentemente escolhidas em conjunto:

1. **Spring Boot**: O Spring Boot é uma estrutura que simplifica a configuração e o desenvolvimento de aplicativos Spring. Ele fornece um ambiente de execução pré-configurado, permitindo que os desenvolvedores se concentrem no código do aplicativo em vez de configurações complexas. O Spring Boot é conhecido por sua facilidade de uso e produtividade, o que acelera o desenvolvimento de aplicativos.

2. **Spring MVC**: O Spring MVC é um módulo do Spring Framework que facilita o desenvolvimento de aplicativos web baseados em padrões MVC (Model-View-Controller). Ele oferece uma estrutura robusta para criar APIs RESTful e aplicativos da web, permitindo um controle eficiente das solicitações HTTP, roteamento e renderização de visualizações.

3. **Spring Security**: A segurança é uma preocupação fundamental em qualquer aplicativo web. O Spring Security é uma estrutura que oferece recursos abrangentes para autenticação e autorização. Ele facilita a implementação de autenticação de usuários, proteção contra ameaças comuns, como CSRF (Cross-Site Request Forgery) e XSS (Cross-Site Scripting), e controle de acesso baseado em papéis.

4. **JSON Web Tokens (JWT)**: JWT é um padrão aberto para representar informações entre duas partes de forma segura e compacta. Ele é frequentemente usado como um mecanismo de autenticação e autorização em aplicativos web e APIs RESTful. O uso de JWT com o Spring Security permite a autenticação baseada em tokens, o que é altamente escalável e adequado para aplicativos distribuídos.

Combinar Spring Boot, Spring MVC e Spring Security com JWT oferece uma base sólida para o desenvolvimento de aplicativos web modernos e seguros. Isso permite aos desenvolvedores criar rapidamente APIs RESTful seguras e aplicativos da web, além de controlar o acesso de usuários de forma flexível e escalável, tornando essas tecnologias uma escolha popular para uma ampla gama de projetos.

E, além do mais, outro motivo importante foi o estudo das tecnologias usadas tendo em vista o desafio de aprender/evolui-las em tempo hábil
    

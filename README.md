# API Pokédex Avançada

Uma API RESTful completa e segura construída com Spring Boot 3 e Java 21, projetada para gerenciar Treinadores, seus Pokémons capturados, e implementar uma lógica avançada de "Modo Jogo" (Visto vs. Capturado).

Este projeto não se limita a um CRUD local; ele atua como um "Proxy" inteligente para a [PokéAPI](https://pokeapi.co/) externa, permitindo consultar habitats, cadeias de evolução e "enriquecer" os dados locais com descrições, tipos, stats e sons. A API é totalmente protegida por **Spring Security 6** e **JWT (JSON Web Tokens)**, com um sistema de **Roles** (Usuário vs. Admin).

-----

## ✨ Principais Funcionalidades

A API é dividida em várias áreas de domínio:

### 🛡️ 1. Segurança & Administração (Spring Security + JWT)

  * **Autenticação JWT:** Endpoints públicos (`/api/auth/register` e `/api/auth/login`) para registro de usuário e obtenção de token.
  * **Autorização por Papel (Roles):**
      * **`ROLE_USER`:** Papel padrão no registro. Pode capturar, ver, trocar e importar Pokémons.
      * **`ROLE_ADMIN`:** Tem permissões de `USER` e é o **único** que pode **Deletar** ou **Atualizar (PUT)** dados (Pokémons, Treinadores).
  * **Criação Automática de Admin:** Um "Super Admin" é criado automaticamente na primeira inicialização da API (configurável no `application.properties`).
  * **Endpoint de Promoção:** Endpoint (`POST /api/admin/promote/{username}`) protegido que permite a um Admin promover outros usuários para `ROLE_ADMIN`.

### 🗃️ 2. Gerenciamento Central (CRUD)

  * **Treinadores:** CRUD completo (Criar, Ler, Atualizar, Deletar) para Treinadores.
  * **Pokémons (Capturados):** CRUD completo para Pokémons *possuídos* por treinadores.
  * **Trocas:** Endpoint (`POST /api/troca`) para realizar a troca de Pokémons entre dois treinadores, com registro automático em um histórico (`GET /api/troca/historico`).

### 🎮 3. "Modo Jogo" (Sistema de Pokédex)

  * **Registro de "Visto":** Endpoint (`POST /api/pokedex-entries/ver`) que permite a um treinador registrar que *viu* um Pokémon, criando uma entrada na sua Pokédex.
  * **Registro de "Capturado" (Automático):** Quando um treinador captura um Pokémon (usando `POST /api/pokedex` ou `POST /api/import`), o sistema automaticamente cria ou atualiza a entrada da Pokédex para o status "CAPTURADO".
  * **Consulta de Pokédex:** Endpoint (`GET /api/pokedex-entries/treinador/{id}`) que lista todas as entradas (vistas e capturadas) da Pokédex de um treinador específico.

### 🌐 4. Integração com PokéAPI Externa

  * **Importação (`/api/import`):** Permite buscar um Pokémon (ou um lote) da PokéAPI e salvá-lo diretamente no banco de dados local como "capturado" por um treinador.
  * **Enriquecimento (`POST /api/pokedex/{id}/enriquecer`):** Endpoint que busca um Pokémon *local* pelo seu ID, chama a PokéAPI externa para obter dados ricos (Tipos, Stats, Descrição e Som/Choro) e os salva permanentemente no banco local.
  * **"Modo Proxy" (Consulta ao Vivo):** Endpoints que consultam a PokéAPI em tempo real sem salvar nada no banco:
      * `GET /api/pokedex-externa/{nome}`: Retorna os dados completos do Pokémon.
      * `GET /api/pokedex-externa/{nome}/habitat`: Retorna uma lista de locais onde o Pokémon pode ser encontrado.
      * `GET /api/pokedex-externa/{nome}/evolucao`: Retorna a cadeia de evolução completa (ex: "eevee", "vaporeon", "jolteon"...).

-----

## 🛠️ Tecnologias Utilizadas

  * **Java 21**
  * **Spring Boot 3.3.0**
  * **Spring Security 6 (com JWT):** Para autenticação e autorização.
  * **Spring Data JPA (Hibernate):** Para persistência de dados.
  * **MySQL:** Banco de dados relacional.
  * **Docker:** Para rodar o banco de dados MySQL facilmente.
  * **Maven:** Gerenciador de dependências.
  * **MapStruct:** Para mapeamento performático entre DTOs e Entidades.
  * **SpringDoc (Swagger/OpenAPI):** Para documentação da API.
  * **RestTemplate:** Para consumir a PokéAPI externa.

-----

## 🚀 Como Começar

Siga os passos abaixo para rodar o projeto localmente.

### Pré-requisitos

  * **JDK 21** (ou superior)
  * **Maven 3.8** (ou superior)
  * **Docker Desktop** (ou uma instalação local do MySQL 8+)

### 1\. Clone o Repositório

```bash
git clone [https://github.com/leandrochs/pokedex.git]
cd pokedex
```

### 2\. Inicie o Banco de Dados (MySQL com Docker)

Este é o método recomendado. Rode no seu terminal:

```bash
docker run -d -p 3306:3306 --name mysql-pokedex -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=pokedex mysql:8
```

  * **Importante:** Substitua `admin` pela senha que você deseja usar.

### 3\. Configure a Aplicação

Abra o arquivo `src/main/resources/application.properties` e garanta que as configurações batem com seu banco de dados e as credenciais de admin desejadas:

```properties
spring.application.name=pokedex

# Configuração do banco de dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/pokedex
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JPA / Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# DevTools (hot reload)
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true

# Swagger UI
springdoc.swagger-ui.path=/swagger-ui.html

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Security
# IMPORTANTE: Use um gerador de HASH (ex: SHA-256) online para criar
jwt.secret.key=e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855

# --- Configuração do Super Admin Padrão ---
#MUDE ISSO EM PRODUÇÃO
admin.default.username=admin
admin.default.password=admin123

```

### 4\. Rode o Projeto

Você pode rodar pela sua IDE (abrindo a classe `PokedexApplication.java` e clicando em "Run") ou usando o Maven:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

-----

## 🔌 Uso da API e Fluxo de Segurança

Após iniciar a aplicação, acesse a documentação completa e interativa do **Swagger UI**:

➡️ [**http://localhost:8080/swagger-ui.html**](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

### Fluxo de Autenticação e Admin (Obrigatório)

Como a API é protegida, você precisa se autenticar.

1.  **Crie um Usuário:**

      * Vá ao endpoint `POST /api/auth/register`.
      * Crie um usuário (ex: `{"username": "user", "password": "password123"}`).
      * Este usuário terá o papel `ROLE_USER`.

2.  **Faça Login (como Admin):**

      * O `AdminDataSeeder` já criou o admin para você (ex: `admin` / `admin123`).
      * Vá ao endpoint `POST /api/auth/login`.
      * Faça login com as credenciais do Admin para obter um token JWT.

3.  **Autorize-se no Swagger:**

      * No Swagger UI, clique no botão **"Authorize"** (canto superior direito).
      * Na caixa de diálogo, digite ` Bearer  ` (com espaço) e cole o token que você recebeu.
      * **Exemplo:** `Bearer eyJhbGciOiJIUzI1NiJ9...`
      * Clique em "Authorize" e feche a caixa.

4.  **Use os Endpoints Protegidos:**

      * Agora você está logado como Admin e pode testar **todos** os endpoints, incluindo os protegidos (`DELETE`, `PUT`, etc.).

5.  **(Opcional) Promova seu usuário:**

      * Para testar a promoção, use o endpoint `POST /api/admin/promote/{username}` (ex: `POST /api/admin/promote/user`).
      * Agora, se você fizer login como `user`, ele também terá permissões de Admin.

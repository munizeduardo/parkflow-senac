# ParkFlow

Sistema integrado de gestão e reserva de vagas de estacionamento.

O ParkFlow é uma aplicação web que centraliza a consulta, reserva e gestão de
vagas em estacionamentos privados. Permite que motoristas consultem a
disponibilidade, selecionem uma vaga, cadastrem seus veículos e realizem ou
cancelem reservas. Administradores contam com um painel para gerenciar vagas,
consultar reservas e acompanhar a ocupação.

## Tecnologias

- **Front-end:** Next.js 15 (React) — App Router
- **Back-end:** Java 23 + Spring Boot 3 (API REST)
- **Banco de dados:** PostgreSQL

## Pré-requisitos

Antes de executar o projeto, verifique se os itens abaixo estão instalados:

| Dependência       | Versão necessária        | Para que é usada                        |
| ----------------- | ------------------------ | --------------------------------------- |
| JDK (Java)        | 23 (Temurin recomendado) | Compilar e rodar o back-end Spring Boot |
| Node.js           | 18.18+ (recomendado 20+) | Executar o front-end Next.js            |
| npm               | vem com o Node.js        | Gerenciar dependências do front-end     |
| PostgreSQL        | 16 (14+ é compatível)    | Banco de dados da aplicação             |
| Docker (opcional) | versão recente           | Subir o PostgreSQL sem instalação local |

> O **Maven não precisa ser instalado**: o projeto usa o Maven Wrapper
> (`./mvnw`), que baixa a versão correta automaticamente. É necessário apenas
> ter o JDK configurado na variável de ambiente `JAVA_HOME`.

### Verificando as versões

```bash
java -version      # deve exibir "23.x"
node -v            # deve exibir "v18.x" ou superior
npm -v
psql --version     # deve exibir "16.x" (ou 14+)
docker --version   # opcional
```

### Configurando o JDK

Caso o JDK esteja instalado mas fora do PATH:

```bash
export JAVA_HOME=/caminho/para/jdk-23
export PATH="$JAVA_HOME/bin:$PATH"
```

## Estrutura do projeto

```
parkflow/
├── back-end/          # API REST Spring Boot
│   └── src/main/java/dev/parkflow/
│       ├── config/       # Configurações de segurança
│       ├── controllers/  # Endpoints REST
│       ├── dtos/         # DTOs de requisição e resposta
│       ├── entities/     # Entidades JPA (Usuario, Veiculo, Vaga, Reserva)
│       ├── repositories/ # Repositórios Spring Data JPA
│       └── services/     # Regras de negócio
├── database/
│   └── postgresql/    # Scripts SQL (schema e dados de exemplo)
└── front-end/         # Aplicação Next.js
    └── app/
        ├── admin/     # Dashboard e gestão (vagas e reservas)
        ├── vagas/     # Consulta de disponibilidade
        ├── reservas/  # Nova reserva e minhas reservas
        ├── login/     # Autenticação
        └── cadastro/  # Cadastro de usuário
```

## Como executar

### 1. Banco de dados (PostgreSQL)

Crie o banco e execute os scripts:

```sql
CREATE DATABASE parkflow;
```

```bash
psql -U postgres -d parkflow -f database/postgresql/schema.sql
psql -U postgres -d parkflow -f database/postgresql/populate.sql
```

> O back-end também cria/atualiza as tabelas automaticamente (`ddl-auto=update`).
> Configure usuário/senha do banco em `back-end/src/main/resources/application.properties`.

### 2. Back-end

```bash
cd back-end
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080`.

### 3. Front-end

```bash
cd front-end
npm install
npm run dev
```

A aplicação fica disponível em `http://localhost:3000`.

## Usuários de teste

Senha de todos os usuários de exemplo: `123456`

| Perfil        | Email              |
| ------------- | ------------------ |
| Administrador | admin@parkflow.com |
| Usuário       | andre@parkflow.com |

## Principais endpoints

| Método | Endpoint                        | Descrição                              |
| ------ | ------------------------------- | -------------------------------------- |
| POST   | `/usuarios/login`               | Autenticar usuário (identifica perfil) |
| POST   | `/usuarios/cadastro`            | Cadastrar usuário                      |
| GET    | `/vagas`                        | Listar todas as vagas                  |
| GET    | `/vagas/disponibilidade`        | Vagas disponíveis em um período        |
| GET    | `/vagas/ocupacao`               | Indicadores de ocupação                |
| POST   | `/vagas`                        | Cadastrar vaga                         |
| PUT    | `/vagas/{id}`                   | Atualizar vaga (status)                |
| DELETE | `/vagas/{id}`                   | Excluir vaga                           |
| POST   | `/reservas`                     | Criar reserva (valida sobreposição)    |
| GET    | `/reservas`                     | Listar todas as reservas               |
| GET    | `/reservas/usuario/{idUsuario}` | Reservas de um usuário                 |
| PUT    | `/reservas/{id}/cancelar`       | Cancelar reserva                       |
| GET    | `/veiculos/usuario/{idUsuario}` | Veículos de um usuário                 |
| POST   | `/veiculos`                     | Cadastrar veículo                      |

## Escopo (MVP)

Funcionalidades contempladas: autenticação, identificação de perfil, consulta de
disponibilidade, seleção de vaga, cadastro de veículo, criação e cancelamento de
reservas, consulta de reservas e gerenciamento administrativo de vagas e reservas.

Fora do escopo inicial: pagamento online, reconhecimento automático de placas,
GPS/geolocalização, integração com cancelas, aplicativo nativo e notificações.

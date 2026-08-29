# 🚗 Avaliação Prática — JDBC

Aplicação desenvolvida em **Java** utilizando **JDBC (Java Database Connectivity)** para gerenciamento e consulta de dados de telemetria de motores industriais.

O projeto foi desenvolvido como uma avaliação prática com o objetivo de aplicar conceitos de acesso a banco de dados utilizando JDBC, organização em camadas e integração com **MySQL**.

## 📋 Sobre o projeto

A aplicação permite trabalhar com informações relacionadas a:

* ⚙️ Motores
* 🏭 Setores
* 📊 Telemetria dos motores
* 🚨 Alertas de motores
* 📜 Histórico de telemetria
* 📈 Resumos e consultas relacionadas aos motores

O projeto utiliza uma arquitetura organizada em diferentes responsabilidades, separando modelos, acesso aos dados, regras de negócio e interface de interação com o usuário.

## 🛠️ Tecnologias utilizadas

| Tecnologia   | Utilização                                 |
| ------------ | ------------------------------------------ |
| ☕ Java 21    | Linguagem de programação                   |
| 🔌 JDBC      | Conexão e comunicação com o banco de dados |
| 🐬 MySQL 8.0 | Banco de dados relacional                  |
| 📦 Maven     | Gerenciamento do projeto e dependências    |
| 🐳 Docker    | Containerização do banco de dados          |
| 🖥️ Console  | Interface de interação com a aplicação     |

O `pom.xml` do projeto está configurado para **Java 21** e utiliza o **MySQL Connector/J 9.4.0** para comunicação com o banco.

## 🏗️ Estrutura do projeto

```text
Avaliacao-Prova-Pratica-JDBC/
│
├── docker/
│   ├── init-db/
│   │   └── 01-init.sql
│   │
│   └── docker-compose.yml
│
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── ctw/
│                   ├── config/
│                   │   └── ConnectionFactory.java
│                   │
│                   ├── dao/
│                   │   ├── AlertaMotorDAO.java
│                   │   ├── MotorDAO.java
│                   │   ├── SetorDAO.java
│                   │   └── TelemetriaDAO.java
│                   │
│                   ├── exception/
│                   │
│                   ├── model/
│                   │   ├── AlertaMotor.java
│                   │   ├── HistoricoTelemetria.java
│                   │   ├── Motor.java
│                   │   ├── MotorResumo.java
│                   │   └── Setor.java
│                   │
│                   ├── service/
│                   │   ├── AlertaMotorService.java
│                   │   ├── MotorService.java
│                   │   ├── SetorService.java
│                   │   └── TelemetriaService.java
│                   │
│                   ├── view/
│                   │   └── MenuConsole.java
│                   │
│                   └── Main.java
│
├── .gitignore
└── pom.xml
```

A estrutura acima pode ser conferida diretamente no repositório.

## 🧩 Arquitetura

O projeto foi organizado em camadas para separar as responsabilidades da aplicação.

### `config`

Responsável pela configuração da conexão com o banco de dados.

A classe `ConnectionFactory` centraliza a criação das conexões JDBC utilizando `DriverManager`.

### `model`

Contém as classes que representam as entidades e objetos utilizados pela aplicação:

* `Motor`
* `Setor`
* `AlertaMotor`
* `HistoricoTelemetria`
* `MotorResumo`

### `dao`

Responsável pelo acesso direto ao banco de dados através do JDBC.

Principais DAOs:

* `MotorDAO`
* `SetorDAO`
* `TelemetriaDAO`
* `AlertaMotorDAO`

Essa camada concentra as operações de persistência e consultas SQL.

### `service`

Responsável por intermediar as operações entre a aplicação e os DAOs, concentrando as regras e operações de negócio.

Serviços disponíveis:

* `MotorService`
* `SetorService`
* `TelemetriaService`
* `AlertaMotorService`

### `view`

Contém a interface de interação com o usuário através do console.

A classe principal dessa camada é `MenuConsole`.

### `Main`

Classe responsável pelo ponto de entrada da aplicação.

---

## 🗄️ Banco de dados

O projeto utiliza **MySQL 8.0** através de um container Docker.

O banco utilizado pela aplicação é:

```text
TelemetriaMotores
```

O Docker Compose configura o MySQL com:

```text
Host: localhost
Porta: 3307
Banco: TelemetriaMotores
Usuário: industria_user
```

A configuração do projeto utiliza a seguinte URL JDBC:

```text
jdbc:mysql://localhost:3307/TelemetriaMotores
```

O Docker Compose também monta o diretório `docker/init-db` no diretório de inicialização do MySQL, permitindo que o script SQL seja executado automaticamente na criação do banco.

> **⚠️ Atenção:** as credenciais presentes no projeto são destinadas ao ambiente de desenvolvimento/avaliação. Em aplicações reais, recomenda-se utilizar variáveis de ambiente ou um mecanismo seguro de gerenciamento de credenciais.

## 🐳 Executando o banco com Docker

### 1. Clone o repositório

```bash
git clone https://github.com/arthurSchgg/Avaliacao-Prova-Pratica-JDBC.git
```

### 2. Entre no diretório

```bash
cd Avaliacao-Prova-Pratica-JDBC
```

### 3. Inicie o MySQL

Entre na pasta `docker`:

```bash
cd docker
```

Execute:

```bash
docker compose up -d
```

O container MySQL será iniciado utilizando a configuração definida no `docker-compose.yml`.

### 4. Verifique o container

```bash
docker ps
```

Você deverá encontrar o container:

```text
mysql_telemetria_industria
```

O MySQL estará disponível na porta:

```text
3307
```

## ☕ Executando a aplicação

Após iniciar o banco de dados, volte para a raiz do projeto:

```bash
cd ..
```

Compile o projeto utilizando o Maven:

```bash
mvn clean package
```

Depois, execute a aplicação através da classe:

```text
org.ctw.Main
```

Também é possível executar diretamente pela IDE, configurando `Main` como a classe principal.

## 🔌 Dependência JDBC

O projeto utiliza o driver oficial do MySQL:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.4.0</version>
</dependency>
```

Essa dependência é responsável por permitir que a aplicação Java estabeleça conexões com o MySQL através do JDBC.

## 🔄 Fluxo da aplicação

O fluxo básico da aplicação segue a seguinte estrutura:

```text
                 ┌──────────────┐
                 │    Main      │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │ MenuConsole  │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │   Service    │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │     DAO      │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │    JDBC      │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │    MySQL     │
                 └──────────────┘
```

Essa separação facilita a manutenção do código e evita concentrar toda a lógica da aplicação em uma única classe.

## 🎯 Objetivos acadêmicos

O projeto permite praticar conceitos importantes de desenvolvimento Java, como:

* Conexão com banco de dados utilizando JDBC;
* Execução de comandos SQL;
* Operações de consulta e persistência;
* Separação de responsabilidades;
* Padrão DAO;
* Camada de serviços;
* Modelagem de entidades;
* Tratamento de exceções;
* Organização de projetos Maven;
* Utilização de Docker para ambiente de banco de dados.

## 📚 Conceitos de JDBC aplicados

Entre os principais conceitos trabalhados estão:

```text
DriverManager
     ↓
Connection
     ↓
PreparedStatement / Statement
     ↓
ResultSet
     ↓
Banco de Dados
```

A classe `ConnectionFactory` centraliza a obtenção de conexões, enquanto os DAOs são responsáveis pelas operações relacionadas às entidades do sistema.

## 🚨 Alertas e telemetria

O sistema possui uma estrutura específica para trabalhar com informações de telemetria e alertas de motores.

Isso inclui classes específicas para:

* Registro e consulta de telemetria;
* Histórico de telemetria;
* Informações dos motores;
* Resumo dos motores;
* Alertas relacionados aos motores.

## 👨‍💻 Autor

Desenvolvido por **Arthur Miguel Schlichting** como parte de uma avaliação prática de desenvolvimento Java com JDBC, no CentroWEG.

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de avaliação prática.

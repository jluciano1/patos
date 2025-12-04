# Projeto Granja de Patos 🦆

Este repositório contém um projeto para Granja de Patos construído com **SpringBoot 3.3.5** e **Java 21 (LTS)**. Este é um projeto Spring Boot configurado para gerar um artefato **WAR**, utilizando Java 21, Liquibase, PostgreSQL, JPA, validação, Swagger Annotations e outras bibliotecas adicionais.

---

## 📦 Tecnologias utilizadas

- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Validation (Jakarta Validation)
- Lombok
- H2 Database (ambiente de desenvolvimento)
- PostgreSQL (produção)
- Liquibase
- Apache POI (manipulação de Excel)
- Guava
- Caelum Stella
- Swagger Annotations

---

## 📚 Dependências do Maven
Abaixo está a lista de dependências utilizadas no projeto.

### **Spring Boot Web**
```
org.springframework.boot : spring-boot-starter-web
```
Fornece:
- Servidor embutido (Tomcat)
- Suporte a APIs REST
- MVC

### **Spring Data JPA**
```
org.springframework.boot : spring-boot-starter-data-jpa
```
Permite:
- ORM com Hibernate
- Repositórios JPA
- Integração com bancos SQL

### **H2 Database**
```
com.h2database : h2
```
Banco em memória para testes.

### **Lombok**
```
org.projectlombok : lombok
```
Gera getters, setters, construtores e builders automaticamente.

### **Spring Boot Starter Test**
```
org.springframework.boot : spring-boot-starter-test
```
Dependências para testes unitários e de integração.

### **Apache POI – Excel**
```
org.apache.poi : poi-ooxml
```
Manipulação de arquivos Excel `.xlsx`.

### **Swagger Annotations**
```
io.swagger.core.v3 : swagger-annotations
```
Permite anotar métodos HTTP com informações Swagger/OpenAPI.

### **Jakarta Validation API**
```
jakarta.validation : jakarta.validation-api
```
Padroniza validações com anotações como `@NotNull`, `@Size`, etc.

### **Spring Boot Starter Validation**
```
org.springframework.boot : spring-boot-starter-validation
```
Implementação integrada do Hibernate Validator.

### **Caelum Stella Core**
```
br.com.caelum.stella : caelum-stella-core
```
Biblioteca para validação de documentos brasileiros (CPF, CNPJ, etc.).

### **Guava**
```
com.google.guava : guava
```
Coleções utilitárias avançadas, caching, strings, e algoritmos.

### **Driver PostgreSQL**
```
org.postgresql : postgresql
```
Driver JDBC oficial para PostgreSQL.

### **Liquibase Core**
```
org.liquibase : liquibase-core
```

### **Java**
- **Java 21** (conforme definido no `pom.xml`)

### **Maven**
- **Mínimo recomendado:** `Apache Maven 3.6.3`
- **Recomendado:** `Maven 3.8.x` ou superior

> Necessário para garantir compatibilidade com **Spring Boot 3.3.5**.

---

## 📁 Estrutura de Build
O projeto utiliza:

```xml
<packaging>war</packaging>
```

Isso significa que ele pode ser empacotado como WAR para deployment em servidores como Tomcat, JBoss ou Payara.

---

## ▶️ Como executar o projeto
### Via Maven:
```bash
mvn spring-boot:run
```

### Gerar artefato WAR:
```bash
mvn clean package
```
O WAR será gerado em:
```
target/patos-0.0.1-SNAPSHOT.war
```

---

## 🗄️ Migrações de Banco (Liquibase)
O Liquibase é carregado automaticamente na inicialização, lendo arquivos de `classpath:/db/changelog/`.

---

## 🐘 Configuração do PostgreSQL
No `application.properties` ou `application.yml` é esperado algo como:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/patos
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=none
spring.liquibase.enabled=true
```

---

## 🧪 Testes
O projeto usa:
- **Spring Boot Starter Test**

Executar testes:
```bash
mvn test
```

---

## 📄 Licença
Projeto de uso interno/estudo — modifique conforme necessário.

---

## 🧱 Arquitetura do Sistema
A arquitetura segue o padrão **MVC** comum em aplicações Spring Boot:

```
┌──────────────────────────────┐
│        Controller (API)       │  → Recebe requisições REST
└───────────────┬──────────────┘
                │
┌───────────────▼──────────────┐
│          Service Layer        │  → Regras de negócio
└───────────────┬──────────────┘
                │
┌───────────────▼──────────────┐
│          Repository           │  → JPA / ORM
└───────────────┬──────────────┘
                │
┌───────────────▼──────────────┐
│      Banco de Dados (SQL)     │ → PostgreSQL / H2
└──────────────────────────────┘
```

## 🗄️ Modelo de Dados

### **🦆 Entidade: Pato** (`pato`)
```
Pato
├── id : Long
├── nome : String
├── mae : Pato (ManyToOne - mãe do pato)
└── status : String
```

### **👤 Entidade: Cliente** (`cliente`)
```
Cliente
├── id : Long
├── nome : String
└── desconto : Boolean
```

### **🧑‍💼 Entidade: Vendedor** (`vendedor`)
```
Vendedor
├── id : Long
├── nome : String
├── cpf : Long
└── matricula : String
```

### **💰 Entidade: Venda** (`venda`)
```
Venda
├── id : Long
├── idCliente : Long  (FK lógica → Cliente)
├── idVendedor : Long (FK lógica → Vendedor)
├── idPato : Long      (FK lógica → Pato)
├── valor : BigDecimal
└── dataHora : Date
```
> Observação: A entidade *Venda* não utiliza relacionamentos JPA, e sim campos primitivos que funcionam como chaves estrangeiras lógicas.

---

## 🔧 Instalação Passo a Passo

### **1. Clonar o projeto**
```bash
git clone https://github.com/seu-repositorio/patos.git
cd patos
```

### **2. Verificar versão do Java**
```bash
java -version
```
> Deve ser **Java 21**.

### **3. Verificar versão do Maven**
```bash
mvn -v
```
> Necessário **Maven 3.6.3+**.

### **4. Instalar dependências**
```bash
mvn clean install
```

### **5. Rodar aplicação**
```bash
mvn spring-boot:run
```

### **6. Acessar API**
```
http://localhost:8080/granja
```

---

## 📘 Documentação Swagger / OpenAPI
Os endpoints estão disponíveis em:
```
http://localhost:8080/swagger-ui.html
```

## 🧩 Diagrama ER (Entidade-Relacionamento)

```mermaid
    PATO {
        Long id
        String nome
        String status
        Long mae_id
    }

    CLIENTE {
        Long id
        String nome
        Boolean desconto
    }

    VENDEDOR {
        Long id
        String nome
        Long cpf
        String matricula
    }

    VENDA {
        Long id
        Long idCliente
        Long idVendedor
        Long idPato
        BigDecimal valor
        Date dataHora
    }

    PATO ||--|{ PATO : "é mãe de"
    CLIENTE ||--o{ VENDA : efetua
    VENDEDOR ||--o{ VENDA : realiza
    PATO ||--o{ VENDA : é vendido
```

---

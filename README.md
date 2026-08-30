# Assistente Financeiro Inteligente com Spring AI

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Groq](https://img.shields.io/badge/Groq_API-F55036?style=for-the-badge&logo=groq&logoColor=white)

Uma API REST robusta desenvolvida em Java e Spring Boot que atua como um assistente financeiro pessoal. O sistema utiliza Inteligência Artificial (Spring AI + Groq) para interpretar linguagem natural em formato de **texto** ou **áudio** (Whisper), classificando despesas e receitas automaticamente e persistindo os dados em um banco relacional PostgreSQL.

## Arquitetura e Fluxo de Funcionamento

1. O usuário envia um comando por texto ou grava um áudio (ex: *"Gastei 50 reais de gasolina hoje"*).
2. Se for áudio, a API consome o modelo **Whisper** para transcrever a voz para texto.
3. O texto é enviado para um LLM (via Groq API) utilizando a funcionalidade de **Function Calling**.
4. A IA decide, de forma autônoma, qual método Java acionar:
   - `registrarTransacao`: Extrai valor, descrição e tipo (receita/despesa) e salva no banco.
   - `consultarSaldo`: Soma receitas e subtrai despesas para devolver o saldo atualizado.
5. Os dados são persistidos de forma segura no **PostgreSQL** através do Hibernate/JPA.

## Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.3.x
* **Inteligência Artificial:** Spring AI, Groq API, Whisper
* **Banco de Dados:** PostgreSQL 16
* **ORM:** Spring Data JPA / Hibernate
* **Gerenciador de Dependências:** Gradle

##  Como Configurar e Rodar o Projeto

### Pré-requisitos
* JDK 17+ instalado
* PostgreSQL instalado e rodando na porta 5432
* Uma chave de API da Groq

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/SEU_USUARIO/assistente-financeiro-spring-ia.git
   cd assistente-financeiro-spring-ai
   ```

2. **Crie o Banco de Dados:**
   Abra o pgAdmin 4 ou seu terminal e crie um banco chamado `orcamentodb`.

3. **Configure as Variáveis de Ambiente:**
   Abra o arquivo `src/main/resources/application.properties` e preencha com as suas credenciais:
   ```properties
   # Configurações do PostgreSQL
   spring.datasource.url=jdbc:postgresql://localhost:5432/orcamentodb
   spring.datasource.username=postgres
   spring.datasource.password=SUA_SENHA_DO_POSTGRES
   spring.jpa.hibernate.ddl-auto=update

   # Configurações da API de IA
   spring.ai.openai.api-key=SUA_CHAVE_API
   spring.ai.openai.base-url=https://api.groq.com/openai/v1
   ```

4. **Execute a aplicação:**
   Rode pela sua IDE ou pelo terminal utilizando o Gradle:
   ```bash
   ./gradlew bootRun
   ```

##  Endpoints da API

### 1. Interação via Texto
Processa comandos de texto para registrar gastos ou consultar o saldo.

**Requisição:** `http://localhost:8080/api/orcamento/texto`
```bash
curl -X POST http://localhost:8080/api/orcamento/texto      -H "Content-Type: text/plain"      -d "Paguei a conta de luz no valor de 120 reais"
```

### 2. Interação via Áudio
Recebe arquivos `.mp3`, `.wav` ou `.m4a`, transcreve para texto e processa a intenção financeira.

**Requisição:** `http://localhost:8080/api/orcamento/audio`
```bash
curl -X POST http://localhost:8080/api/orcamento/audio      -F "file=@C:\caminho\para\seu\arquivo.mp3"
```

## Desenvolvedor
Desenvolvido com foco em arquitetura backend, integração de modelos de IA em ambientes de produção e persistência de dados.

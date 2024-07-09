# Projeto de Benchmark de Recursos Computacionais

Este projeto foi desenvolvido para realizar benchmarks de uso de recursos computacionais, como CPU e memória. Ele é parte de um experimento científico.

## Funcionalidades

- Geração de arquivos CSV com dados aleatórios.
- Leitura de arquivos CSV e conversão em objetos Java.
- Persistência dos objetos Java em banco de dados.
- Compactação e descompactação de arquivos.
- Monitoramento com Prometheus e Grafana.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Univocity Parsers 2.9.1**
- **MariaDB 10.6**
- **Docker**
- **Prometheus**
- **Grafana**
- **Spring Boot Actuator**

## Estrutura do CSV

Os arquivos CSV gerados e lidos pelo sistema seguem uma estrutura padronizada que pode ser encontrada no código, justamente por se tratar de um experimento científico, e não um produto. Todavia, sua estrutura pode ser alterada por meio do código. Abaixo estão listados os atributos do CSV em sua estrutura atual:

- Firstname
- Lastname
- Email
- Age
- Gender
- Phone
- Address
- City
- State
- Zipcode
- Country
- Job

## Documentação da API

A documentação detalhada dos endpoints da API está disponível via Swagger. Após iniciar a aplicação, você pode acessar a documentação no seguinte endereço:

[Swagger API Documentation](http://localhost:8080/swagger-ui.html)

## Configuração do Diretório de Saída

Antes de executar a aplicação, é necessário configurar a variável de ambiente `FILE_OUTPUT_DIRECTORY`, que define o diretório de saída para os arquivos CSV gerados, bem como o local onde os arquivos compactados e descompactados serão armazenados. Substitua `<your_user>` pelo seu usuário do sistema.

Exemplo de configuração:

```bash
FILE_OUTPUT_DIRECTORY=C:\Users\<your_user>\Documents\csv
```

## Configuração do Monitoramento

Em breve, será disponibilizado o Docker Compose para configuração de monitoramento utilizando Prometheus e Grafana em conjunto com o Spring Boot Actuator.

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/ronny-souza/java-environment-performance.git
    ```

2. Navegue até o diretório do projeto.

3. Configure a variável de ambiente.
    ```bash
    export FILE_OUTPUT_DIRECTORY=C:\Users\<your_user>\Documents\csv  # Para Linux/Mac
    set FILE_OUTPUT_DIRECTORY=C:\Users\<your_user>\Documents\csv  # Para Windows
    ```

4.  Compile o projeto:
    ```bash
    ./mvnw clean install
    ```

5. Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```

6. Acesse a aplicação em `http://localhost:8080`.

## Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

**Nota:** Este projeto foi desenvolvido exclusivamente para fins de experimentação e pesquisa acadêmica.

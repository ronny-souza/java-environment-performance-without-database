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

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/ronny-souza/java-environment-performance-without-database.git
    ```

2. Navegue até o diretório do projeto.

3. Configure a variável de ambiente.
    ```bash
    export FILE_OUTPUT_DIRECTORY=<your_directory>  # Para Linux/Mac
    set FILE_OUTPUT_DIRECTORY=<your_directory>  # Para Windows
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

## Configuração com a GraalVM Native Image
Para a configuração da GraalVM Native Image, uma máquina Linux foi utilizada, e alguns passos adicionais se fazem necessários. A versão utilizada é a Oracle GraalVM 17.0.12.

1. Instalar alguns pacotes adicionais necessários utilizados para a construção da imagem nativa:
    ```bash
    sudo apt-get install build-essential
    ```

2. Instalar o seguinte pacote:
    ```bash
    sudo apt-get install zlib1g-dev
    ```

3. Configurações de reflexão

<p>Inicialmente, haverão problemas com a reflexão, dada a natureza da GraalVM Native Image, que não reconhece classes que utilizam reflexão dentro da aplicação nativamente. Por isso, é necessária a execução de um agente para monitorar o uso da aplicação e escrever automaticamente um arquivo de configurações de reflexão que o processo de construção da imagem nativa irá ler para registrar as classes, métodos e atributos. Então o primeiro passo é construir um executável comum da aplicação, que pode ser feito com o comando abaixo:

   ```bash
    mvn clean package -DskipTests
   ```

<p>Em seguida, deverá realizar a execução do executável gerado, mas com parâmetros adicionais que irão ativar o agente de monitoramento e registro de classes, e configurar o diretório onde ficarão os arquivos JSON gerados pelo agente. É importante ressaltar que o diretório de saída configurado aqui, também deve ser o mesmo configurado no `pom.xml`, na opção `-H:ConfigurationFileDirectories`.</p>

```bash
java -Dspring.aot.enabled=true -agentlib:native-image-agent=config-output-dir=./native-image/reflection -jar target/benchmark-0.0.1-SNAPSHOT.jar
```

<p>Após isso, quando a aplicação estiver executando, execute o máximo de operações possíveis, para que o agente alcance mais classes e assim registre-as. Um ponto importante é que, mesmo com o agente, não há 100% de garantia de que a aplicação irá conseguir alcançar todas as classes necessárias, e por isso erros na geração da imagem podem ocorrer durante o processo, acusando problemas com classes não reconhecidas. Se isso ocorrer, você precisará ler os logs que a GraalVM retorna, e assim adicionar as classes no arquivo JSON manualmente, o que não é um processo difícil, apenas trabalhoso e demorado.</p>

4. Geração da imagem nativa

O processo de geração da imagem nativa pode levar um tempo considerável, e isso pode depender de fatores como o tamanho da aplicação, por exemplo. Em meu caso, particularmente, durou cerca de 20 minutos.

```bash
mvn -Pnative native:compile -DskipTests
```

## Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

**Nota:** Este projeto foi desenvolvido exclusivamente para fins de experimentação e pesquisa acadêmica.

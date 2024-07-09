FROM container-registry.oracle.com/graalvm/native-image:17 AS build
WORKDIR /app

COPY . .

RUN ./mvnw -Pnative native:compile -DskipTests

FROM ubuntu:22.04
WORKDIR /app

RUN apt-get update && apt-get install -y build-essential && apt-get install -y gcc && \
    apt-get install -y zlib1g-dev && apt-get clean

EXPOSE 8080
COPY --from=build /app/target/benchmarknative benchmarknative
ENTRYPOINT ["./benchmarknative"]
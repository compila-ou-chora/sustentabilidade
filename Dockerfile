# syntax=docker/dockerfile:1

# ======================================================================
# Etapa 1 - Build: compila o projeto Maven e gera o .jar executavel
# ======================================================================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia primeiro o pom.xml para aproveitar o cache de dependencias do Docker.
# Enquanto o pom.xml nao mudar, essa camada fica em cache entre builds.
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copia o codigo-fonte e empacota a aplicacao (os testes ja rodam na etapa
# de "testes" do pipeline de CI, por isso sao pulados aqui para acelerar
# o build da imagem).
COPY src ./src
RUN mvn -B clean package -DskipTests

# ======================================================================
# Etapa 2 - Runtime: imagem final, enxuta, somente com o JRE e o .jar
# ======================================================================
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

# Usuario nao-root por boas praticas de seguranca em containers
RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /app/target/sustentabilidade-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R spring:spring /app
USER spring:spring

ENV SERVER_PORT=8080
EXPOSE 8080

# Healthcheck usado pelo Docker/Compose para saber quando a aplicacao
# esta pronta (consome o endpoint do Spring Boot Actuator)
HEALTHCHECK --interval=15s --timeout=5s --start-period=40s --retries=8 \
   CMD wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

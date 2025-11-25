# Automanager - Atividade 1

> Microserviço Java (Spring Boot) para cadastro de clientes. Usa Maven, H2 (runtime) e requer Java 17.

Pré-requisitos
- Java 17 (JDK)
- Git
- (Opcional) Maven — o projeto inclui Maven Wrapper (`mvnw` / `mvnw.cmd`)

Como executar (Windows)
1. Compilar:
```cmd
cd automanager
mvnw.cmd clean package
```
2. Executar em desenvolvimento:
```cmd
cd automanager
mvnw.cmd spring-boot:run
```

Configuração
- Arquivo: `automanager/src/main/resources/application.properties`
- Porta padrão: `8080` (alterar `server.port` ou usar variável `SERVER_PORT`)

Banco de dados
- H2 está incluído como dependência de runtime (para desenvolvimento/testes)


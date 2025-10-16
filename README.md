# Livros-ja
API REST para cadastro e vendas de livros


15/09/2025:
- AINDA ESTÁ EM FASE DE DESENVOLVIMENTO
- NO MOMENTO, SÓ RODA DE FORMA LOCAL COM A PORTA 8080

16/10/2025:
- API praticamente finalizada
- Falta corrijir alguns bugs de código
- Application YML está bugado devido as branchs e não aparece (não permite dar push nem pull)

Portanto, deixarei o código dela aqui:
"
spring:
  profiles:
    active: dev

  flyway:
    enabled: true
    locations: classpath:db/migration

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

  jackson:
    default-property-inclusion: non_null

jwt:
  secret:  b9b60f4e-3bcf-4e85-9773-8d2725faae1c
"

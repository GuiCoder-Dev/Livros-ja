plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.2.10"
}

group = "com.livrosja"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	//JWT
	implementation("io.jsonwebtoken:jjwt:0.13.0") // JWT

	//Spring Security
	implementation ("org.springframework.boot:spring-boot-starter-security")

	//Spring Validation
	implementation("org.springframework.boot:spring-boot-starter-validation") // Validation

	//Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5") // Swagger

	//MySQL and Flyway
	implementation("org.flywaydb:flyway-mysql:11.1.0") // MYSQL / FLYWAY
	implementation("org.flywaydb:flyway-core:11.1.0") // FLYWAY
	runtimeOnly("com.mysql:mysql-connector-j") // MYSQL
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

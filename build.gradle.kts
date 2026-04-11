plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.2"
}

group = "com.ecommerce"
version = "ECOMMERCE-1.0"

repositories {
    maven { url = uri("https://jitpack.io") }
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    runtimeOnly ("org.mariadb.jdbc:mariadb-java-client:3.0.6")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.security:spring-security-core")
    implementation("com.github.iamport:iamport-rest-client-java:0.2.23")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.ahocorasick:ahocorasick:0.6.3")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation("software.amazon.awssdk:bom")
    implementation("software.amazon.awssdk:s3:2.25.70")
    implementation("software.amazon.awssdk:s3-presigner:2.25.70")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.1.11")
    testImplementation("org.mockito:mockito-core:5.14.2")
}

tasks.test {
    useJUnitPlatform()
}
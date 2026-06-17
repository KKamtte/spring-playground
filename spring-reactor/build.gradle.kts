plugins {
    id("java")
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "server"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.projectreactor:reactor-core")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    /*
        1. spring-webflux: Spring WebFlux 프레임 워크 본체
        2. reactor-netty-http: 기본 HTTP 서버인 Reactor Netty
        3. reactor-core: Reactor core 라이브러리
        4. jackson-databind: JSON 직렬화/역직렬화 라이브러리
    */

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.projectreactor:reactor-test:3.7.6")
}

tasks.test {
    useJUnitPlatform()
}
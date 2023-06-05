plugins {
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.bmuschko.docker-java-application") version "9.3.1"
    id("io.freefair.lombok") version "8.0.1"
}

group = "io.github.mfvanek"
version = "2.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

application {
    mainClass.set("io.github.mfvanek.spring5mvc.TracingSpringMvcDemoApp")
}

repositories {
    mavenLocal()
    mavenCentral()
}

val swaggerVersion = "3.0.0"

dependencies {
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:8.5.89")
    implementation(libs.spring.webmvc)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    implementation(libs.jaeger.core)
    implementation(libs.jaeger.thrift)

    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.slf4j:slf4j-api:2.0.7")

    implementation("io.springfox:springfox-swagger2:${swaggerVersion}")
    implementation("io.springfox:springfox-swagger-ui:${swaggerVersion}")

    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(libs.spring.test)
    testImplementation("com.jayway.jsonpath:json-path:2.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    jar {
        archiveClassifier.set("default")
    }

    shadowJar {
        mergeServiceFiles()
        val classifier : String? = null
        archiveClassifier.set(classifier)
    }
}

docker {
    javaApplication {
        baseImage.set("eclipse-temurin:17.0.7_7-jre-focal")
        maintainer.set("Ivan Vakhrushev")
        images.set(listOf("${project.name}:${project.version}", "${project.name}:latest"))
    }
}

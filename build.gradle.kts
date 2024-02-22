import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.bmuschko.docker-java-application") version "9.4.0"
    id("io.freefair.lombok") version "8.6"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("net.ltgt.errorprone") version "3.1.0"
}

group = "io.github.mfvanek"
version = "2.0.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withJavadocJar()
    withSourcesJar()
}
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    options.errorprone {
        disableWarningsInGeneratedCode.set(true)
        disable("Slf4jLoggerShouldBeNonStatic")
    }
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
    implementation(platform("org.springframework:spring-framework-bom:5.3.31"))
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:9.0.84")
    implementation("org.springframework:spring-webmvc")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.google.code.findbugs:jsr305:3.0.2")

    implementation(libs.jaeger.core)
    implementation(libs.jaeger.thrift)

    implementation("ch.qos.logback:logback-classic:1.5.0")
    implementation("org.slf4j:slf4j-api:2.0.12")

    implementation("io.springfox:springfox-swagger2:${swaggerVersion}")
    implementation("io.springfox:springfox-swagger-ui:${swaggerVersion}")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.springframework:spring-test")
    testImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("org.hamcrest:hamcrest:2.2")

    errorprone("com.google.errorprone:error_prone_core:2.25.0")
    errorprone("jp.skypencil.errorprone.slf4j:errorprone-slf4j:0.1.22")
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

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

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    checkForGradleUpdate = true
    gradleReleaseChannel = "current"
    checkConstraints = true
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

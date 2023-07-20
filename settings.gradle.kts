rootProject.name = "spring5-mvc-with-embedded-tomcat"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val jaegerVersion = version("jaegertracing", "1.8.1")
            library("jaeger-core", "io.jaegertracing", "jaeger-core")
                    .versionRef(jaegerVersion)
            library("jaeger-thrift", "io.jaegertracing", "jaeger-thrift")
                    .versionRef(jaegerVersion)
            val springVersion = version("springframework", "5.3.29")
            library("spring-webmvc", "org.springframework", "spring-webmvc")
                    .versionRef(springVersion)
            library("spring-test", "org.springframework", "spring-test")
                    .versionRef(springVersion)
        }
    }
}

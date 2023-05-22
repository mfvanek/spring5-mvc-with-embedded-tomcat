rootProject.name = "spring5-mvc-with-embedded-tomcat"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val jaegerVersion = version("jaegertracing", "1.3.1")
            library("jaeger-core", "io.jaegertracing", "jaeger-core")
                    .versionRef(jaegerVersion)
            library("jaeger-thrift", "io.jaegertracing", "jaeger-thrift")
                    .versionRef(jaegerVersion)
            val springVersion = version("springframework", "5.3.27")
            library("spring-webmvc", "org.springframework", "spring-webmvc")
                    .versionRef(springVersion)
            library("spring-test", "org.springframework", "spring-test")
                    .versionRef(springVersion)
        }
    }
}

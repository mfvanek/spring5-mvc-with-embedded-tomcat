rootProject.name = "spring5-mvc-with-embedded-tomcat"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val jaegerVersion = version("jaegertracing", "1.8.1")
            library("jaeger-core", "io.jaegertracing", "jaeger-core")
                    .versionRef(jaegerVersion)
            library("jaeger-thrift", "io.jaegertracing", "jaeger-thrift")
                    .versionRef(jaegerVersion)
        }
    }
}

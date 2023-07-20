package io.github.mfvanek.spring5mvc.config;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    @Bean
    SamplerConfiguration samplerConfig() {
        return SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);
    }

    @Bean
    ReporterConfiguration reporterConfig() {
        return ReporterConfiguration.fromEnv()
                .withLogSpans(true);
    }

    @Bean
    public Tracer tracer(@Nonnull SamplerConfiguration samplerConfig,
                         @Nonnull ReporterConfiguration reporterConfig) {
//        return new JaegerTracer.Builder("spring5-mvc-with-embedded-tomcat")
//                .withSampler(new ConstSampler(true))
//                .withScopeManager(mdcScopeManager)
//                .withReporter(reporter)
//                .build();

        var config = new io.jaegertracing.Configuration("spring5-mvc-with-embedded-tomcat")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        return config.getTracer();
    }
}

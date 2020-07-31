package io.github.mfvanek.spring5mvc.config;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.MDCScopeManager;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.spi.Reporter;
import io.jaegertracing.thrift.internal.senders.UdpSender;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
public class TracingConfig {

    @Bean
    public MDCScopeManager mdcScopeManager() {
        return new MDCScopeManager.Builder()
                .build();
    }

    @Bean
    public Reporter reporter() {
        return new CompositeReporter(
                new LoggingReporter(),
                new RemoteReporter.Builder()
                        .withSender(new UdpSender())
                        .build()
        );
    }

    @Bean
    public Tracer tracer(@Nonnull MDCScopeManager mdcScopeManager, @Nonnull Reporter reporter) {
        return new JaegerTracer.Builder("spring5-mvc-with-embedded-tomcat")
                .withSampler(new ConstSampler(true))
                .withScopeManager(mdcScopeManager)
                .withReporter(reporter)
                .build();
    }
}

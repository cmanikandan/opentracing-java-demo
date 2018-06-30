package com.mikelduke.vhs.catalog;

import io.jaegertracing.reporters.InMemoryReporter;
import io.jaegertracing.reporters.Reporter;
import io.jaegertracing.samplers.ConstSampler;
import io.jaegertracing.samplers.Sampler;
import io.opentracing.util.GlobalTracer;

public class TracingConfiguration {
    
    public static void configureGlobalTracer() {
		//TODO Use real reported to istio tracing
		Reporter reporter = new InMemoryReporter() {
            @Override
            public void report(io.jaegertracing.Span span) {
                super.report(span);
                System.out.println("Span Reported: " + span + " Tags: " + span.getTags());
            }
        };
        
        Sampler sampler = new ConstSampler(true);
        io.opentracing.Tracer tracer = new io.jaegertracing.Tracer.Builder("sparkjava-test")
                .withReporter(reporter)
                .withSampler(sampler)
                .build();

        GlobalTracer.register(tracer);
	}
}
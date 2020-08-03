package io.github.mfvanek.spring5mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Slf4j
public class TracingSpringMvcDemoAppWithJetty {

    private static final int PORT = 8080;

    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION_PACKAGE = "io.github.mfvanek.spring5mvc.config";
    private static final String MAPPING_URL = "/";

    public static void main(String[] args) throws Exception {
        new TracingSpringMvcDemoAppWithJetty().startJetty(PORT);
    }

    private void startJetty(int port) throws Exception {
        log.debug("Starting server at port {}", port);
        Server server = new Server(port);
        server.setHandler(getServletContextHandler());
        addRuntimeShutdownHook(server);

        server.start();
        log.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setErrorHandler(null);

        contextHandler.setContextPath(CONTEXT_PATH);

        // JSP
        contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
        contextHandler.addServlet(JspServlet.class, "*.jsp");

        // Spring
        WebApplicationContext webAppContext = getWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webAppContext);
        ServletHolder springServletHolder = new ServletHolder("dispatcherServlet", dispatcherServlet);
        contextHandler.addServlet(springServletHolder, MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(webAppContext));

        return contextHandler;
    }

    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION_PACKAGE);
        return context;
    }

    private static void addRuntimeShutdownHook(final Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server.isStarted()) {
                server.setStopAtShutdown(true);
                try {
                    server.stop();
                } catch (Exception e) {
                    log.error("Error while stopping jetty server: " + e.getMessage(), e);
                }
            }
        }));
    }
}

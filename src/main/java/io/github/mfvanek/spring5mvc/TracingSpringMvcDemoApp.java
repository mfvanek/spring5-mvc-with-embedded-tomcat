package io.github.mfvanek.spring5mvc;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

public class TracingSpringMvcDemoApp {

    private static final int PORT = getPort();

    public static void main(String[] args) throws Exception {
        final Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(PORT);
        tomcat.getConnector();
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("/", ".");
        tomcat.start();
        tomcat.getServer().await();
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 8080;
    }

    // based on https://github.com/joansmith/spring-boot/blob/410dedc5675121da87cdbb83a53ad43179982407/spring-boot/src/main/java/org/springframework/boot/context/embedded/AbstractEmbeddedServletContainerFactory.java#L172
    private static String createTempDir() {
        try {
            File tempDir = File.createTempFile("tomcat.", "." + PORT);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir.getAbsolutePath();
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex
            );
        }
    }
}

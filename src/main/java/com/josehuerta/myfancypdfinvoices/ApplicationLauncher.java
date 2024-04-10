package com.josehuerta.myfancypdfinvoices;

import com.josehuerta.myfancypdfinvoices.context.MyFancyPdfInvoicesApplicationConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);

        // For web apps, WebApplicationContext is required like ApplicationContext is required for Spring to work
        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());

        // One and only entry point for Spring WebMVC.
        // This servlet will accept ALL incoming HTTP requests.
        // For it to work, it needs the WebApplicationContext, i.e., know about our @Controllers.
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);

        // Register Spring's DispatcherServlet
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    // Create the application context.
    public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MyFancyPdfInvoicesApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;

    }
}
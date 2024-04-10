package com.josehuerta.myfancypdfinvoices.context;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.ApplicationLauncher;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import com.josehuerta.myfancypdfinvoices.services.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class) // Scan all packages by pointing to root package
@PropertySource("classpath:/application.properties") // Read in application.properties
@EnableWebMvc // Automatically enable JSON <-> Java object conversions
public class ApplicationConfiguration {

//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }

    /*
    Since we are returning a String from a @Controller,
    Spring will ask all the ViewResolvers it knows to find and render that index.html view/template.
    Hence, we declare a ThymeleafViewResolver so Spring knows about it.
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());

        viewResolver.setOrder(1); // optional
        viewResolver.setViewNames(new String[] {"*.html", "*.xhtml"}); // optional
        return viewResolver;
    }

    /*
    The ViewResolver needs a SpringTemplateEngine to work with, a Thymeleaf-specific configuration bean,
    which is in Thymeleaf’s documentation. It lets us configure a couple of more advanced Thymeleaf settings,
    but it also needs a reference to a templateResolver, the class that actually finds your Thymeleaf template.
    */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    /*
    There are several ways to tell the SpringResourceTemplateResolver where to find its templates. One of them is to
    just prefix every template name with a Spring resources classifier.

    Here we are saying that all your templates should be in the /templates/ folder on our classpath
    (i.e. src/main/resources/ during development, later on in the .jar file) and that they should not be cached,
    which makes sense during development, but not in production.
    */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /*
    Beans tell Spring that on ApplicationContext startup, it should
    create one instance of each @Bean annotated class.
    */

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Add @Scope to return a new UserService everytime we call it
    public UserService userService() {
        return new UserService();
    }

    public InvoiceService invoiceService(UserService userService, String cdnUrl) {
        return new InvoiceService(userService, cdnUrl);
    }

    @Bean // Keep @Bean annotation because Jackson is a 3rd party library
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

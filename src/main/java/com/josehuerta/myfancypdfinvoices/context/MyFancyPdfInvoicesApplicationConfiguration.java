package com.josehuerta.myfancypdfinvoices.context;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.ApplicationLauncher;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import com.josehuerta.myfancypdfinvoices.services.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class) // Scan all packages by pointing to root package
@PropertySource("classpath:/application.properties") // Read in application.properties
public class MyFancyPdfInvoicesApplicationConfiguration {

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

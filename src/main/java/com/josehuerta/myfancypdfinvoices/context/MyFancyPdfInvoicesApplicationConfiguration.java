package com.josehuerta.myfancypdfinvoices.context;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import com.josehuerta.myfancypdfinvoices.services.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyFancyPdfInvoicesApplicationConfiguration {

    /*
    Beans tell Spring that on ApplicationContext startup, it should
    create one instance of each @Bean annotated class.
    */

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // Add @Scope to return a new UserService everytime we call it
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public InvoiceService invoiceService(UserService userService) {
        return new InvoiceService(userService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

package com.josehuerta.myfancypdfinvoices.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.context.MyFancyPdfInvoicesApplicationConfiguration;
import com.josehuerta.myfancypdfinvoices.model.Invoice;


import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import com.josehuerta.myfancypdfinvoices.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

public class MyFancyPdfInvoicesServlet extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;
    private InvoiceService invoiceService;

    /*
    Every HTTPServlet has an init() method. Here we use it to create an ApplicationContext by
    calling new AnnotationConfigApplicationContext and passing in our configuration class.
     */
    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyFancyPdfInvoicesApplicationConfiguration.class);

        /*
         whenever your JVM is about to stop, Spring properly shuts down its applicationContext
         first and also calls the @PreDestroy methods of all registered @Beans.
         */
        ctx.registerShutdownHook();

        // Spring reads in our @Configuration class and constructs our beans.
        this.userService = ctx.getBean(UserService.class);
        this.objectMapper = ctx.getBean(ObjectMapper.class);
        this.invoiceService = ctx.getBean(InvoiceService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       if(request.getRequestURI().equalsIgnoreCase("/")) {
           response.setContentType("text/html; charset=UTF-8");
           response.getWriter().print(
                   "<html>\n" +
                           "<body>\n" +
                           "<h1>Hello World</h1>\n" +
                           "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" +
                           "</body>\n" +
                           "</html>");
       }
       else if(request.getRequestURI().equalsIgnoreCase("/invoices")) {
           response.setContentType("application/json; charset=UTF-8");

           List<Invoice> invoices = invoiceService.findAll();
           response.getWriter().print(objectMapper.writeValueAsString(invoices));
       }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getRequestURI().equalsIgnoreCase("/invoices")) {

            String userId = request.getParameter("user_id");
            Integer amount = Integer.valueOf(request.getParameter("amount"));

            Invoice invoice = invoiceService.create(userId, amount);

            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoice);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
package com.josehuerta.myfancypdfinvoices.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.model.Invoice;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MyFancyPdfInvoicesServlet extends HttpServlet {

    private final InvoiceService invoiceService = new InvoiceService();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
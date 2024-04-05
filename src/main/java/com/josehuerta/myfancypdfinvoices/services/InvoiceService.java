package com.josehuerta.myfancypdfinvoices.services;

import com.josehuerta.myfancypdfinvoices.context.Application;
import com.josehuerta.myfancypdfinvoices.model.Invoice;
import com.josehuerta.myfancypdfinvoices.model.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class InvoiceService {

    private final UserService userService;

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    public InvoiceService(UserService userService) {
        this.userService = userService;
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {
        // User validation check
        User user = userService.findById(userId);
        if(user == null) {
            throw new IllegalStateException();
        }

        // TODO real pdf creation and storing on network service
        Invoice invoice = new Invoice(userId, amount, "http://www.africau.edu/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }
}

package com.josehuerta.myfancypdfinvoices.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josehuerta.myfancypdfinvoices.model.User;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import com.josehuerta.myfancypdfinvoices.services.UserService;

public class Application {

    public static final UserService userService = new UserService();
    public static final InvoiceService invoiceService = new InvoiceService(userService);
    public static final ObjectMapper objectMapper = new ObjectMapper();

}

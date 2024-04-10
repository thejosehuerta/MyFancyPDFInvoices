package com.josehuerta.myfancypdfinvoices.services;

import com.josehuerta.myfancypdfinvoices.model.Invoice;
import com.josehuerta.myfancypdfinvoices.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    // Field injection
    //@Autowired // With this annotation, Spring will construct the UserService for us and inject the dependencies

    // We will use constructor injection
    private final UserService userService;

    private final String cdnUrl;

    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
        this.cdnUrl = cdnUrl;
    }

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {
        // User validation check
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalStateException();
        }

        // TODO real pdf creation and storing on network service
        Invoice invoice = new Invoice(userId, amount, cdnUrl + "/images/default/sample.pdf");
        invoices.add(invoice);
        return invoice;
    }

    /*
    if you want to make sure that your object, including all its dependencies is completely constructed,
    you will need to use @PostConstruct.
     */
    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }

    /*
    PreDestroy method gets called whenever you shut down your applicationContext gracefully.
     */
    @PreDestroy
    public void shutdown() {
        System.out.println("Deleting downloaded templates...");
        // TODO actual deletion of PDFs
    }

//    // Setter injection
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
}

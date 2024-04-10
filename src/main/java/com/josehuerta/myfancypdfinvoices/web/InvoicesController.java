package com.josehuerta.myfancypdfinvoices.web;

import com.josehuerta.myfancypdfinvoices.dto.InvoiceDto;
import com.josehuerta.myfancypdfinvoices.model.Invoice;
import com.josehuerta.myfancypdfinvoices.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/* This annotation is so @ComponentScan can find it and also let SpringMVC knows that this
class can accept HTTP requests */
//@Controller

@RestController // Short for @Controller and @ResponseBody
public class InvoicesController {

    private final InvoiceService invoiceService;

    // Constructor injecting the InvoiceService
    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    // Shorthand for @GetMapping -> @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @PostMapping("/invoices/")
    public Invoice createInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
        return invoiceService.create(invoiceDto.getUserId(), invoiceDto.getAmount());
    }


}

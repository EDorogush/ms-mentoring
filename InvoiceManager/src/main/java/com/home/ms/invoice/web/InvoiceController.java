package com.home.ms.invoice.web;

import com.home.ms.invoice.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> searchInvoices(InvoiceGetRequestParameters params) {
        final String userId = getUserId(params.getOwnedBy());
        return invoiceService.searchInvoicesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Invoice searchInvoiceById(@PathVariable String id) {
        return invoiceService.searchInvoiceById(id);
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        final String invoiceId = invoiceService.createInvoice(invoice);
        invoice.setId(invoiceId);
        return invoice;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable String id) {
        invoiceService.deleteById(id);

    }

    private String getUserId(String ownedBy) {
        if (ownedBy == null || ownedBy.equals("me")) {
            return "user_id-9";
        }
        return ownedBy;
    }


}

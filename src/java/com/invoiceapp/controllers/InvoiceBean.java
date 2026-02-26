package com.invoiceapp.controllers;

import com.invoiceapp.entities.Client;
import com.invoiceapp.entities.Company;
import com.invoiceapp.entities.Invoice;
import com.invoiceapp.entities.InvoiceItem;
import com.invoiceapp.services.ClientService;
import com.invoiceapp.services.CompanyService;
import com.invoiceapp.services.InvoiceService;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class InvoiceBean implements Serializable {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private ClientService clientService;

    @Inject
    private CompanyService companyService;

    @Inject
    private ClientBean clientBean;

    private Invoice invoice;
    private InvoiceItem newItem;

    private Long selectedClientId;
    private Client newClient = new Client();

    @PostConstruct
    public void init() {
        invoice = new Invoice();
        invoice.setItems(new ArrayList<>());

        newItem = new InvoiceItem();

        // Auto load company
        Company company = companyService.getCompanyProfile();
        invoice.setCompany(company);
    }

    public void addItem() {
        newItem.setInvoice(invoice);
        invoice.getItems().add(newItem);
        newItem = new InvoiceItem();
    }

    public String generateInvoice() {

        // Handle client selection
        if (selectedClientId != null) {
            Client existingClient = clientService.findById(selectedClientId);
            invoice.setClient(existingClient);
        } else {
            // Save new client automatically
            Client savedClient = clientService.save(newClient);
            invoice.setClient(savedClient);
            clientBean.refreshClients();
        }

        invoice.setSubtotal(getSubtotal());
        invoice.setTaxAmount(getTax());
        invoice.setTotalAmount(getTotal());

        invoiceService.saveInvoice(invoice);

        return "invoiceView?faces-redirect=true";
    }

    // ===== Calculations =====

    public Double getSubtotal() {
        return invoice.getItems()
                .stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();
    }

    public Double getTax() {
        if (invoice.getTaxRate() == null) return 0.0;
        return getSubtotal() * invoice.getTaxRate() / 100;
    }

    public Double getTotal() {
        Double discount = invoice.getDiscount() == null ? 0.0 : invoice.getDiscount();
        return getSubtotal() + getTax() - discount;
    }

    // ===== Getters =====

    public Invoice getInvoice() { return invoice; }
    public InvoiceItem getNewItem() { return newItem; }
    public Long getSelectedClientId() { return selectedClientId; }
    public Client getNewClient() { return newClient; }

    public void setSelectedClientId(Long selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }
}
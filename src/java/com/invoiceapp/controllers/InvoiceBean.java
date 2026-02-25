package com.invoiceapp.controllers;


import com.invoiceapp.entities.Invoice;
import com.invoiceapp.entities.InvoiceItem;
import com.invoiceapp.services.InvoiceService;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MEDIO
 */
@Named
@SessionScoped
public class InvoiceBean implements Serializable {

    @Inject
    private InvoiceService invoiceService;

    private final Invoice invoice = new Invoice();
    private InvoiceItem newItem = new InvoiceItem();

    public InvoiceBean() {
        invoice.setItems(new ArrayList<>());
    }

    public void addItem() {
        newItem.setInvoice(invoice);
        invoice.getItems().add(newItem);
        newItem = new InvoiceItem();
    }

    public String generateInvoice() {
        System.out.println("Generate invoice clicked");

        invoice.setSubtotal(getSubtotal());
        invoice.setTaxAmount(getTax());
        invoice.setTotalAmount(getTotal());

        invoiceService.saveInvoice(invoice);

        return "invoiceView?faces-redirect=true";
    }
    
    public Double getSubtotal() {
        if (invoice.getItems() == null) return 0.0;

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
        return getSubtotal() + getTax();
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InvoiceItem getNewItem() {
        return newItem;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setNewItem(InvoiceItem newItem) {
        this.newItem = newItem;
    }

    public InvoiceBean(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    
    
}
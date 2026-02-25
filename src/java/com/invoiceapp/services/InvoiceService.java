package com.invoiceapp.services;

import com.invoiceapp.entities.Invoice;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InvoiceService {

    @PersistenceContext(unitName = "invoicingAppPU")
    private EntityManager em;

    // Save new invoice OR update existing one
    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getId() == null) {
            em.persist(invoice);   // New invoice
            return invoice;
        } else {
            return em.merge(invoice);  // Update existing invoice
        }
    }

    // Find invoice by ID
    public Invoice findById(Long id) {
        return em.find(Invoice.class, id);
    }

    // Get all invoices
    public List<Invoice> findAll() {
        return em.createQuery("SELECT i FROM Invoice i", Invoice.class)
                 .getResultList();
    }

    // Delete invoice
    public void delete(Long id) {
        Invoice invoice = em.find(Invoice.class, id);
        if (invoice != null) {
            em.remove(invoice);
        }
    }
}
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

    public Invoice saveInvoice(Invoice invoice) {

        // Ensure bidirectional relationship is set
        if (invoice.getItems() != null) {
            invoice.getItems().forEach(item -> {
                item.setInvoice(invoice);
            });
        }

        if (invoice.getId() == null) {
            em.persist(invoice);
            return invoice;
        } else {
            return em.merge(invoice);
        }
    }

    public Invoice findById(Long id) {
        return em.find(Invoice.class, id);
    }

    public List<Invoice> findAll() {
        return em.createQuery(
                "SELECT i FROM Invoice i ORDER BY i.issueDate DESC",
                Invoice.class)
                .getResultList();
    }

    public void delete(Long id) {
        Invoice invoice = em.find(Invoice.class, id);
        if (invoice != null) {
            em.remove(invoice);
        }
    }

    // NEW: find invoices by client
    public List<Invoice> findByClient(Long clientId) {
        return em.createQuery(
                "SELECT i FROM Invoice i WHERE i.client.id = :clientId",
                Invoice.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
}
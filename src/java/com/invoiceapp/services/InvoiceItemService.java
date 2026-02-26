/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.services;

import com.invoiceapp.entities.InvoiceItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MEDIO
 */
@Stateless
public class InvoiceItemService {

    @PersistenceContext(unitName = "invoicingAppPU")
    private EntityManager em;

    public InvoiceItem save(InvoiceItem item) {
        if (item.getId() == null) {
            em.persist(item);
            return item;
        } else {
            return em.merge(item);
        }
    }

    public void delete(Long id) {
        InvoiceItem item = em.find(InvoiceItem.class, id);
        if (item != null) {
            em.remove(item);
        }
    }
}

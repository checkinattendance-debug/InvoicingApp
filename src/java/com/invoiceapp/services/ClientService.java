/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.services;

/**
 *
 * @author MEDIO
 */

import com.invoiceapp.entities.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientService {

    @PersistenceContext(unitName = "invoicingAppPU")
    private EntityManager em;

    public Client save(Client client) {
        if (client.getId() == null) {
            em.persist(client);
            return client;
        } else {
            return em.merge(client);
        }
    }

    public Client findById(Long id) {
        return em.find(Client.class, id);
    }

    public List<Client> findAll() {
        return em.createQuery(
                "SELECT c FROM Client c ORDER BY c.name",
                Client.class)
                .getResultList();
    }

    public void delete(Long id) {
        Client client = em.find(Client.class, id);
        if (client != null) {
            em.remove(client);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.services;

import com.invoiceapp.entities.Company;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MEDIO
 */
@Stateless
public class CompanyService {

    @PersistenceContext
    private EntityManager em;

    public Company getCompanyProfile() {
        return em.createQuery("SELECT c FROM Company c", Company.class)
                 .setMaxResults(1)
                 .getSingleResult();
    }
}

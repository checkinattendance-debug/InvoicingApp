/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.services;

import com.invoiceapp.entities.Company;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MEDIO
 */
@Stateless
public class CompanyService {

    @PersistenceContext(unitName = "invoicingAppPU")
    private EntityManager em;

    public Company getCompanyProfile() {
        List<Company> companies = em.createQuery(
                "SELECT c FROM Company c", Company.class)
                .setMaxResults(1)
                .getResultList();

        return companies.isEmpty() ? null : companies.get(0);
    }

    public Company save(Company company) {
        if (company.getId() == null) {
            em.persist(company);
            return company;
        } else {
            return em.merge(company);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.controllers;

import com.invoiceapp.entities.Company;
import com.invoiceapp.services.CompanyService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CompanyBean implements Serializable {

    @Inject
    private CompanyService companyService;

    private Company company;

    @PostConstruct
    public void init() {
        company = companyService.getCompanyProfile();

        if (company == null) {
            company = new Company(); // auto create if not exists
        }
    }

    public String updateCompany() {
        companyService.save(company);
        return "invoiceForm?faces-redirect=true"; // go back to main page
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
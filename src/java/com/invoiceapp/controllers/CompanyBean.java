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
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MEDIO
 */
@Named
@SessionScoped
public class CompanyBean implements Serializable {

    @Inject
    private CompanyService companyService;

    private Company company;

    @PostConstruct
    public void init() {
        company = companyService.getCompanyProfile();
    }

    public Company getCompany() {
        return company;
    }
}

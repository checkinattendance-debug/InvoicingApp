/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invoiceapp.controllers;

/**
 *
 * @author MEDIO
 */

import com.invoiceapp.entities.Client;
import com.invoiceapp.services.ClientService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    @Inject
    private ClientService clientService;

    private List<Client> clients;

    @PostConstruct
    public void init() {
        clients = clientService.findAll();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void refreshClients() {
        clients = clientService.findAll();
    }
}

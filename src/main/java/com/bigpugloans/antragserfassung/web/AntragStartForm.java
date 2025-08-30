package com.bigpugloans.antragserfassung.web;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;

@InfrastructureRing
public class AntragStartForm {
    private String benutzerId;

    public AntragStartForm() {
    }

    public String getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(String benutzerId) {
        this.benutzerId = benutzerId;
    }
}
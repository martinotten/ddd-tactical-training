package com.bigpugloans.antragserfassung.web;

import org.jmolecules.architecture.onion.classical.InfrastructureRing;

@InfrastructureRing
public class AbschlussForm {
    private String benutzerKommentar;

    public AbschlussForm() {
    }

    public String getBenutzerKommentar() {
        return benutzerKommentar;
    }

    public void setBenutzerKommentar(String benutzerKommentar) {
        this.benutzerKommentar = benutzerKommentar;
    }
}
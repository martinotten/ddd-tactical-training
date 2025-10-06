package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.query.AusgabenDetails;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public class AusgabenForm {
    private BigDecimal lebenshaltungskosten;
    private BigDecimal miete;
    private BigDecimal privateKrankenversicherung;
    private BigDecimal sonstigeVersicherungen;
    private BigDecimal kreditraten;
    private BigDecimal sonstigeAusgaben;

    public AusgabenForm() {
    }

    public void setVorhandeneWerte(AusgabenDetails details) {
        this.lebenshaltungskosten = details.lebenshaltungskosten();
        this.miete = details.miete();
        this.privateKrankenversicherung = details.privateKrankenversicherung();
        this.sonstigeVersicherungen = details.sonstigeVersicherungen();
        this.kreditraten = details.kreditraten();
        this.sonstigeAusgaben = details.sonstigeAusgaben();
    }

    // Getters and Setters
    public BigDecimal getLebenshaltungskosten() { return lebenshaltungskosten; }
    public void setLebenshaltungskosten(BigDecimal lebenshaltungskosten) { this.lebenshaltungskosten = lebenshaltungskosten; }
    
    public BigDecimal getMiete() { return miete; }
    public void setMiete(BigDecimal miete) { this.miete = miete; }
    
    public BigDecimal getPrivateKrankenversicherung() { return privateKrankenversicherung; }
    public void setPrivateKrankenversicherung(BigDecimal privateKrankenversicherung) { this.privateKrankenversicherung = privateKrankenversicherung; }
    
    public BigDecimal getSonstigeVersicherungen() { return sonstigeVersicherungen; }
    public void setSonstigeVersicherungen(BigDecimal sonstigeVersicherungen) { this.sonstigeVersicherungen = sonstigeVersicherungen; }
    
    public BigDecimal getKreditraten() { return kreditraten; }
    public void setKreditraten(BigDecimal kreditraten) { this.kreditraten = kreditraten; }
    
    public BigDecimal getSonstigeAusgaben() { return sonstigeAusgaben; }
    public void setSonstigeAusgaben(BigDecimal sonstigeAusgaben) { this.sonstigeAusgaben = sonstigeAusgaben; }
}
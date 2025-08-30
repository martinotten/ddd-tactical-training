package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.domain.model.Nutzungsart;
import com.bigpugloans.antragserfassung.domain.model.Objektart;
import com.bigpugloans.antragserfassung.query.FinanzierungsobjektDetails;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.math.BigDecimal;

@InfrastructureRing
public class FinanzierungsobjektForm {
    private Objektart objektart;
    private String objektStrasse;
    private String objektHausnummer;
    private String objektPostleitzahl;
    private String objektOrt;
    private String objektLand = "Deutschland";
    private BigDecimal kaufpreis;
    private BigDecimal nebenkosten;
    private Integer baujahr;
    private Double wohnflaeche;
    private Integer anzahlZimmer;
    private Nutzungsart nutzungsart;

    public FinanzierungsobjektForm() {
    }

    public void setVorhandeneWerte(FinanzierungsobjektDetails details) {
        this.objektart = details.objektart();
        if (details.objektAdresse() != null) {
            this.objektStrasse = details.objektAdresse().strasse();
            this.objektHausnummer = details.objektAdresse().hausnummer();
            this.objektPostleitzahl = details.objektAdresse().postleitzahl();
            this.objektOrt = details.objektAdresse().ort();
            this.objektLand = details.objektAdresse().land();
        }
        this.kaufpreis = details.kaufpreis();
        this.nebenkosten = details.nebenkosten();
        this.baujahr = details.baujahr();
        this.wohnflaeche = details.wohnflaeche();
        this.anzahlZimmer = details.anzahlZimmer();
        this.nutzungsart = details.nutzungsart();
    }

    // Getters and Setters
    public Objektart getObjektart() { return objektart; }
    public void setObjektart(Objektart objektart) { this.objektart = objektart; }
    
    public String getObjektStrasse() { return objektStrasse; }
    public void setObjektStrasse(String objektStrasse) { this.objektStrasse = objektStrasse; }
    
    public String getObjektHausnummer() { return objektHausnummer; }
    public void setObjektHausnummer(String objektHausnummer) { this.objektHausnummer = objektHausnummer; }
    
    public String getObjektPostleitzahl() { return objektPostleitzahl; }
    public void setObjektPostleitzahl(String objektPostleitzahl) { this.objektPostleitzahl = objektPostleitzahl; }
    
    public String getObjektOrt() { return objektOrt; }
    public void setObjektOrt(String objektOrt) { this.objektOrt = objektOrt; }
    
    public String getObjektLand() { return objektLand; }
    public void setObjektLand(String objektLand) { this.objektLand = objektLand; }
    
    public BigDecimal getKaufpreis() { return kaufpreis; }
    public void setKaufpreis(BigDecimal kaufpreis) { this.kaufpreis = kaufpreis; }
    
    public BigDecimal getNebenkosten() { return nebenkosten; }
    public void setNebenkosten(BigDecimal nebenkosten) { this.nebenkosten = nebenkosten; }
    
    public Integer getBaujahr() { return baujahr; }
    public void setBaujahr(Integer baujahr) { this.baujahr = baujahr; }
    
    public Double getWohnflaeche() { return wohnflaeche; }
    public void setWohnflaeche(Double wohnflaeche) { this.wohnflaeche = wohnflaeche; }
    
    public Integer getAnzahlZimmer() { return anzahlZimmer; }
    public void setAnzahlZimmer(Integer anzahlZimmer) { this.anzahlZimmer = anzahlZimmer; }
    
    public Nutzungsart getNutzungsart() { return nutzungsart; }
    public void setNutzungsart(Nutzungsart nutzungsart) { this.nutzungsart = nutzungsart; }
}
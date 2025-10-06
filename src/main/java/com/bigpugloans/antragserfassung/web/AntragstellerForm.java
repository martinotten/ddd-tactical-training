package com.bigpugloans.antragserfassung.web;

import com.bigpugloans.antragserfassung.domain.model.Familienstand;
import com.bigpugloans.antragserfassung.domain.model.Branche;
import com.bigpugloans.antragserfassung.domain.model.Berufsart;
import com.bigpugloans.antragserfassung.query.AntragstellerDetails;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.time.LocalDate;

@InfrastructureRing
public class AntragstellerForm {
    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;
    private String telefonnummer;
    private String emailAdresse;
    private String strasse;
    private String hausnummer;
    private String postleitzahl;
    private String ort;
    private String land = "Deutschland";
    private Familienstand familienstand;
    private Integer anzahlKinder;
    private String kundennummer;
    private Branche branche;
    private Berufsart berufsart;
    private String arbeitgeber;
    private LocalDate beschaeftigtSeit;

    public AntragstellerForm() {
    }

    public void setVorhandeneWerte(AntragstellerDetails details) {
        this.vorname = details.vorname();
        this.nachname = details.nachname();
        this.geburtsdatum = details.geburtsdatum();
        this.telefonnummer = details.telefonnummer();
        this.emailAdresse = details.emailAdresse();
        if (details.anschrift() != null) {
            this.strasse = details.anschrift().strasse();
            this.hausnummer = details.anschrift().hausnummer();
            this.postleitzahl = details.anschrift().postleitzahl();
            this.ort = details.anschrift().ort();
            this.land = details.anschrift().land();
        }
        this.familienstand = details.familienstand();
        this.anzahlKinder = details.anzahlKinder();
        this.kundennummer = details.kundennummer();
        this.branche = details.branche();
        this.berufsart = details.berufsart();
        this.arbeitgeber = details.arbeitgeber();
        this.beschaeftigtSeit = details.beschaeftigtSeit();
    }

    // Getters and Setters
    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }
    
    public String getNachname() { return nachname; }
    public void setNachname(String nachname) { this.nachname = nachname; }
    
    public LocalDate getGeburtsdatum() { return geburtsdatum; }
    public void setGeburtsdatum(LocalDate geburtsdatum) { this.geburtsdatum = geburtsdatum; }
    
    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }
    
    public String getEmailAdresse() { return emailAdresse; }
    public void setEmailAdresse(String emailAdresse) { this.emailAdresse = emailAdresse; }
    
    public String getStrasse() { return strasse; }
    public void setStrasse(String strasse) { this.strasse = strasse; }
    
    public String getHausnummer() { return hausnummer; }
    public void setHausnummer(String hausnummer) { this.hausnummer = hausnummer; }
    
    public String getPostleitzahl() { return postleitzahl; }
    public void setPostleitzahl(String postleitzahl) { this.postleitzahl = postleitzahl; }
    
    public String getOrt() { return ort; }
    public void setOrt(String ort) { this.ort = ort; }
    
    public String getLand() { return land; }
    public void setLand(String land) { this.land = land; }
    
    public Familienstand getFamilienstand() { return familienstand; }
    public void setFamilienstand(Familienstand familienstand) { this.familienstand = familienstand; }
    
    public Integer getAnzahlKinder() { return anzahlKinder; }
    public void setAnzahlKinder(Integer anzahlKinder) { this.anzahlKinder = anzahlKinder; }
    
    public String getKundennummer() { return kundennummer; }
    public void setKundennummer(String kundennummer) { this.kundennummer = kundennummer; }
    
    public Branche getBranche() { return branche; }
    public void setBranche(Branche branche) { this.branche = branche; }
    
    public Berufsart getBerufsart() { return berufsart; }
    public void setBerufsart(Berufsart berufsart) { this.berufsart = berufsart; }
    
    public String getArbeitgeber() { return arbeitgeber; }
    public void setArbeitgeber(String arbeitgeber) { this.arbeitgeber = arbeitgeber; }
    
    public LocalDate getBeschaeftigtSeit() { return beschaeftigtSeit; }
    public void setBeschaeftigtSeit(LocalDate beschaeftigtSeit) { this.beschaeftigtSeit = beschaeftigtSeit; }
}
package com.bigpugloans.events.antrag;

import java.util.Date;

public class Antragsteller {
    private String vorname;
    private String nachname;
    private String strasse;
    private String postleitzahl;
    private String ort;
    private Familienstand familienstand;
    private Branche branche;
    private Berufsart berufsart;
    private String arbeitgeber;
    private Date beschaeftigtSeit;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public Familienstand getFamilienstand() {
        return familienstand;
    }

    public void setFamilienstand(Familienstand familienstand) {
        this.familienstand = familienstand;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public Berufsart getBerufsart() {
        return berufsart;
    }

    public void setBerufsart(Berufsart berufsart) {
        this.berufsart = berufsart;
    }

    public String getArbeitgeber() {
        return arbeitgeber;
    }

    public void setArbeitgeber(String arbeitgeber) {
        this.arbeitgeber = arbeitgeber;
    }

    public Date getBeschaeftigtSeit() {
        return beschaeftigtSeit;
    }

    public void setBeschaeftigtSeit(Date beschaeftigtSeit) {
        this.beschaeftigtSeit = beschaeftigtSeit;
    }
}

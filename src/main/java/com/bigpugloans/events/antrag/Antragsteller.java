package com.bigpugloans.events.antrag;

import java.util.Date;
import java.util.Objects;

public class Antragsteller {
    private String kundennummer;
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
    private Date geburtstdatum;

    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }

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

    public Date getGeburtstdatum() {
        return geburtstdatum;
    }

    public void setGeburtstdatum(Date geburtstdatum) {
        this.geburtstdatum = geburtstdatum;
    }

    @Override
    public String toString() {
        return "Antragsteller{" +
                "kundennummer='" + kundennummer + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", strasse='" + strasse + '\'' +
                ", postleitzahl='" + postleitzahl + '\'' +
                ", ort='" + ort + '\'' +
                ", familienstand=" + familienstand +
                ", branche=" + branche +
                ", berufsart=" + berufsart +
                ", arbeitgeber='" + arbeitgeber + '\'' +
                ", beschaeftigtSeit=" + beschaeftigtSeit +
                ", geburtstdatum=" + geburtstdatum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antragsteller that = (Antragsteller) o;
        return Objects.equals(kundennummer, that.kundennummer) && Objects.equals(vorname, that.vorname) && Objects.equals(nachname, that.nachname) && Objects.equals(strasse, that.strasse) && Objects.equals(postleitzahl, that.postleitzahl) && Objects.equals(ort, that.ort) && familienstand == that.familienstand && branche == that.branche && berufsart == that.berufsart && Objects.equals(arbeitgeber, that.arbeitgeber) && Objects.equals(beschaeftigtSeit, that.beschaeftigtSeit) && Objects.equals(geburtstdatum, that.geburtstdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundennummer, vorname, nachname, strasse, postleitzahl, ort, familienstand, branche, berufsart, arbeitgeber, beschaeftigtSeit, geburtstdatum);
    }
}

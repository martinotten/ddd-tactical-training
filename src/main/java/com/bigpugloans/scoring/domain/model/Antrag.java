package com.bigpugloans.scoring.domain.model;

import com.bigpugloans.events.antrag.Antragsteller;
import com.bigpugloans.events.antrag.Einkommen;
import com.bigpugloans.events.antrag.Ausgaben;
import com.bigpugloans.events.antrag.Finanzierungsobjekt;
import com.bigpugloans.events.antrag.Kosten;
import com.bigpugloans.events.antrag.Eigenmittel;
import com.bigpugloans.events.antrag.Finanzierung;
import java.util.Objects;

public class Antrag {
    private String antragsnummer;
    private Antragsteller antragsteller;
    private Einkommen einkommen;
    private Ausgaben ausgaben;
    private Finanzierungsobjekt finanzierungsobjekt;
    private Kosten kosten;
    private Eigenmittel eigenmittel;
    private Finanzierung finanzierung;
    private Integer marktwert;
    private Integer summeDarlehen;
    private Integer summeEigenmittel;
    private Integer monatlicheEinnahmen;
    private Integer monatlicheAusgaben;
    private Integer monatlicheDarlehensbelastungen;

    public Antrag() {
    }

    public Antrag(String antragsnummer, String kundennummer, Integer monatlicheEinnahmen, 
                  Integer monatlicheAusgaben, Integer monatlicheDarlehensbelastungen, 
                  String wohnort, Integer kaufnebenkosten, Integer marktwert, 
                  Integer summeDarlehen, Integer summeEigenmittel, 
                  String vorname, String nachname, String strasse, 
                  String postleitzahl, String ort, java.time.LocalDate geburtstdatum) {
        this.antragsnummer = antragsnummer;
        this.monatlicheEinnahmen = monatlicheEinnahmen;
        this.monatlicheAusgaben = monatlicheAusgaben;
        this.monatlicheDarlehensbelastungen = monatlicheDarlehensbelastungen;
        this.marktwert = marktwert;
        this.summeDarlehen = summeDarlehen;
        this.summeEigenmittel = summeEigenmittel;

        this.antragsteller = new Antragsteller();
        this.antragsteller.setKundennummer(kundennummer);
        this.antragsteller.setVorname(vorname);
        this.antragsteller.setNachname(nachname);
        this.antragsteller.setStrasse(strasse);
        this.antragsteller.setPostleitzahl(postleitzahl);
        this.antragsteller.setOrt(wohnort != null ? wohnort : ort);
        if (geburtstdatum != null) {
            this.antragsteller.setGeburtstdatum(java.sql.Date.valueOf(geburtstdatum));
        }

        this.kosten = new Kosten();
        if (kaufnebenkosten != null) {
            this.kosten.setNebenkosten(kaufnebenkosten);
        }
    }

    @Override
    public String toString() {
        return "Antrag{" +
                "antragsnummer='" + antragsnummer + '\'' +
                ", antragsteller=" + antragsteller +
                ", einkommen=" + einkommen +
                ", ausgaben=" + ausgaben +
                ", finanzierungsobjekt=" + finanzierungsobjekt +
                ", kosten=" + kosten +
                ", eigenmittel=" + eigenmittel +
                ", finanzierung=" + finanzierung +
                ", marktwert=" + marktwert +
                ", summeDarlehen=" + summeDarlehen +
                ", summeEigenmittel=" + summeEigenmittel +
                ", monatlicheEinnahmen=" + monatlicheEinnahmen +
                ", monatlicheAusgaben=" + monatlicheAusgaben +
                ", monatlicheDarlehensbelastungen=" + monatlicheDarlehensbelastungen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antrag antrag = (Antrag) o;
        return Objects.equals(antragsnummer, antrag.antragsnummer) &&
               Objects.equals(antragsteller, antrag.antragsteller) &&
               Objects.equals(einkommen, antrag.einkommen) &&
               Objects.equals(ausgaben, antrag.ausgaben) &&
               Objects.equals(finanzierungsobjekt, antrag.finanzierungsobjekt) &&
               Objects.equals(kosten, antrag.kosten) &&
               Objects.equals(eigenmittel, antrag.eigenmittel) &&
               Objects.equals(finanzierung, antrag.finanzierung) &&
               Objects.equals(marktwert, antrag.marktwert) &&
               Objects.equals(summeDarlehen, antrag.summeDarlehen) &&
               Objects.equals(summeEigenmittel, antrag.summeEigenmittel) &&
               Objects.equals(monatlicheEinnahmen, antrag.monatlicheEinnahmen) &&
               Objects.equals(monatlicheAusgaben, antrag.monatlicheAusgaben) &&
               Objects.equals(monatlicheDarlehensbelastungen, antrag.monatlicheDarlehensbelastungen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(antragsnummer, antragsteller, einkommen, ausgaben, 
                           finanzierungsobjekt, kosten, eigenmittel, finanzierung,
                           marktwert, summeDarlehen, summeEigenmittel, 
                           monatlicheEinnahmen, monatlicheAusgaben, monatlicheDarlehensbelastungen);
    }

    public String antragsnummer() { return antragsnummer; }

    public String getAntragsnummer() {
        return antragsnummer;
    }

    public void setAntragsnummer(String antragsnummer) {
        this.antragsnummer = antragsnummer;
    }

    public Antragsteller getAntragsteller() {
        return antragsteller;
    }

    public void setAntragsteller(Antragsteller antragsteller) {
        this.antragsteller = antragsteller;
    }

    public void setEinkommen(Einkommen einkommen) {
        this.einkommen = einkommen;
    }

    public void setAusgaben(Ausgaben ausgaben) {
        this.ausgaben = ausgaben;
    }

    public void setFinanzierungsobjekt(Finanzierungsobjekt finanzierungsobjekt) {
        this.finanzierungsobjekt = finanzierungsobjekt;
    }

    public void setKosten(Kosten kosten) {
        this.kosten = kosten;
    }

    public void setEigenmittel(Eigenmittel eigenmittel) {
        this.eigenmittel = eigenmittel;
    }

    public void setFinanzierung(Finanzierung finanzierung) {
        this.finanzierung = finanzierung;
    }

    public String kundennummer() {
        return antragsteller != null ? antragsteller.getKundennummer() : null;
    }

    public String wohnort() {
        return antragsteller != null ? antragsteller.getOrt() : null;
    }

    public Integer kaufnebenkosten() {
        return kosten != null ? kosten.getNebenkosten() : 0;
    }

    public Integer marktwert() {
        return marktwert != null ? marktwert : 0;
    }

    public Integer summeDarlehen() {
        return summeDarlehen != null ? summeDarlehen : 0;
    }

    public Integer summeEigenmittel() {
        return summeEigenmittel != null ? summeEigenmittel : 0;
    }

    public Integer monatlicheEinnahmen() {
        return monatlicheEinnahmen != null ? monatlicheEinnahmen : 0;
    }

    public Integer monatlicheAusgaben() {
        return monatlicheAusgaben != null ? monatlicheAusgaben : 0;
    }

    public Integer monatlicheDarlehensbelastungen() {
        return monatlicheDarlehensbelastungen != null ? monatlicheDarlehensbelastungen : 0;
    }
}
package com.bigpugloans.events.antrag;

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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antrag antrag = (Antrag) o;
        return Objects.equals(antragsnummer, antrag.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }

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

    public Einkommen getEinkommen() {
        return einkommen;
    }

    public void setEinkommen(Einkommen einkommen) {
        this.einkommen = einkommen;
    }

    public Ausgaben getAusgaben() {
        return ausgaben;
    }

    public void setAusgaben(Ausgaben ausgaben) {
        this.ausgaben = ausgaben;
    }

    public Finanzierungsobjekt getFinanzierungsobjekt() {
        return finanzierungsobjekt;
    }

    public void setFinanzierungsobjekt(Finanzierungsobjekt finanzierungsobjekt) {
        this.finanzierungsobjekt = finanzierungsobjekt;
    }

    public Kosten getKosten() {
        return kosten;
    }

    public void setKosten(Kosten kosten) {
        this.kosten = kosten;
    }

    public Eigenmittel getEigenmittel() {
        return eigenmittel;
    }

    public void setEigenmittel(Eigenmittel eigenmittel) {
        this.eigenmittel = eigenmittel;
    }

    public Finanzierung getFinanzierung() {
        return finanzierung;
    }

    public void setFinanzierung(Finanzierung finanzierung) {
        this.finanzierung = finanzierung;
    }
}

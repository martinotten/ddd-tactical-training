package com.bigpugloans.events.antrag;

import java.util.Objects;

public class Ausgaben {
    private int privateKrankenversicherung;
    private int restschuldKredite;
    private int monatlicheBelastungKredite;
    private int lebenshaltungsKosten;
    private int miete;
    private boolean mieteEntfaelltKuenftig;

    public int getPrivateKrankenversicherung() {
        return privateKrankenversicherung;
    }

    public void setPrivateKrankenversicherung(int privateKrankenversicherung) {
        this.privateKrankenversicherung = privateKrankenversicherung;
    }

    public int getRestschuldKredite() {
        return restschuldKredite;
    }

    public void setRestschuldKredite(int restschuldKredite) {
        this.restschuldKredite = restschuldKredite;
    }

    public int getMonatlicheBelastungKredite() {
        return monatlicheBelastungKredite;
    }

    public void setMonatlicheBelastungKredite(int monatlicheBelastungKredite) {
        this.monatlicheBelastungKredite = monatlicheBelastungKredite;
    }

    public int getLebenshaltungsKosten() {
        return lebenshaltungsKosten;
    }

    public void setLebenshaltungsKosten(int lebenshaltungsKosten) {
        this.lebenshaltungsKosten = lebenshaltungsKosten;
    }

    public int getMiete() {
        return miete;
    }

    public void setMiete(int miete) {
        this.miete = miete;
    }

    public boolean isMieteEntfaelltKuenftig() {
        return mieteEntfaelltKuenftig;
    }

    public void setMieteEntfaelltKuenftig(boolean mieteEntfaelltKuenftig) {
        this.mieteEntfaelltKuenftig = mieteEntfaelltKuenftig;
    }

    @Override
    public String toString() {
        return "Ausgaben{" +
                "privateKrankenversicherung=" + privateKrankenversicherung +
                ", restschuldKredite=" + restschuldKredite +
                ", monatlicheBelastungKredite=" + monatlicheBelastungKredite +
                ", lebenshaltungsKosten=" + lebenshaltungsKosten +
                ", miete=" + miete +
                ", mieteEntfaelltKuenftig=" + mieteEntfaelltKuenftig +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ausgaben ausgaben = (Ausgaben) o;
        return privateKrankenversicherung == ausgaben.privateKrankenversicherung && restschuldKredite == ausgaben.restschuldKredite && monatlicheBelastungKredite == ausgaben.monatlicheBelastungKredite && lebenshaltungsKosten == ausgaben.lebenshaltungsKosten && miete == ausgaben.miete && mieteEntfaelltKuenftig == ausgaben.mieteEntfaelltKuenftig;
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateKrankenversicherung, restschuldKredite, monatlicheBelastungKredite, lebenshaltungsKosten, miete, mieteEntfaelltKuenftig);
    }
}

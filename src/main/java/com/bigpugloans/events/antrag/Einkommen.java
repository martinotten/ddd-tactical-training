package com.bigpugloans.events.antrag;

import java.util.Objects;

public class Einkommen {
    private int gehalt;
    private boolean bonusVorhanden;
    private int mietEinnahmenFinanzierungsobjekt;
    private int mietEinnahmenWeitereObjekte;
    private int weitereEinkuenfte;

    private int vermoegenKonten;
    private int vermoegenGoldWertpapiere;

    public int getGehalt() {
        return gehalt;
    }

    public void setGehalt(int gehalt) {
        this.gehalt = gehalt;
    }

    public boolean isBonusVorhanden() {
        return bonusVorhanden;
    }

    public void setBonusVorhanden(boolean bonusVorhanden) {
        this.bonusVorhanden = bonusVorhanden;
    }

    public int getMietEinnahmenFinanzierungsobjekt() {
        return mietEinnahmenFinanzierungsobjekt;
    }

    public void setMietEinnahmenFinanzierungsobjekt(int mietEinnahmenFinanzierungsobjekt) {
        this.mietEinnahmenFinanzierungsobjekt = mietEinnahmenFinanzierungsobjekt;
    }

    public int getMietEinnahmenWeitereObjekte() {
        return mietEinnahmenWeitereObjekte;
    }

    public void setMietEinnahmenWeitereObjekte(int mietEinnahmenWeitereObjekte) {
        this.mietEinnahmenWeitereObjekte = mietEinnahmenWeitereObjekte;
    }

    public int getWeitereEinkuenfte() {
        return weitereEinkuenfte;
    }

    public void setWeitereEinkuenfte(int weitereEinkuenfte) {
        this.weitereEinkuenfte = weitereEinkuenfte;
    }

    public int getVermoegenKonten() {
        return vermoegenKonten;
    }

    public void setVermoegenKonten(int vermoegenKonten) {
        this.vermoegenKonten = vermoegenKonten;
    }

    public int getVermoegenGoldWertpapiere() {
        return vermoegenGoldWertpapiere;
    }

    public void setVermoegenGoldWertpapiere(int vermoegenGoldWertpapiere) {
        this.vermoegenGoldWertpapiere = vermoegenGoldWertpapiere;
    }

    @Override
    public String toString() {
        return "Einkommen{" +
                "gehalt=" + gehalt +
                ", bonusVorhanden=" + bonusVorhanden +
                ", mietEinnahmenFinanzierungsobjekt=" + mietEinnahmenFinanzierungsobjekt +
                ", mietEinnahmenWeitereObjekte=" + mietEinnahmenWeitereObjekte +
                ", weitereEinkuenfte=" + weitereEinkuenfte +
                ", vermoegenKonten=" + vermoegenKonten +
                ", vermoegenGoldWertpapiere=" + vermoegenGoldWertpapiere +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Einkommen einkommen = (Einkommen) o;
        return gehalt == einkommen.gehalt && bonusVorhanden == einkommen.bonusVorhanden && mietEinnahmenFinanzierungsobjekt == einkommen.mietEinnahmenFinanzierungsobjekt && mietEinnahmenWeitereObjekte == einkommen.mietEinnahmenWeitereObjekte && weitereEinkuenfte == einkommen.weitereEinkuenfte && vermoegenKonten == einkommen.vermoegenKonten && vermoegenGoldWertpapiere == einkommen.vermoegenGoldWertpapiere;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gehalt, bonusVorhanden, mietEinnahmenFinanzierungsobjekt, mietEinnahmenWeitereObjekte, weitereEinkuenfte, vermoegenKonten, vermoegenGoldWertpapiere);
    }
}

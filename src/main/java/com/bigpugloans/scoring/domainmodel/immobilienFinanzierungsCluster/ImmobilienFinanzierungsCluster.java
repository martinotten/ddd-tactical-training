package com.bigpugloans.scoring.domainmodel.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domainmodel.Eigenkapitalanteil;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;
import com.bigpugloans.scoring.domainmodel.Waehrungsbetrag;

public class ImmobilienFinanzierungsCluster {
    private Waehrungsbetrag summeDarlehen;
    private Waehrungsbetrag beleihungswert;
    private Waehrungsbetrag eigenmittel;
    private Waehrungsbetrag marktwertImmobilie;
    private Waehrungsbetrag kaufnebenkosten;
    private Eigenkapitalanteil eigenkapitalanteil;
    private boolean marktwertDurchschnittlich;

    public ImmobilienFinanzierungsCluster() {
        this.summeDarlehen = new Waehrungsbetrag(0);
        this.beleihungswert = new Waehrungsbetrag(0);
        this.eigenmittel = new Waehrungsbetrag(0);
        this.marktwertImmobilie = new Waehrungsbetrag(0);
        this.kaufnebenkosten = new Waehrungsbetrag(0);
        this.eigenkapitalanteil = new Eigenkapitalanteil(new Prozentwert(0));
    }

    public void setSummeDarlehen(Waehrungsbetrag summeDarlehen) {
        this.summeDarlehen = summeDarlehen;
    }

    public void setBeleihungswert(Waehrungsbetrag beleihungswert) {
        this.beleihungswert = beleihungswert;
    }

    public void setEigenmittel(Waehrungsbetrag eigenmittel) {
        this.eigenmittel = eigenmittel;
    }

    public void setMarktwertImmobilie(Waehrungsbetrag marktwertImmobilie) {
        this.marktwertImmobilie = marktwertImmobilie;
    }

    public void setKaufnebenkosten(Waehrungsbetrag kaufnebenkosten) {
        this.kaufnebenkosten = kaufnebenkosten;
    }

    public boolean koKriteriumIstErfuellt() {
        return summeDarlehen.groesserAls(beleihungswert) || !summeDarlehen.plus(eigenmittel).equals(marktwertImmobilie.plus(kaufnebenkosten));
    }

    public boolean pruefeKoKriterium() {
        return summeDarlehen.groesserAls(beleihungswert) || !summeDarlehen.plus(eigenmittel).equals(marktwertImmobilie.plus(kaufnebenkosten));
    }

    public Punkte berechnePunkte() {
        int punkteEigenkapital = eigenkapitalanteil.berechnePunkte().getPunkte();
        int ergebnis = punkteEigenkapital + (marktwertDurchschnittlich ? 10 : 0);

        return new Punkte(ergebnis);
    }

    public void setEigenkapitalanteil(Prozentwert eigenkapitalanteil) {
        this.eigenkapitalanteil = new Eigenkapitalanteil(eigenkapitalanteil);
    }

    public void setMarktwertDurchschnittlich(boolean durchschnittlicherMarktwert) {
        this.marktwertDurchschnittlich = durchschnittlicherMarktwert;
    }
}

package com.bigpugloans.scoring.domainmodel;

public class Immobilie {
    private boolean marktwertDurchschnittlich;

    public void setMarktwertDurchschnittlich(boolean marktwertDurchschnittlich) {
        this.marktwertDurchschnittlich = marktwertDurchschnittlich;
    }

    public Punkte berechnePunkte() {
        if(marktwertDurchschnittlich) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }

    }
}

package com.bigpugloans.scoring.domainmodel;

public class Antragsteller {
    private String wohnort;

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public Punkte berechnePunkte() {
        if ("Hamburg".equals(wohnort)) {
            return new Punkte(5);
        } else if ("München".equals(wohnort)) {
            return new Punkte(5);
        }else {
            return new Punkte(0);
        }
    }
}

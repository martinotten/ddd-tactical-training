package com.bigpugloans.scoring.domain.model;

import org.jmolecules.architecture.onion.classical.DomainModelRing;

import java.math.BigDecimal;

@DomainModelRing
public class Prozentwert {
    private BigDecimal wert;

    private Prozentwert() {
    }

    public Prozentwert(BigDecimal wert) {
        this.wert = wert;
    }

    public BigDecimal getWert() {
        return wert;
    }

    public Prozentwert(int wert) {
        this.wert = new BigDecimal(wert);
    }

    public boolean kleinerAls(Prozentwert andererProzentwert) {
        return wert.compareTo(andererProzentwert.wert) < 0;
    }

    public boolean groesserAls(Prozentwert andererProzentwert) {
        return wert.compareTo(andererProzentwert.wert) > 0;
    }

    public boolean zwischen(Prozentwert von, Prozentwert bis) {
        return wert.compareTo(von.wert) >= 0 && wert.compareTo(bis.wert) <= 0;
    }
}

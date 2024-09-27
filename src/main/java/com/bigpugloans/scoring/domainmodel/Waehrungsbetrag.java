package com.bigpugloans.scoring.domainmodel;

import java.math.BigDecimal;

public class Waehrungsbetrag {
    private final BigDecimal betrag;

    public Waehrungsbetrag(int betrag) {
        this.betrag = new BigDecimal(betrag);
    }

    public boolean groesserAls(Waehrungsbetrag andererBetrag) {
        return betrag.compareTo(andererBetrag.betrag) > 0;
    }

    public boolean kleinerAls(Waehrungsbetrag andererBetrag) {
        return betrag.compareTo(andererBetrag.betrag) < 0;
    }

    public Waehrungsbetrag plus(Waehrungsbetrag andererBetrag) {
        return new Waehrungsbetrag(betrag.add(andererBetrag.betrag).intValue());
    }

    public Waehrungsbetrag minus(Waehrungsbetrag andererBetrag) {
        return new Waehrungsbetrag(betrag.subtract(andererBetrag.betrag).intValue());
    }
}

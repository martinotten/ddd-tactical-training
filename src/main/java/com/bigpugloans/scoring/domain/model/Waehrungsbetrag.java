package com.bigpugloans.scoring.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Waehrungsbetrag {
    private final BigDecimal betrag;

    public Waehrungsbetrag(int betrag) {
        this.betrag = new BigDecimal(betrag);
    }

    public Waehrungsbetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public BigDecimal betrag() {
        return betrag;
    }

    public boolean groesserAls(Waehrungsbetrag andererBetrag) {
        if(andererBetrag == null) {
            andererBetrag = new Waehrungsbetrag(0);
        }
        return betrag.compareTo(andererBetrag.betrag) > 0;
    }

    public boolean kleinerAls(Waehrungsbetrag andererBetrag) {
        if(andererBetrag == null) {
            andererBetrag = new Waehrungsbetrag(0);
        }
        return betrag.compareTo(andererBetrag.betrag) < 0;
    }

    public Waehrungsbetrag plus(Waehrungsbetrag andererBetrag) {
        if(andererBetrag == null) {
            andererBetrag = new Waehrungsbetrag(0);
        }
        return new Waehrungsbetrag(betrag.add(andererBetrag.betrag).intValue());
    }

    public Waehrungsbetrag minus(Waehrungsbetrag andererBetrag) {
        if(andererBetrag == null) {
            andererBetrag = new Waehrungsbetrag(0);
        }
        return new Waehrungsbetrag(betrag.subtract(andererBetrag.betrag).intValue());
    }

    public Prozentwert anteilVon(Waehrungsbetrag andererBetrag) {
        if(andererBetrag == null) {
            andererBetrag = new Waehrungsbetrag(0);
        }
        BigDecimal divided = this.betrag.divide(andererBetrag.betrag, 2, RoundingMode.HALF_UP);
        return new Prozentwert(divided.multiply(new BigDecimal(100)));
    }

    @Override
    public String toString() {
        return "Waehrungsbetrag{" +
                "betrag=" + betrag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waehrungsbetrag that = (Waehrungsbetrag) o;
        return Objects.equals(betrag, that.betrag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(betrag);
    }
}

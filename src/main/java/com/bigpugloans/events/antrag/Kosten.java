package com.bigpugloans.events.antrag;

import java.util.Objects;

public class Kosten {
    private int kostenGrundstueck;
    private int kaufpreisOderBaukosten;
    private int kostenUmbaumassnahmen;
    private int nebenkosten;

    public int getKostenGrundstueck() {
        return kostenGrundstueck;
    }

    public void setKostenGrundstueck(int kostenGrundstueck) {
        this.kostenGrundstueck = kostenGrundstueck;
    }

    public int getKaufpreisOderBaukosten() {
        return kaufpreisOderBaukosten;
    }

    public void setKaufpreisOderBaukosten(int kaufpreisOderBaukosten) {
        this.kaufpreisOderBaukosten = kaufpreisOderBaukosten;
    }

    public int getKostenUmbaumassnahmen() {
        return kostenUmbaumassnahmen;
    }

    public void setKostenUmbaumassnahmen(int kostenUmbaumassnahmen) {
        this.kostenUmbaumassnahmen = kostenUmbaumassnahmen;
    }

    public int getNebenkosten() {
        return nebenkosten;
    }

    public void setNebenkosten(int nebenkosten) {
        this.nebenkosten = nebenkosten;
    }

    @Override
    public String toString() {
        return "Kosten{" +
                "kostenGrundstueck=" + kostenGrundstueck +
                ", kaufpreisOderBaukosten=" + kaufpreisOderBaukosten +
                ", kostenUmbaumassnahmen=" + kostenUmbaumassnahmen +
                ", nebenkosten=" + nebenkosten +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kosten kosten = (Kosten) o;
        return kostenGrundstueck == kosten.kostenGrundstueck && kaufpreisOderBaukosten == kosten.kaufpreisOderBaukosten && kostenUmbaumassnahmen == kosten.kostenUmbaumassnahmen && nebenkosten == kosten.nebenkosten;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kostenGrundstueck, kaufpreisOderBaukosten, kostenUmbaumassnahmen, nebenkosten);
    }
}

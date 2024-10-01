package com.bigpugloans.scoring.domainmodel.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domainmodel.KoKriterien;
import com.bigpugloans.scoring.domainmodel.Prozentwert;
import com.bigpugloans.scoring.domainmodel.Punkte;

import java.util.Objects;

public class RueckzahlungsWahrscheinlichkeit {
    private Prozentwert rueckzahlungsWahrscheinlichkeit;

    public RueckzahlungsWahrscheinlichkeit(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungsWahrscheinlichkeit = rueckzahlungsWahrscheinlichkeit;
    }

    public Punkte berechnePunkte() {
        return new Punkte(rueckzahlungsWahrscheinlichkeit.getWert().intValue());
    }

    public KoKriterien bestimmeKoKriterien() {
        if(rueckzahlungsWahrscheinlichkeit.kleinerAls(new Prozentwert(60) )) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    @Override
    public String toString() {
        return "RueckzahlungsWahrscheinlichkeit{" +
                "rueckzahlungsWahrscheinlichkeit=" + rueckzahlungsWahrscheinlichkeit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RueckzahlungsWahrscheinlichkeit that = (RueckzahlungsWahrscheinlichkeit) o;
        return Objects.equals(rueckzahlungsWahrscheinlichkeit, that.rueckzahlungsWahrscheinlichkeit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rueckzahlungsWahrscheinlichkeit);
    }
}

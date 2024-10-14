package com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster;

import com.bigpugloans.scoring.domain.model.KoKriterien;
import com.bigpugloans.scoring.domain.model.Prozentwert;
import com.bigpugloans.scoring.domain.model.Punkte;

import java.util.Objects;

class RueckzahlungsWahrscheinlichkeit {
    private Prozentwert rueckzahlungsWahrscheinlichkeit;

    Prozentwert rueckzahlungsWahrscheinlichkeit() {
        return rueckzahlungsWahrscheinlichkeit;
    }

    RueckzahlungsWahrscheinlichkeit(Prozentwert rueckzahlungsWahrscheinlichkeit) {
        this.rueckzahlungsWahrscheinlichkeit = rueckzahlungsWahrscheinlichkeit;
    }

    Punkte berechnePunkte() {
        return new Punkte(rueckzahlungsWahrscheinlichkeit.getWert().intValue());
    }

    KoKriterien bestimmeKoKriterien() {
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

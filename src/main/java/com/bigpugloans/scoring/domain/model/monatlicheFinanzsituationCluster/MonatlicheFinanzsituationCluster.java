package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Objects;

@DomainModelRing
@AggregateRoot
public class MonatlicheFinanzsituationCluster {
    @Identity
    private final Antragsnummer antragsnummer;
    private Waehrungsbetrag einnahmen;
    private Waehrungsbetrag ausgaben;
    private Waehrungsbetrag neueDarlehensBelastungen;

    public MonatlicheFinanzsituationCluster(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
    }

    public KoKriterien pruefeKoKriterium() {
        if(einnahmen
                .minus(ausgaben)
                .minus(neueDarlehensBelastungen)
                .kleinerAls(new Waehrungsbetrag(0))) {
            return new KoKriterien(1);
        } else {
            return new KoKriterien(0);
        }
    }

    public Punkte berechnePunkte() {
        Waehrungsbetrag monatlicherUeberschussOhneTilgungen = einnahmen.minus(ausgaben);
        if(monatlicherUeberschussOhneTilgungen.groesserAls(new Waehrungsbetrag(1500))) {
            return new Punkte(15);
        } else {
            return new Punkte(0);
        }
    }

    public ClusterScoringEvent scoren() {
        if(einnahmen == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Einnahmen fehlen.");
        }
        if(ausgaben == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Ausgaben fehlen.");
        }
        if(neueDarlehensBelastungen == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Neue Darlehensbelastungen fehlen.");
        }
        return new ClusterGescored(this.antragsnummer, berechnePunkte(), pruefeKoKriterium());
    }

    public void monatlicheEinnahmenHinzufuegen(Waehrungsbetrag monatlicheEinnahmen) {
        this.einnahmen = monatlicheEinnahmen;
    }

    public void monatlicheAusgabenHinzufuegen(Waehrungsbetrag monatlicheAusgaben) {
        this.ausgaben = monatlicheAusgaben;
    }

    public void monatlicheDarlehensbelastungenHinzufuegen(Waehrungsbetrag monatlicheDarlehensBelastungen) {
        this.neueDarlehensBelastungen = monatlicheDarlehensBelastungen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonatlicheFinanzsituationCluster that = (MonatlicheFinanzsituationCluster) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}

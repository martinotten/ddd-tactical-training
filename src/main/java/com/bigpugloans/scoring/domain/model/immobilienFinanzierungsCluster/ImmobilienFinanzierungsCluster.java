package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.*;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Objects;

@DomainModelRing
@AggregateRoot
public class ImmobilienFinanzierungsCluster {
    @Identity
    private final Antragsnummer antragsnummer;

    private Waehrungsbetrag beleihungswert;
    private MarktwertVergleich marktwertVergleich;
    private Waehrungsbetrag marktwert;
    private Waehrungsbetrag kaufnebenkosten;
    private Waehrungsbetrag summeDarlehen;
    private Waehrungsbetrag eigenmittel;


    public ImmobilienFinanzierungsCluster(Antragsnummer antragsnummer) {
        if(antragsnummer == null) {
            throw new IllegalArgumentException("Antragsnummer darf nicht null sein.");
        }
        this.antragsnummer = antragsnummer;
    }

    public Antragsnummer antragsnummer() {
        return antragsnummer;
    }

    private KoKriterien pruefeKoKriterium() {
        int anzahlKoKriterien = 0;
        if (summeDarlehen.groesserAls(beleihungswert)) {
            anzahlKoKriterien++;
        }
        
        if(!summeDarlehen.plus(eigenmittel).equals(marktwert.plus(kaufnebenkosten))) {
            anzahlKoKriterien++;
        }
        return new KoKriterien(anzahlKoKriterien);
    }

    private Punkte berechnePunkte() {
        Punkte ergebnis = new Punkte(0);
        Prozentwert eigenkapitalanteil = berechneEigenkapitalAnteil();
        if (eigenkapitalanteil.zwischen(new Prozentwert(15), new Prozentwert(20))) {
            ergebnis = ergebnis.plus(new Punkte(5));
        } else if (eigenkapitalanteil.zwischen(new Prozentwert(20), new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(10));
        } else if (eigenkapitalanteil.groesserAls(new Prozentwert(30))) {
            ergebnis = ergebnis.plus(new Punkte(15));
        }


        ergebnis = ergebnis.plus(marktwertVergleich.berechnePunkte(marktwert));
        return ergebnis;
    }

    private Prozentwert berechneEigenkapitalAnteil() {
        return eigenmittel.anteilVon(marktwert.plus(kaufnebenkosten));
    }

    public ClusterScoringEvent scoren() {
        if(beleihungswert == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Der Beleihungswert fehlt.");
        }
        if(marktwert == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Der Marktwert fehlt.");
        }
        if(kaufnebenkosten == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Die Kaufnebenkosten fehlen.");
        }
        if (summeDarlehen == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Die Summe der Darlehen fehlt.");
        }
        if (eigenmittel == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Die Summe der Eigenmittel fehlt.");
        }
        if (marktwertVergleich == null) {
            return new ClusterKonnteNochNichtGescoredWerden(this.antragsnummer, "Der Marktwertvergleich fehlt.");
        }

        return new ClusterGescored(this.antragsnummer, berechnePunkte(), pruefeKoKriterium());
    }

    public void beleihungswertHinzufuegen(Waehrungsbetrag beleihungswert) {
        this.beleihungswert = beleihungswert;
    }

    public void summeDarlehenHinzufuegen(Waehrungsbetrag summeDarlehen) {
        this.summeDarlehen = summeDarlehen;
    }

    public void summeEigenmittelHinzufuegen(Waehrungsbetrag summeEigenmittel) { 
        this.eigenmittel = summeEigenmittel;
    }

    public void marktwertHinzufuegen(Waehrungsbetrag marktwert) {
        this.marktwert = marktwert;
    }

    public void kaufnebenkostenHinzufuegen(Waehrungsbetrag kaufnebenkosten) {
        this.kaufnebenkosten = kaufnebenkosten;
    }

    public void marktwertVerlgeichHinzufuegen(Waehrungsbetrag minimalerMarktwert, Waehrungsbetrag maximalerMarktwert, Waehrungsbetrag durchschnittlicherMarktwertVon, Waehrungsbetrag durchschnittlicherMarktwertBis) {
        this.marktwertVergleich = new MarktwertVergleich(minimalerMarktwert, maximalerMarktwert, durchschnittlicherMarktwertVon, durchschnittlicherMarktwertBis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmobilienFinanzierungsCluster that = (ImmobilienFinanzierungsCluster) o;
        return Objects.equals(antragsnummer, that.antragsnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragsnummer);
    }
}



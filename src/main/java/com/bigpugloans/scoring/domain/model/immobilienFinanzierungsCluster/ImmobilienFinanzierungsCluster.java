package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.*;

import java.math.BigDecimal;
import java.util.Objects;

public class ImmobilienFinanzierungsCluster {
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

    public ImmobilienFinanzierungsClusterMemento memento() {
        ImmobilienFinanzierungsClusterMemento memento = new ImmobilienFinanzierungsClusterMemento(antragsnummer.nummer());
        if(this.beleihungswert != null) {
            memento.beleihungswert(beleihungswert.betrag());
        }
        if(this.marktwert != null) {
            memento.marktwert(marktwert.betrag());
        }
        if(this.kaufnebenkosten != null) {
            memento.kaufnebenkosten(kaufnebenkosten.betrag());
        }
        if(this.summeDarlehen != null) {
            memento.summeDarlehen(summeDarlehen.betrag());
        }
        if(this.eigenmittel != null) {
            memento.eigenmittel(eigenmittel.betrag());
        }
        if(this.marktwertVergleich != null) {
            memento.minimalerMarktwert(marktwertVergleich.minimalerMarktwert().betrag());
            memento.maximalerMarktwert(marktwertVergleich.maximalerMarktwert().betrag());
            memento.durchschnittlicherMarktwertVon(marktwertVergleich.durchschnittlicherMarktwertVon().betrag());
            memento.durchschnittlicherMarktwertBis(marktwertVergleich.durchschnittlicherMarktwertBis().betrag());
        }
        return memento;
    }

    public static ImmobilienFinanzierungsCluster fromMemento(ImmobilienFinanzierungsClusterMemento memento) {
        Antragsnummer antragsnummer = new Antragsnummer(memento.antragsnummer);
        ImmobilienFinanzierungsCluster cluster = new ImmobilienFinanzierungsCluster(antragsnummer);
        if(memento.beleihungswert != null) {
            cluster.beleihungswertHinzufuegen(new Waehrungsbetrag(memento.beleihungswert));
        }
        if(memento.marktwert != null) {
            cluster.marktwertHinzufuegen(new Waehrungsbetrag(memento.marktwert));
        }
        if(memento.kaufnebenkosten != null) {
            cluster.kaufnebenkostenHinzufuegen(new Waehrungsbetrag(memento.kaufnebenkosten));
        }
        if(memento.summeDarlehen != null) {
            cluster.summeDarlehenHinzufuegen(new Waehrungsbetrag(memento.summeDarlehen));
        }
        if(memento.eigenmittel != null) {
            cluster.summeEigenmittelHinzufuegen(new Waehrungsbetrag(memento.eigenmittel));
        }
        if(memento.minimalerMarktwert != null && memento.maximalerMarktwert != null && memento.durchschnittlicherMarktwertVon != null && memento.durchschnittlicherMarktwertBis != null) {
            cluster.marktwertVerlgeichHinzufuegen(new Waehrungsbetrag(memento.minimalerMarktwert), new Waehrungsbetrag(memento.maximalerMarktwert), new Waehrungsbetrag(memento.durchschnittlicherMarktwertVon), new Waehrungsbetrag(memento.durchschnittlicherMarktwertBis));
        }
        return cluster;
    }

    public static class ImmobilienFinanzierungsClusterMemento {
        private String antragsnummer;
        private BigDecimal beleihungswert;
        private BigDecimal minimalerMarktwert;
        private BigDecimal maximalerMarktwert;
        private BigDecimal durchschnittlicherMarktwertVon;
        private BigDecimal durchschnittlicherMarktwertBis;
        private BigDecimal marktwert;
        private BigDecimal kaufnebenkosten;
        private BigDecimal summeDarlehen;
        private BigDecimal eigenmittel;

        public ImmobilienFinanzierungsClusterMemento() {
        }

        public ImmobilienFinanzierungsClusterMemento(String antragsnummer) {
            this.antragsnummer = antragsnummer;
        }

        public String antragsnummer() {
            return antragsnummer;
        }
        public void beleihungswert(BigDecimal beleihungswert) {
            this.beleihungswert = beleihungswert;
        }
        public BigDecimal beleihungswert() {
            return beleihungswert;
        }

        public void minimalerMarktwert(BigDecimal minimalerMarktwert) {
            this.minimalerMarktwert = minimalerMarktwert;
        }
        public BigDecimal minimalerMarktwert() {
            return minimalerMarktwert;
        }

        public void maximalerMarktwert(BigDecimal maximalerMarktwert) {
            this.maximalerMarktwert = maximalerMarktwert;
        }
        public BigDecimal maximalerMarktwert() {
            return maximalerMarktwert;
        }

        public void durchschnittlicherMarktwertVon(BigDecimal durchschnittlicherMarktwertVon) {
            this.durchschnittlicherMarktwertVon = durchschnittlicherMarktwertVon;
        }
        public BigDecimal durchschnittlicherMarktwertVon() {
            return durchschnittlicherMarktwertVon;
        }

        public void durchschnittlicherMarktwertBis(BigDecimal durchschnittlicherMarktwertBis) {
            this.durchschnittlicherMarktwertBis = durchschnittlicherMarktwertBis;
        }
        public BigDecimal durchschnittlicherMarktwertBis() {
            return durchschnittlicherMarktwertBis;
        }

        public void marktwert(BigDecimal marktwert) {
            this.marktwert = marktwert;
        }
        public BigDecimal marktwert() {
            return marktwert;
        }

        public void kaufnebenkosten(BigDecimal kaufnebenkosten) {
            this.kaufnebenkosten = kaufnebenkosten;
        }
        public BigDecimal kaufnebenkosten() {
            return kaufnebenkosten;
        }

        public void summeDarlehen(BigDecimal summeDarlehen) {
            this.summeDarlehen = summeDarlehen;
        }
        public BigDecimal summeDarlehen() {
            return summeDarlehen;
        }

        public void eigenmittel(BigDecimal eigenmittel) {
            this.eigenmittel = eigenmittel;
        }
        public BigDecimal eigenmittel() {
            return eigenmittel;
        }


    }
}



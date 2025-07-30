package com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster;

import com.bigpugloans.scoring.domain.model.*;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Optional;

@Entity
public class ImmobilienFinanzierungsCluster implements ClusterScoring {
    @Id
    @GeneratedValue
    private Long id;
    
    @Embedded 
    private ScoringId scoringId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "beleihungswert"))
    })
    private Waehrungsbetrag beleihungswert;

    @Embedded
    private MarktwertVergleich marktwertVergleich;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "marktwert"))
    })
    private Waehrungsbetrag marktwert;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "kaufnebenkosten"))
    })
    private Waehrungsbetrag kaufnebenkosten;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "summeDarlehen"))
    })
    private Waehrungsbetrag summeDarlehen;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "eigenmittel"))
    })
    private Waehrungsbetrag eigenmittel;

    private ImmobilienFinanzierungsCluster() {
        // JPA constructor
    }
    
    public ImmobilienFinanzierungsCluster(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringID darf nicht null sein.");
        }
        this.scoringId = scoringId;
    }

    public ScoringId scoringId() {
        return scoringId;
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

    public Optional<ClusterGescored> scoren() {
        if(beleihungswert == null) {
            return Optional.empty();
        }
        if(marktwert == null) {
            return Optional.empty();
        }
        if(kaufnebenkosten == null) {
            return Optional.empty();
        }
        if (summeDarlehen == null) {
            return Optional.empty();
        }
        if (eigenmittel == null) {
            return Optional.empty();
        }
        if (marktwertVergleich == null) {
            return Optional.empty();
        }

        return Optional.of(new ClusterGescored(this.scoringId, berechnePunkte(), pruefeKoKriterium()));
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
        return Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoringId);
    }
}



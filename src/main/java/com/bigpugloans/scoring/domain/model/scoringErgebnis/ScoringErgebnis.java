package com.bigpugloans.scoring.domain.model.scoringErgebnis;

import com.bigpugloans.scoring.domain.model.*;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungsCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@DomainModelRing
@AggregateRoot
public class ScoringErgebnis {
    @Identity
    private final ScoringId scoringId;

    private ClusterGescored antragstellerClusterErgebnis;
    private ClusterGescored auskunfteiClusterErgebnis;
    private ClusterGescored immobilienFinanzierungsClusterErgebnis;
    private ClusterGescored monatlicherHaushaltsueberschussClusterErgebnis;

    private KoKriterien koKriterien;
    private Punkte gesamtPunkte;

    public ScoringErgebnis(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }
        this.scoringId = scoringId;
        this.gesamtPunkte = new Punkte(0);
        this.koKriterien = new KoKriterien(0);
    }

    /**
     * Factory method to create a ScoringErgebnis from cluster scoring results.
     * This method encapsulates the business logic for building a complete scoring result.
     */
    public static Optional<ScoringErgebnis> ausClusterErgebnissen(ScoringId scoringId, Set<ClusterScoring> clusters) {
        if (clusters.isEmpty()) {
            return Optional.empty();
        }

        // Score all clusters
        var scoringResults = clusters.stream()
                .map(ClusterScoring::scoren)
                .toList();

        // Check if any cluster failed to score
        if (scoringResults.stream().anyMatch(Optional::isEmpty)) {
            return Optional.empty();
        }

        // Create the scoring result and add all cluster results
        ScoringErgebnis scoringErgebnis = new ScoringErgebnis(scoringId);
        var clusterList = clusters.stream().toList();
        
        for (int i = 0; i < clusterList.size(); i++) {
            var clusterResult = scoringResults.get(i);
            var cluster = clusterList.get(i);

            clusterResult.ifPresent(clusterGescored -> scoringErgebnis.addClusterResult(clusterGescored, cluster));
        }
        
        return Optional.of(scoringErgebnis);
    }

    /**
     * Adds a cluster result to this scoring result.
     * This method encapsulates the business logic for handling different cluster types.
     */
    private void addClusterResult(ClusterGescored clusterResult, ClusterScoring cluster) {
        switch (cluster) {
            case AntragstellerCluster ignored -> antragstellerClusterHinzufuegen(clusterResult);
            case MonatlicheFinanzsituationCluster ignored -> monatlicheFinansituationClusterHinzufuegen(clusterResult);
            case ImmobilienFinanzierungsCluster ignored -> immobilienFinanzierungClusterHinzufuegen(clusterResult);
            case AuskunfteiErgebnisCluster ignored -> auskunfteiErgebnisClusterHinzufuegen(clusterResult);
            default -> throw new IllegalArgumentException("Unknown cluster type: " + cluster.getClass().getSimpleName());
        }
    }

    public ScoringId scoringId() {
        return scoringId;
    }

    public Optional<AntragErfolgreichGescored> berechneErgebnis() {
        if (koKriterien.anzahl() > 0) {
            return Optional.of(new AntragErfolgreichGescored(scoringId, ScoringFarbe.ROT));
        }

        if(antragstellerClusterErgebnis != null
                && auskunfteiClusterErgebnis !=null
                && immobilienFinanzierungsClusterErgebnis !=null
                && monatlicherHaushaltsueberschussClusterErgebnis !=null) {

            if (gesamtPunkte.groesserAls(new Punkte(119))) {
                return Optional.of(new AntragErfolgreichGescored(scoringId, ScoringFarbe.GRUEN));
            } else {
                return Optional.of(new AntragErfolgreichGescored(scoringId, ScoringFarbe.ROT));
            }
        } else {
            return Optional.empty();
        }
    }

    public void auskunfteiErgebnisClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.scoringId().equals(this.scoringId)) {
            this.auskunfteiClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "ScoringId von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.scoringId,
                    clusterGescored.scoringId()
            ));
        }
    }


    public void antragstellerClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.scoringId().equals(this.scoringId)) {
            this.antragstellerClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "ScoringId von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.scoringId,
                    clusterGescored.scoringId()
            ));
        }
    }

    public void immobilienFinanzierungClusterHinzufuegen(ClusterGescored clusterGescored) {
        if (clusterGescored.scoringId().equals(this.scoringId)) {
            this.immobilienFinanzierungsClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "ScoringId von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.scoringId,
                    clusterGescored.scoringId()
            ));
        }
    }


    public void monatlicheFinansituationClusterHinzufuegen(ClusterGescored clusterGescored) {
        if(clusterGescored.scoringId().equals(this.scoringId)) {
            this.monatlicherHaushaltsueberschussClusterErgebnis = clusterGescored;
            this.gesamtPunkte = gesamtPunkte.plus(clusterGescored.punkte());
            this.koKriterien = new KoKriterien(this.koKriterien.anzahl() + clusterGescored.koKriterien().anzahl());
        } else {
            throw new IllegalArgumentException(String.format(
                "ScoringId von ScoringErgebnis (%s) und ClusterGescored (%s) stimmt nicht überein.",
                    this.scoringId,
                    clusterGescored.scoringId()
            ));
        }
    }

    @Override
    public String toString() {
        return "ScoringErgebnis{" +
                "scoringId=" + scoringId +
                ", antragstellerClusterErgebnis=" + antragstellerClusterErgebnis +
                ", auskunfteiClusterErgebnis=" + auskunfteiClusterErgebnis +
                ", immobilienFinanzierungsClusterErgebnis=" + immobilienFinanzierungsClusterErgebnis +
                ", monatlicherHaushaltsueberschussClusterErgebnis=" + monatlicherHaushaltsueberschussClusterErgebnis +
                ", koKriterien=" + koKriterien +
                ", gesamtPunkte=" + gesamtPunkte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoringErgebnis that = (ScoringErgebnis) o;
        return Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoringId);
    }
}

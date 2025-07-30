package com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.*;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Optional;

@Entity
public class MonatlicheFinanzsituationCluster implements ClusterScoring {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ScoringId scoringId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "einnahmen")
            )
    })
    private Waehrungsbetrag einnahmen;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "ausgaben")
            )
    })
    private Waehrungsbetrag ausgaben;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "betrag", column = @Column(name = "neueDarlehensBelastungen")
            )
    })
    private Waehrungsbetrag neueDarlehensBelastungen;

    private MonatlicheFinanzsituationCluster() {
        // JPA constructor
    }

    public MonatlicheFinanzsituationCluster(ScoringId scoringId) {
        if(scoringId == null) {
            throw new IllegalArgumentException("ScoringId darf nicht null sein.");
        }
        this.scoringId = scoringId;
    }

    public ScoringId scoringId() {
        return scoringId;
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

    public Optional<ClusterGescored> scoren() {
        if(einnahmen == null) {
            return Optional.empty();
        }
        if(ausgaben == null) {
            return Optional.empty();
        }
        if(neueDarlehensBelastungen == null) {
            return Optional.empty();
        }
        return Optional.of(new ClusterGescored(this.scoringId, berechnePunkte(), pruefeKoKriterium()));
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
        return Objects.equals(scoringId, that.scoringId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scoringId);
    }
}

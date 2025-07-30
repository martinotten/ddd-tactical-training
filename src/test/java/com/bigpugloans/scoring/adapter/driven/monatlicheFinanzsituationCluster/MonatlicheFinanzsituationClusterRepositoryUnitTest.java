package com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testutils.RepositoryTestBase;
import com.bigpugloans.scoring.testutils.TestMonatlicheFinanzsituationClusterSpringDataRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonatlicheFinanzsituationClusterRepositoryUnitTest extends RepositoryTestBase {

    @Test
    void shouldSaveAndLoadCluster() {
        // Arrange
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("TEST123");
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);
        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        cluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1000));
        cluster.monatlicheDarlehensbelastungenHinzufuegen(new Waehrungsbetrag(500));

        // Act
        repository.speichern(cluster);
        MonatlicheFinanzsituationCluster loaded = repository.lade(scoringId);

        // Assert
        assertNotNull(loaded);
        assertEquals(scoringId, loaded.scoringId());
        
        var memento = loaded.memento();
        assertNotNull(memento);
        assertEquals(3000, memento.einnahmen().intValue());
        assertEquals(1000, memento.ausgaben().intValue());
        assertEquals(500, memento.neueDarlehensBelastungen().intValue());
    }

    @Test
    void shouldReturnNullWhenClusterNotFound() {
        // Arrange
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        ScoringId nonExistentId = ScoringId.preScoringIdAusAntragsnummer("NONEXISTENT");

        // Act
        MonatlicheFinanzsituationCluster result = repository.lade(nonExistentId);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldUpdateExistingCluster() {
        // Arrange
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("TEST456");
        
        // Save initial cluster
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);
        cluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2000));
        repository.speichern(cluster);

        // Update cluster
        MonatlicheFinanzsituationCluster updatedCluster = new MonatlicheFinanzsituationCluster(scoringId);
        updatedCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(5000));
        updatedCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(2000));

        // Act
        repository.speichern(updatedCluster);
        MonatlicheFinanzsituationCluster loaded = repository.lade(scoringId);

        // Assert
        assertNotNull(loaded);
        var memento = loaded.memento();
        assertEquals(5000, memento.einnahmen().intValue());
        assertEquals(2000, memento.ausgaben().intValue());
    }

    @Test
    void shouldHandleNullValuesInCluster() {
        // Arrange
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("TEST789");
        MonatlicheFinanzsituationCluster cluster = new MonatlicheFinanzsituationCluster(scoringId);
        // Don't add any financial data - memento should have nulls

        // Act
        repository.speichern(cluster);
        MonatlicheFinanzsituationCluster loaded = repository.lade(scoringId);

        // Assert
        assertNotNull(loaded);
        assertEquals(scoringId, loaded.scoringId());
        
        var memento = loaded.memento();
        assertNotNull(memento);
        // These should be null since no data was added
        assertNull(memento.einnahmen());
        assertNull(memento.ausgaben());
        assertNull(memento.neueDarlehensBelastungen());
    }
}
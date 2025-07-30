package com.bigpugloans.scoring.adapter.driven.antragstellerCluster;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.testutils.RepositoryTestBase;
import com.bigpugloans.scoring.testutils.TestAntragstellerClusterSpringDataRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AntragstellerClusterRepositoryUnitTest extends RepositoryTestBase {

    @Test
    void shouldSaveAndLoadCluster() {
        // Arrange
        TestAntragstellerClusterSpringDataRepository springDataRepo = 
            new TestAntragstellerClusterSpringDataRepository(connection);
        AntragstellerClusterJDBCRespository repository = 
            new AntragstellerClusterJDBCRespository(springDataRepo);

        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("TEST123");
        AntragstellerCluster cluster = new AntragstellerCluster(scoringId);
        cluster.wohnortHinzufuegen("Berlin");
        cluster.guthabenHinzufuegen(new Waehrungsbetrag(5000));

        // Act
        repository.speichern(cluster);
        AntragstellerCluster loaded = repository.lade(scoringId);

        // Assert
        assertNotNull(loaded);
        assertEquals(scoringId, loaded.scoringId());
        
        var memento = loaded.memento();
        assertNotNull(memento);
        assertEquals("Berlin", memento.wohnort());
        assertEquals(5000, memento.guthaben().intValue());
    }

    @Test
    void shouldReturnNullWhenClusterNotFound() {
        // Arrange
        TestAntragstellerClusterSpringDataRepository springDataRepo = 
            new TestAntragstellerClusterSpringDataRepository(connection);
        AntragstellerClusterJDBCRespository repository = 
            new AntragstellerClusterJDBCRespository(springDataRepo);

        ScoringId nonExistentId = ScoringId.preScoringIdAusAntragsnummer("NONEXISTENT");

        // Act
        AntragstellerCluster result = repository.lade(nonExistentId);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldHandleNullValuesInCluster() {
        // Arrange
        TestAntragstellerClusterSpringDataRepository springDataRepo = 
            new TestAntragstellerClusterSpringDataRepository(connection);
        AntragstellerClusterJDBCRespository repository = 
            new AntragstellerClusterJDBCRespository(springDataRepo);

        ScoringId scoringId = ScoringId.preScoringIdAusAntragsnummer("TEST789");
        AntragstellerCluster cluster = new AntragstellerCluster(scoringId);
        // Don't add any data

        // Act
        repository.speichern(cluster);
        AntragstellerCluster loaded = repository.lade(scoringId);

        // Assert
        assertNotNull(loaded);
        assertEquals(scoringId, loaded.scoringId());
        
        var memento = loaded.memento();
        assertNotNull(memento);
        // These should be null since no data was added
        assertNull(memento.wohnort());
        assertEquals(0, memento.guthaben().intValue()); // Default guthaben is 0, not null
    }
}
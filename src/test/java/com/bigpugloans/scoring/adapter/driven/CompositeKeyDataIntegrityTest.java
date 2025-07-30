package com.bigpugloans.scoring.adapter.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerCluster;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testutils.RepositoryTestBase;
import com.bigpugloans.scoring.testutils.TestAntragstellerClusterSpringDataRepository;
import com.bigpugloans.scoring.testutils.TestMonatlicheFinanzsituationClusterSpringDataRepository;
import com.bigpugloans.scoring.adapter.driven.antragstellerCluster.AntragstellerClusterJDBCRespository;
import com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterJDBCRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that verify the composite key (antragsnummer + scoring_art) allows
 * PRE and MAIN scorings to coexist without overwriting each other.
 */
class CompositeKeyDataIntegrityTest extends RepositoryTestBase {

    @Test
    void shouldStoreBothPreAndMainScoringForSameAntragsnummer() {
        // Arrange
        String antragsnummer = "12345";
        ScoringId preScoringId = ScoringId.preScoringIdAusAntragsnummer(antragsnummer);
        ScoringId mainScoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        // Create PRE scoring cluster
        MonatlicheFinanzsituationCluster preCluster = new MonatlicheFinanzsituationCluster(preScoringId);
        preCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2000));
        preCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(800));

        // Create MAIN scoring cluster
        MonatlicheFinanzsituationCluster mainCluster = new MonatlicheFinanzsituationCluster(mainScoringId);
        mainCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(3000));
        mainCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(1200));

        // Act - Save both scorings
        repository.speichern(preCluster);
        repository.speichern(mainCluster);

        // Assert - Both should be retrievable independently
        MonatlicheFinanzsituationCluster loadedPreCluster = repository.lade(preScoringId);
        MonatlicheFinanzsituationCluster loadedMainCluster = repository.lade(mainScoringId);

        assertNotNull(loadedPreCluster);
        assertNotNull(loadedMainCluster);
        
        // Verify PRE cluster data
        assertEquals(preScoringId, loadedPreCluster.scoringId());
        assertEquals(2000, loadedPreCluster.memento().einnahmen().intValue());
        assertEquals(800, loadedPreCluster.memento().ausgaben().intValue());
        
        // Verify MAIN cluster data
        assertEquals(mainScoringId, loadedMainCluster.scoringId());
        assertEquals(3000, loadedMainCluster.memento().einnahmen().intValue());
        assertEquals(1200, loadedMainCluster.memento().ausgaben().intValue());
    }

    @Test
    void shouldAllowUpdatingPreScoringWithoutAffectingMainScoring() {
        // Arrange
        String antragsnummer = "67890";
        ScoringId preScoringId = ScoringId.preScoringIdAusAntragsnummer(antragsnummer);
        ScoringId mainScoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        
        TestAntragstellerClusterSpringDataRepository springDataRepo = 
            new TestAntragstellerClusterSpringDataRepository(connection);
        AntragstellerClusterJDBCRespository repository = 
            new AntragstellerClusterJDBCRespository(springDataRepo);

        // Create and save PRE scoring
        AntragstellerCluster preCluster = new AntragstellerCluster(preScoringId);
        preCluster.wohnortHinzufuegen("Munich");
        preCluster.guthabenHinzufuegen(new Waehrungsbetrag(1000));
        repository.speichern(preCluster);

        // Create and save MAIN scoring
        AntragstellerCluster mainCluster = new AntragstellerCluster(mainScoringId);
        mainCluster.wohnortHinzufuegen("Berlin");
        mainCluster.guthabenHinzufuegen(new Waehrungsbetrag(5000));
        repository.speichern(mainCluster);

        // Act - Update PRE scoring
        AntragstellerCluster updatedPreCluster = new AntragstellerCluster(preScoringId);
        updatedPreCluster.wohnortHinzufuegen("Hamburg");
        updatedPreCluster.guthabenHinzufuegen(new Waehrungsbetrag(2000));
        repository.speichern(updatedPreCluster);

        // Assert - PRE should be updated, MAIN should be unchanged
        AntragstellerCluster loadedPreCluster = repository.lade(preScoringId);
        AntragstellerCluster loadedMainCluster = repository.lade(mainScoringId);

        assertNotNull(loadedPreCluster);
        assertNotNull(loadedMainCluster);
        
        // Verify PRE cluster was updated
        assertEquals("Hamburg", loadedPreCluster.memento().wohnort());
        assertEquals(2000, loadedPreCluster.memento().guthaben().intValue());
        
        // Verify MAIN cluster unchanged
        assertEquals("Berlin", loadedMainCluster.memento().wohnort());
        assertEquals(5000, loadedMainCluster.memento().guthaben().intValue());
    }

    @Test
    void shouldReturnNullWhenQueryingNonExistentScoringType() {
        // Arrange
        String antragsnummer = "99999";
        ScoringId preScoringId = ScoringId.preScoringIdAusAntragsnummer(antragsnummer);
        ScoringId mainScoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        // Save only PRE scoring
        MonatlicheFinanzsituationCluster preCluster = new MonatlicheFinanzsituationCluster(preScoringId);
        preCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(1500));
        repository.speichern(preCluster);

        // Act & Assert
        MonatlicheFinanzsituationCluster loadedPreCluster = repository.lade(preScoringId);
        MonatlicheFinanzsituationCluster loadedMainCluster = repository.lade(mainScoringId);

        assertNotNull(loadedPreCluster);
        assertNull(loadedMainCluster);  // MAIN scoring doesn't exist
        
        assertEquals(1500, loadedPreCluster.memento().einnahmen().intValue());
    }
}
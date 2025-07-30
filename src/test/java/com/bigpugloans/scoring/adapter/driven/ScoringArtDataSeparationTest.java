package com.bigpugloans.scoring.adapter.driven;

import com.bigpugloans.scoring.domain.model.ScoringId;
import com.bigpugloans.scoring.domain.model.Waehrungsbetrag;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationCluster;
import com.bigpugloans.scoring.testutils.RepositoryTestBase;
import com.bigpugloans.scoring.testutils.TestMonatlicheFinanzsituationClusterSpringDataRepository;
import com.bigpugloans.scoring.adapter.driven.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterJDBCRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Critical test to verify that PRE and MAIN scoring data are properly separated
 * and don't overwrite each other. This test would fail with the old schema.
 */
class ScoringArtDataSeparationTest extends RepositoryTestBase {

    @Test
    void demonstrateDataIntegrityFixForPreAndMainScoring() {
        // Arrange
        String antragsnummer = "CRITICAL_TEST_12345";
        ScoringId preScoringId = ScoringId.preScoringIdAusAntragsnummer(antragsnummer);
        ScoringId mainScoringId = ScoringId.mainScoringIdAusAntragsnummer(antragsnummer);
        
        TestMonatlicheFinanzsituationClusterSpringDataRepository springDataRepo = 
            new TestMonatlicheFinanzsituationClusterSpringDataRepository(connection);
        MonatlicheFinanzsituationClusterJDBCRepository repository = 
            new MonatlicheFinanzsituationClusterJDBCRepository(springDataRepo);

        // Step 1: Save PRE scoring with specific data
        MonatlicheFinanzsituationCluster preCluster = new MonatlicheFinanzsituationCluster(preScoringId);
        preCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(1000));
        preCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(500));
        repository.speichern(preCluster);
        
        // Verify PRE data was saved
        MonatlicheFinanzsituationCluster savedPre = repository.lade(preScoringId);
        assertNotNull(savedPre, "PRE scoring should be saved");
        assertEquals(1000, savedPre.memento().einnahmen().intValue());
        assertEquals(500, savedPre.memento().ausgaben().intValue());

        // Step 2: Save MAIN scoring with DIFFERENT data
        // With the old schema, this would OVERWRITE the PRE data!
        MonatlicheFinanzsituationCluster mainCluster = new MonatlicheFinanzsituationCluster(mainScoringId);
        mainCluster.monatlicheEinnahmenHinzufuegen(new Waehrungsbetrag(2000));
        mainCluster.monatlicheAusgabenHinzufuegen(new Waehrungsbetrag(800));
        repository.speichern(mainCluster);

        // Step 3: CRITICAL VERIFICATION - PRE data should NOT be overwritten
        MonatlicheFinanzsituationCluster preAfterMain = repository.lade(preScoringId);
        MonatlicheFinanzsituationCluster mainAfterSave = repository.lade(mainScoringId);

        // Both should exist with their original data
        assertNotNull(preAfterMain, "PRE scoring should still exist after MAIN save");
        assertNotNull(mainAfterSave, "MAIN scoring should exist");
        
        // PRE data should be unchanged
        assertEquals(1000, preAfterMain.memento().einnahmen().intValue(), 
                    "PRE einnahmen should NOT be overwritten by MAIN save");
        assertEquals(500, preAfterMain.memento().ausgaben().intValue(), 
                    "PRE ausgaben should NOT be overwritten by MAIN save");
        
        // MAIN data should be as saved
        assertEquals(2000, mainAfterSave.memento().einnahmen().intValue(),
                    "MAIN einnahmen should be correctly saved");
        assertEquals(800, mainAfterSave.memento().ausgaben().intValue(),
                    "MAIN ausgaben should be correctly saved");
        
        // Verify they are actually different entities
        assertNotEquals(preAfterMain.memento().einnahmen(), 
                       mainAfterSave.memento().einnahmen(),
                       "PRE and MAIN should have different einnahmen values");
    }
}
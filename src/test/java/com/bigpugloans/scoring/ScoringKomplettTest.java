package com.bigpugloans.scoring;

import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

@SpringBootTest
@Testcontainers
public class ScoringKomplettTest {
    @Autowired
    PreScoringStart preScoringStart;
    @Autowired
    VerarbeitungImmobilienBewertung verarbeitungImmobilienBewertung;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testeKomplettestPreScoring() {
       ScoringDatenAusAntrag antrag = antrag();
       preScoringStart.startePreScoring(antrag);
       verarbeitungImmobilienBewertung.verarbeiteImmobilienBewertung(new ImmobilienBewertung(antrag.antragsnummer(), 90000, 50000, 180000, 90000, 110000));
    }

    private ScoringDatenAusAntrag antrag() {
        String antragsnummer = "123";
        String kundennummer = "456";
        int ausgabenPrivateKrankenversicherung = 0;
        int ausgabenMonatlicheBelastungKredite = 100;
        int ausgabenLebenshaltungsKosten = 400;
        int ausgabenMiete = 800;
        boolean mieteEntfaelltKuenftig = false;
        int gehalt = 3500;
        int mietEinnahmenFinanzierungsobjekt = 0;
        int mietEinnahmenWeitereObjekte = 0;
        int weitereEinkuenfte = 300;
        int summeDarlehen1 = 300000;
        int monatlicheDarlehensbelastungen1 = 600;
        int monatlicheDarlehensbelastungen2 = 0;
        int summeDarlehen2 = 0;
        int monatlicheDarlehensbelastungen3 = 0;
        int summeDarlehen3 = 0;
        int monatlicheDarlehensbelastungen4 = 0;
        int summeDarlehen4 = 0;
        int kaufnebenkosten = 20000;
        int kaufpreisOderBaukosten = 330000;
        int kostenGrundstueck = 100000;

        int summeEigenmittel = 150000;
        String vorname = "Michael";
        String nachname = "Plöd";
        String strasse = "Musterstraße 1";
        String stadt = "Hamburg";
        String plz = "20257";
        Date geburtsdatum = new Date();
        return new ScoringDatenAusAntrag(
                antragsnummer,
                kundennummer,
                ausgabenPrivateKrankenversicherung,
                ausgabenMonatlicheBelastungKredite,
                ausgabenLebenshaltungsKosten,
                ausgabenMiete,
                mieteEntfaelltKuenftig,
                gehalt,
                mietEinnahmenFinanzierungsobjekt,
                mietEinnahmenWeitereObjekte,
                weitereEinkuenfte,
                monatlicheDarlehensbelastungen1,
                monatlicheDarlehensbelastungen2,
                monatlicheDarlehensbelastungen3,
                monatlicheDarlehensbelastungen4,
                kaufnebenkosten,
                kaufpreisOderBaukosten,
                kostenGrundstueck,
                summeDarlehen1,
                summeDarlehen2,
                summeDarlehen3,
                summeDarlehen4,
                summeEigenmittel,
                vorname,
                nachname,
                strasse,
                stadt,
                plz,
                geburtsdatum
        );
    }
}

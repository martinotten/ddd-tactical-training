package com.bigpugloans.scoring;

import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.events.ImmobilieBewertet;
import com.bigpugloans.events.antrag.*;
import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Testcontainers
public class ScoringKomplettTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.7");
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testeKomplettestPreScoring() {
       Antrag antrag = antrag();
       AntragEingereicht antragEingereicht = new AntragEingereicht();
       antragEingereicht.setAntrag(antrag);
       antragEingereicht.setAntragsnummer(antrag.getAntragsnummer());
       antragEingereicht.setTimestamp(new Date());
       applicationEventPublisher.publishEvent(antragEingereicht);

       ImmobilieBewertet immobilieBewertet = new ImmobilieBewertet();
       immobilieBewertet.setAntragsnummer(antrag.getAntragsnummer());
       immobilieBewertet.setBeleihungswert(320000);
       immobilieBewertet.setMinimalerMarktwert(280000);
       immobilieBewertet.setMaximalerMarktwert(550000);
       immobilieBewertet.setDurchschnittlicherMarktwertVon(300000);
       immobilieBewertet.setDurchschnittlicherMarktwertBis(350000);
       applicationEventPublisher.publishEvent(immobilieBewertet);
    }

    private Antrag antrag() {
        Antrag antrag = new Antrag();
        antrag.setAntragsnummer("123");

        Antragsteller antragsteller = new Antragsteller();
        antragsteller.setKundennummer("456");
        antragsteller.setArbeitgeber("Big Pug Bank");
        antragsteller.setBeschaeftigtSeit(new Date());
        antragsteller.setBerufsart(Berufsart.ANGESTELLT);
        antragsteller.setBranche(Branche.BANK);
        antragsteller.setFamilienstand(Familienstand.LEDIG);
        antragsteller.setGeburtstdatum(new Date());
        antragsteller.setNachname("Mustermann");
        antragsteller.setVorname("Max");
        antragsteller.setPostleitzahl("20257");
        antragsteller.setStrasse("Musterstraße 1");
        antragsteller.setOrt("Hamburg");
        antrag.setAntragsteller(antragsteller);

        Einkommen einkommen = new Einkommen();
        einkommen.setGehalt(4500);
        einkommen.setBonusVorhanden(false);
        einkommen.setMietEinnahmenFinanzierungsobjekt(0);
        einkommen.setWeitereEinkuenfte(200);
        einkommen.setMietEinnahmenWeitereObjekte(0);
        antrag.setEinkommen(einkommen);

        Ausgaben ausgaben = new Ausgaben();
        ausgaben.setLebenshaltungsKosten(500);
        ausgaben.setMiete(800);
        ausgaben.setMieteEntfaelltKuenftig(true);
        ausgaben.setPrivateKrankenversicherung(400);
        antrag.setAusgaben(ausgaben);

        Finanzierungsobjekt finanzierungsobjekt = new Finanzierungsobjekt();
        finanzierungsobjekt.setOrt("Hamburg");
        finanzierungsobjekt.setPostleitzahl("20257");
        finanzierungsobjekt.setStrasse("Musterstraße");
        finanzierungsobjekt.setHausnummer("2");
        finanzierungsobjekt.setBaujahr("2015");
        finanzierungsobjekt.setWohnflaecheInQuadratmeter(150);
        finanzierungsobjekt.setGrundstuecksflaecheInQuadratmeter(800);
        finanzierungsobjekt.setNutzart(Nutzart.SELBSTBEWOHNT);
        finanzierungsobjekt.setVerwendungFinanzierung(VerwendungFinanzierung.KAUF);
        finanzierungsobjekt.setBauweise(Bauweise.STEIN);
        finanzierungsobjekt.setObjektart(Objektart.WOHNUNG);
        finanzierungsobjekt.setAusstattungsQualitaet(AusstattungsQualitaet.STANDARD);
        finanzierungsobjekt.setKeller(Ausbauart.TEILWEISE_AUSGEBAUT);
        finanzierungsobjekt.setDachgeschoss(Ausbauart.VOLL_AUSGEBAUT);
        finanzierungsobjekt.setStellplaetze(Stellplaetze.CARPORT);
        Set<AusstattungsMerkmal> ausstattungsMerkmalSet = new HashSet<>();
        ausstattungsMerkmalSet.add(AusstattungsMerkmal.SOLARANLAGE);
        ausstattungsMerkmalSet.add(AusstattungsMerkmal.SATELLITEN_EMPFANG);
        finanzierungsobjekt.setAusstattungsMerkmale(ausstattungsMerkmalSet);
        finanzierungsobjekt.setAnzahlEtagen(2);
        finanzierungsobjekt.setLetzteModernisierung(new Date());
        finanzierungsobjekt.setLageWohnung(LageWohnung.OBERGESCHOSS);
        finanzierungsobjekt.setAnzahlWohnungenInGebaeude(2);
        finanzierungsobjekt.setBezeichnungWohnung("Wohnung 2");
        antrag.setFinanzierungsobjekt(finanzierungsobjekt);

        Kosten kosten = new Kosten();
        kosten.setKaufpreisOderBaukosten(300000);
        kosten.setKostenGrundstueck(100000);
        kosten.setNebenkosten(20000);
        antrag.setKosten(kosten);

        Finanzierung finanzierung = new Finanzierung();
        Darlehen darlehen1 = new Darlehen();
        darlehen1.setDarlehensHoehe(300000);
        darlehen1.setTilgung(2);
        darlehen1.setZinsBindung(10);
        darlehen1.setMonatlicheBelastung(800);
        darlehen1.setZinssatz(2.2);
        finanzierung.setDarlehen1(darlehen1);
        finanzierung.setFinanzierungBedarf(300000);
        antrag.setFinanzierung(finanzierung);

        Eigenmittel eigenmittel = new Eigenmittel();
        eigenmittel.setBarvermoegen(120000);
        eigenmittel.setSumme(120000);
        antrag.setEigenmittel(eigenmittel);
        return antrag;
    }
}

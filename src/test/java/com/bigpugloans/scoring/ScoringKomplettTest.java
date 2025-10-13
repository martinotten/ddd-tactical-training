package com.bigpugloans.scoring;

import com.bigpugloans.events.AntragEingereicht;
import com.bigpugloans.events.ImmobilieBewertet;
import com.bigpugloans.events.antrag.*;
import com.bigpugloans.scoring.domain.model.Antrag;
import com.bigpugloans.scoring.adapter.driven.backends.KonditionsAbfrageAdapter;
import com.bigpugloans.scoring.adapter.driven.backends.KonditionsAbfrageServiceAdapter;
import com.bigpugloans.scoring.adapter.driven.backends.LeseKontoSaldoAdapter;
import com.bigpugloans.scoring.adapter.driven.messaging.ScoringErgebnisVeroeffentlichenAdapter;
import com.bigpugloans.scoring.adapter.driving.AntragEingereichtMessageListener;
import com.bigpugloans.scoring.adapter.driving.ImmobilieBewertetMessageListener;
import com.bigpugloans.scoring.application.model.ScoringDatenAusAntrag;
import com.bigpugloans.scoring.application.service.EingereicherAntragVerarbeitenApplicationService;
import com.bigpugloans.scoring.application.service.ScoringAusfuehrenUndVeroeffentlichenApplicationService;
import com.bigpugloans.scoring.application.service.VerarbeitungImmobilienBewertungApplicationService;
import com.bigpugloans.scoring.domain.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.PreScoringStart;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import com.bigpugloans.scoring.domain.model.antragstellerCluster.AntragstellerClusterRepository;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisCluster;
import com.bigpugloans.scoring.domain.model.auskunfteiErgebnisCluster.AuskunfteiErgebnisClusterRepository;
import com.bigpugloans.scoring.domain.model.immobilienFinanzierungsCluster.ImmobilienFinanzierungClusterRepository;
import com.bigpugloans.scoring.domain.model.monatlicheFinanzsituationCluster.MonatlicheFinanzsituationClusterRepository;
import com.bigpugloans.scoring.domain.model.scoringErgebnis.ScoringErgebnisRepository;
import com.bigpugloans.scoring.domain.service.*;
import com.bigpugloans.scoring.testinfrastructure.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoringKomplettTest {

    private AntragstellerClusterRepository antragstellerClusterRepository = new InMemoryAntragstellerClusterRepository();
    private MonatlicheFinanzsituationClusterRepository monatlicheFinanzsituationClusterRepository = new InMemoryMonatlicheFinanzsituationClusterRepository();

    private ImmobilienFinanzierungClusterRepository immobilienFinanzierungClusterRepository = new InMemoryImmobilienFinanzierungClusterRepository();
    private AuskunfteiErgebnisClusterRepository auskunfteiErgebnisClusterRepository = new InMemoryAuskunfteiErgebnisClusterRepository();
    private ScoringErgebnisRepository scoringErgebnisRepository = new InMemoryScoringErgebnisRepository();

    @Test
    void testeKomplettestPreScoring() {
        ApplicationEventPublisher applicationEventPublisher = new ApplicationEventPublisher() {
            @Override
            public void publishEvent(@NotNull Object event) {
                if (event instanceof AntragEingereicht) {
                    new AntragEingereichtMessageListener(new EingereicherAntragVerarbeitenApplicationService(
                            new AntragHinzufuegenDomainService(antragstellerClusterRepository, monatlicheFinanzsituationClusterRepository, immobilienFinanzierungClusterRepository, auskunfteiErgebnisClusterRepository),
                            new AuskunfteiHinzufuegenDomainService(auskunfteiErgebnisClusterRepository),
                            new KonditionsAbfrageServiceAdapter(new KonditionsAbfrageAdapter()),
                            new LeseKontoSaldoAdapter(),
                            new KontosaldoHinzufuegenDomainService(new InMemoryAntragstellerClusterRepository()),
                            new ScoringAusfuehrenUndVeroeffentlichenApplicationService(new ScoringDomainService(antragstellerClusterRepository, monatlicheFinanzsituationClusterRepository, immobilienFinanzierungClusterRepository, auskunfteiErgebnisClusterRepository), scoringErgebnisRepository, new TestScoringErgebnisVeroeffentlichen())
                    )).onAntragEingereicht((AntragEingereicht) event);
                } else if (event instanceof ImmobilieBewertet) {
                    new ImmobilieBewertetMessageListener(
                            new VerarbeitungImmobilienBewertungApplicationService(
                                    new ImmobilienBewertungHinzufuegenDomainService(immobilienFinanzierungClusterRepository),
                                    new ScoringAusfuehrenUndVeroeffentlichenApplicationService(
                                            new ScoringDomainService(
                                                    antragstellerClusterRepository,
                                                    monatlicheFinanzsituationClusterRepository,
                                                    immobilienFinanzierungClusterRepository,
                                                    auskunfteiErgebnisClusterRepository),
                                            scoringErgebnisRepository,
                                            new ScoringErgebnisVeroeffentlichenAdapter(Assertions::assertNotNull))))
                            .onImmobilieBewertet((ImmobilieBewertet) event);

                }
            }
        };

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

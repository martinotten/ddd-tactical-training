package com.bigpugloans.antragserfassung;

import com.bigpugloans.antragserfassung.domain.model.*;
import com.bigpugloans.antragserfassung.query.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AntragserfassungIntegrationTest {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    private FixtureConfiguration<Antragserfassung> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Antragserfassung.class);
    }

    @Test
    void sollteKompleteAntragserfassungDurchlaufenKoennen() throws Exception {
        // Given
        UUID antragsnummer = UUID.randomUUID();
        String benutzerId = "testBenutzer";

        // When: Antrag starten
        StarteAntragCommand starteCommand = new StarteAntragCommand(antragsnummer, benutzerId);
        commandGateway.sendAndWait(starteCommand);

        // Then: Antrag sollte in Übersicht erscheinen
        Thread.sleep(100); // Kurz warten für Event-Verarbeitung
        CompletableFuture<List<AntragserfassungUebersichtView>> uebersichtFuture = 
            queryGateway.query(new FindeAlleAntragserfassungenQuery(null), 
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(AntragserfassungUebersichtView.class));
        List<AntragserfassungUebersichtView> antraege = uebersichtFuture.get(5, TimeUnit.SECONDS);
        
        AntragserfassungUebersichtView gestarteterAntrag = antraege.stream()
            .filter(a -> a.antragsnummer().equals(antragsnummer))
            .findFirst()
            .orElse(null);
        
        assertThat(gestarteterAntrag).isNotNull();
        assertThat(gestarteterAntrag.status()).isEqualTo(AntragserfassungsStatus.GESTARTET);
        assertThat(gestarteterAntrag.benutzerId()).isEqualTo(benutzerId);
        assertThat(gestarteterAntrag.fortschrittsprozenteBerechnen()).isEqualTo(0.0);

        // When: Antragsteller erfassen
        Anschrift anschrift = new Anschrift("Musterstraße", "123", "12345", "Musterstadt", "Deutschland");
        AntragstellerErfassenCommand antragstellerCommand = new AntragstellerErfassenCommand(
            antragsnummer,
            "Max",
            "Mustermann", 
            LocalDate.of(1990, 1, 1),
            "0123456789",
            "max@example.com",
            anschrift,
            Familienstand.LEDIG,
            0
        );
        commandGateway.sendAndWait(antragstellerCommand);

        // Then: Fortschritt sollte sich aktualisiert haben
        Thread.sleep(100);
        CompletableFuture<AntragsdetailView> detailFuture = 
            queryGateway.query(new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class));
        AntragsdetailView antragsdetail = detailFuture.get(5, TimeUnit.SECONDS);
        
        assertThat(antragsdetail).isNotNull();
        assertThat(antragsdetail.antragstellerDetails()).isNotNull();
        assertThat(antragsdetail.antragstellerDetails().vorname()).isEqualTo("Max");
        assertThat(antragsdetail.antragstellerDetails().nachname()).isEqualTo("Mustermann");

        // When: Finanzierungsobjekt erfassen
        Anschrift objektAdresse = new Anschrift("Objektstraße", "456", "54321", "Objektstadt", "Deutschland");
        FinanzierungsobjektErfassenCommand objektCommand = new FinanzierungsobjektErfassenCommand(
            antragsnummer,
            Objektart.EIGENTUMSWOHNUNG,
            objektAdresse,
            new BigDecimal("300000"),
            new BigDecimal("15000"),
            2010,
            85.5,
            3,
            Nutzungsart.EIGENNUTZUNG
        );
        commandGateway.sendAndWait(objektCommand);

        // When: Ausgaben erfassen
        AusgabenErfassenCommand ausgabenCommand = new AusgabenErfassenCommand(
            antragsnummer,
            new BigDecimal("2000"),
            new BigDecimal("800"),
            new BigDecimal("400"),
            new BigDecimal("100"),
            new BigDecimal("500"),
            new BigDecimal("200")
        );
        commandGateway.sendAndWait(ausgabenCommand);

        // When: Einkommen erfassen
        EinkommenErfassenCommand einkommenCommand = new EinkommenErfassenCommand(
            antragsnummer,
            new BigDecimal("4500"),
            new BigDecimal("3000"),
            new BigDecimal("2500"),
            new BigDecimal("0"),
            new BigDecimal("100"),
            new BigDecimal("0"),
            Beschaeftigungsverhaeltnis.ANGESTELLT
        );
        commandGateway.sendAndWait(einkommenCommand);

        // Then: Alle Daten sollten erfasst sein
        Thread.sleep(100);
        antragsdetail = queryGateway.query(new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class))
            .get(5, TimeUnit.SECONDS);
        
        assertThat(antragsdetail.finanzierungsobjektDetails()).isNotNull();
        assertThat(antragsdetail.ausgabenDetails()).isNotNull();
        assertThat(antragsdetail.einkommenDetails()).isNotNull();
        
        // Berechnungen testen
        assertThat(antragsdetail.ausgabenDetails().gesamtausgabenBerechnen())
            .isEqualTo(new BigDecimal("4000"));
        assertThat(antragsdetail.einkommenDetails().gesamteinkommenBerechnen())
            .isGreaterThan(new BigDecimal("4500"));

        // When: Antragserfassung abschließen
        AntragserfassungAbschliessenCommand abschlussCommand = new AntragserfassungAbschliessenCommand(
            antragsnummer,
            "Antrag vollständig erfasst und geprüft"
        );
        commandGateway.sendAndWait(abschlussCommand);

        // Then: Antrag sollte abgeschlossen sein
        Thread.sleep(100);
        antragsdetail = queryGateway.query(new FindeAntragsdetailQuery(antragsnummer), 
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(AntragsdetailView.class))
            .get(5, TimeUnit.SECONDS);
        
        assertThat(antragsdetail.status()).isEqualTo(AntragserfassungsStatus.ABGESCHLOSSEN);
        assertThat(antragsdetail.benutzerKommentar()).isEqualTo("Antrag vollständig erfasst und geprüft");
        assertThat(antragsdetail.abgeschlossenAm()).isNotNull();
    }

    @Test
    void sollteValidierungsfehlerBeiUnvollstaendigemAbschlussFangen() {
        // Given
        UUID antragsnummer = UUID.randomUUID();
        
        fixture.given()
            .when(new StarteAntragCommand(antragsnummer, "testBenutzer"))
            .expectEvents(new AntragGestartetEvent(antragsnummer, "testBenutzer", null));

        // When/Then: Abschließen ohne alle Schritte sollte fehlschlagen
        fixture.given(new AntragGestartetEvent(antragsnummer, "testBenutzer", null))
            .when(new AntragserfassungAbschliessenCommand(antragsnummer, "Zu früh"))
            .expectException(IllegalStateException.class);
    }

    @Test
    void sollteReihenfolgeValidierungEinhalten() {
        // Given
        UUID antragsnummer = UUID.randomUUID();
        
        // When/Then: Finanzierungsobjekt ohne Antragsteller sollte fehlschlagen
        fixture.given(new AntragGestartetEvent(antragsnummer, "testBenutzer", null))
            .when(new FinanzierungsobjektErfassenCommand(
                antragsnummer, 
                Objektart.EIGENTUMSWOHNUNG, 
                new Anschrift("Test", "1", "12345", "Test", "DE"),
                new BigDecimal("300000"), 
                null, null, null, null, null))
            .expectException(IllegalStateException.class);
    }

    @Test
    void sollteBearbeitungNachAbschlussVerhindern() {
        // Given
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        // When: Vollständiger Ablauf
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", null),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Mustermann", LocalDate.now(), null, null, anschrift, null, null, null),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, null),
            new AusgabenErfasstEvent(antragsnummer, new BigDecimal("2000"), null, null, null, null, null, null),
            new EinkommenErfasstEvent(antragsnummer, new BigDecimal("4000"), null, null, null, null, null, null, null),
            new AntragserfassungAbgeschlossenEvent(antragsnummer, "Test", null)
        )
        // Then: Weitere Bearbeitung sollte fehlschlagen
        .when(new AntragstellerErfassenCommand(antragsnummer, "Neuer", "Name", LocalDate.now(), null, null, anschrift, null, null))
        .expectException(IllegalStateException.class);
    }
}
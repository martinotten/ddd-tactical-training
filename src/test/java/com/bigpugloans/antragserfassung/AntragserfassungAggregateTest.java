package com.bigpugloans.antragserfassung;

import com.bigpugloans.antragserfassung.domain.model.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.*;
import static org.hamcrest.Matchers.*;

class AntragserfassungAggregateTest {

    private FixtureConfiguration<Antragserfassung> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Antragserfassung.class);
    }

    @Test
    void sollteAntragStartenKoennen() {
        String benutzerId = "testBenutzer";

        // Da UUID im Aggregate generiert wird, können wir nur prüfen dass Command erfolgreich verarbeitet wird
        fixture.givenNoPriorActivity()
            .when(new StarteAntragCommand(benutzerId))
            .expectSuccessfulHandlerExecution();
    }

    @Test
    void sollteAntragstellerNachStartErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Musterstraße", "123", "12345", "Musterstadt", "Deutschland");
        
        fixture.given(new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()))
            .when(new AntragstellerErfassenCommand(
                antragsnummer,
                "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                "0123456789",
                "max@example.com",
                anschrift,
                Familienstand.VERHEIRATET,
                2,
                "KUNDE123",
                Branche.INDUSTRIE,
                Berufsart.ANGESTELLT,
                "Test GmbH",
                LocalDate.of(2020, 1, 1)
            ))
            .expectEventsMatching(payloadsMatching(exactSequenceOf(
                matches(event -> event instanceof AntragstellerErfasstEvent e &&
                    e.antragsnummer().equals(antragsnummer) &&
                    e.vorname().equals("Max") &&
                    e.nachname().equals("Mustermann") &&
                    e.geburtsdatum().equals(LocalDate.of(1990, 1, 1)) &&
                    e.telefonnummer().equals("0123456789") &&
                    e.emailAdresse().equals("max@example.com") &&
                    e.anschrift().equals(anschrift) &&
                    e.familienstand().equals(Familienstand.VERHEIRATET) &&
                    e.anzahlKinder() == 2 &&
                    e.kundennummer().equals("KUNDE123") &&
                    e.branche().equals(Branche.INDUSTRIE) &&
                    e.berufsart().equals(Berufsart.ANGESTELLT) &&
                    e.arbeitgeber().equals("Test GmbH") &&
                    e.beschaeftigtSeit().equals(LocalDate.of(2020, 1, 1)) &&
                    e.zeitpunkt() != null
                )
            )));
    }

    @Test
    void sollteFinanzierungsobjektNachAntragstellerErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Objektstraße", "456", "54321", "Objektstadt", "Deutschland");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Mustermann", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now())
        )
        .when(new FinanzierungsobjektErfassenCommand(
            antragsnummer,
            Objektart.EINFAMILIENHAUS,
            anschrift,
            new BigDecimal("450000"),
            new BigDecimal("25000"),
            2015,
            120.5,
            4,
            Nutzungsart.EIGENNUTZUNG
        ))
        .expectEventsMatching(payloadsMatching(exactSequenceOf(
            matches(event -> event instanceof FinanzierungsobjektErfasstEvent e &&
                e.antragsnummer().equals(antragsnummer) &&
                e.objektart().equals(Objektart.EINFAMILIENHAUS) &&
                e.objektAdresse().equals(anschrift) &&
                e.kaufpreis().equals(new BigDecimal("450000")) &&
                e.nebenkosten().equals(new BigDecimal("25000")) &&
                e.baujahr().equals(2015) &&
                e.wohnflaeche().equals(120.5) &&
                e.anzahlZimmer().equals(4) &&
                e.nutzungsart().equals(Nutzungsart.EIGENNUTZUNG) &&
                e.zeitpunkt() != null
            )
        )));
    }

    @Test
    void sollteAusgabenNachFinanzierungsobjektErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now()),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, Instant.now())
        )
        .when(new AusgabenErfassenCommand(
            antragsnummer,
            new BigDecimal("1800"),
            new BigDecimal("1200"),
            new BigDecimal("350"),
            new BigDecimal("120"),
            new BigDecimal("600"),
            new BigDecimal("180")
        ))
        .expectEventsMatching(payloadsMatching(exactSequenceOf(
            matches(event -> event instanceof AusgabenErfasstEvent e &&
                e.antragsnummer().equals(antragsnummer) &&
                e.lebenshaltungskosten().equals(new BigDecimal("1800")) &&
                e.miete().equals(new BigDecimal("1200")) &&
                e.privateKrankenversicherung().equals(new BigDecimal("350")) &&
                e.sonstigeVersicherungen().equals(new BigDecimal("120")) &&
                e.kreditraten().equals(new BigDecimal("600")) &&
                e.sonstigeAusgaben().equals(new BigDecimal("180")) &&
                e.zeitpunkt() != null
            )
        )));
    }

    @Test
    void sollteEinkommenNachAusgabenErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now()),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, Instant.now()),
            new AusgabenErfasstEvent(antragsnummer, new BigDecimal("2000"), null, null, null, null, null, Instant.now())
        )
        .when(new EinkommenErfassenCommand(
            antragsnummer,
            new BigDecimal("5200"),
            new BigDecimal("4000"),
            new BigDecimal("3500"),
            new BigDecimal("800"),
            new BigDecimal("150"),
            new BigDecimal("300"),
            Beschaeftigungsverhaeltnis.ANGESTELLT
        ))
        .expectEventsMatching(payloadsMatching(exactSequenceOf(
            matches(event -> event instanceof EinkommenErfasstEvent e &&
                e.antragsnummer().equals(antragsnummer) &&
                e.nettoEinkommen().equals(new BigDecimal("5200")) &&
                e.urlaubsgeld().equals(new BigDecimal("4000")) &&
                e.weihnachtsgeld().equals(new BigDecimal("3500")) &&
                e.mieteinnahmen().equals(new BigDecimal("800")) &&
                e.kapitalertraege().equals(new BigDecimal("150")) &&
                e.sonstigeEinkommen().equals(new BigDecimal("300")) &&
                e.beschaeftigungsverhaeltnis().equals(Beschaeftigungsverhaeltnis.ANGESTELLT) &&
                e.zeitpunkt() != null
            )
        )));
    }

    @Test
    void sollteAntragserfassungNachAllenSchrittenAbschliessenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now()),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, Instant.now()),
            new AusgabenErfasstEvent(antragsnummer, new BigDecimal("2000"), null, null, null, null, null, Instant.now()),
            new EinkommenErfasstEvent(antragsnummer, new BigDecimal("4000"), null, null, null, null, null, null, Instant.now())
        )
        .when(new AntragserfassungAbschliessenCommand(antragsnummer, "Vollständig erfasst und geprüft"))
        .expectEventsMatching(payloadsMatching(exactSequenceOf(
            matches(event -> event instanceof AntragserfassungAbgeschlossenEvent e &&
                e.antragsnummer().equals(antragsnummer) &&
                e.benutzerKommentar().equals("Vollständig erfasst und geprüft") &&
                e.zeitpunkt() != null
            )
        )));
    }

    @Test
    void sollteValidierungsfehlerBeiLeeremBenutzerkennungWerfen() {
        UUID antragsnummer = UUID.randomUUID();
        
        fixture.givenNoPriorActivity()
            .when(new StarteAntragCommand(""))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    void sollteValidierungsfehlerBeiLeeremVornamenWerfen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()))
            .when(new AntragstellerErfassenCommand(
                antragsnummer,
                "",  // Leerer Vorname
                "Mustermann",
                LocalDate.now(),
                null, null, anschrift, null, null,
                null, null, null, null, null
            ))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    void sollteValidierungsfehlerBeiNegativemKaufpreisWerfen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now())
        )
        .when(new FinanzierungsobjektErfassenCommand(
            antragsnummer,
            Objektart.EIGENTUMSWOHNUNG,
            anschrift,
            new BigDecimal("-100000"),  // Negativer Kaufpreis
            null, null, null, null, null
        ))
        .expectException(IllegalArgumentException.class);
    }

    @Test
    void sollteReihenfolgeValidierungEinhalten() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        // Finanzierungsobjekt ohne Antragsteller sollte fehlschlagen
        fixture.given(new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()))
            .when(new FinanzierungsobjektErfassenCommand(
                antragsnummer,
                Objektart.EIGENTUMSWOHNUNG,
                anschrift,
                new BigDecimal("300000"),
                null, null, null, null, null
            ))
            .expectException(IllegalStateException.class);
    }

    @Test
    void sollteBearbeitungNachAbschlussVerhindern() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, null, null, null, null, null, Instant.now()),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, Instant.now()),
            new AusgabenErfasstEvent(antragsnummer, new BigDecimal("2000"), null, null, null, null, null, Instant.now()),
            new EinkommenErfasstEvent(antragsnummer, new BigDecimal("4000"), null, null, null, null, null, null, Instant.now()),
            new AntragserfassungAbgeschlossenEvent(antragsnummer, "Abgeschlossen", Instant.now())
        )
        .when(new AntragstellerErfassenCommand(
            antragsnummer,
            "Neuer",
            "Name",
            LocalDate.now(),
            null, null, anschrift, null, null,
            null, null, null, null, null
        ))
        .expectException(IllegalStateException.class);
    }
}
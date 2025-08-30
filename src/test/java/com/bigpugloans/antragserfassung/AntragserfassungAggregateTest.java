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

import static org.axonframework.test.matchers.Matchers.andNoMore;
import static org.axonframework.test.matchers.Matchers.exactSequenceOf;

class AntragserfassungAggregateTest {

    private FixtureConfiguration<Antragserfassung> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Antragserfassung.class);
    }

    @Test
    void sollteAntragStartenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        String benutzerId = "testBenutzer";

        fixture.givenNoPriorActivity()
            .when(new StarteAntragCommand(antragsnummer, benutzerId))
            .expectEvents(new AntragGestartetEvent(antragsnummer, benutzerId, null))
;
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
                2
            ))
            .expectEvents(new AntragstellerErfasstEvent(
                antragsnummer,
                "Max",
                "Mustermann", 
                LocalDate.of(1990, 1, 1),
                "0123456789",
                "max@example.com",
                anschrift,
                Familienstand.VERHEIRATET,
                2,
                null  // Zeitpunkt wird automatisch gesetzt
            ));
    }

    @Test
    void sollteFinanzierungsobjektNachAntragstellerErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Objektstraße", "456", "54321", "Objektstadt", "Deutschland");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Mustermann", LocalDate.now(), null, null, anschrift, null, null, Instant.now())
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
        .expectEvents(new FinanzierungsobjektErfasstEvent(
            antragsnummer,
            Objektart.EINFAMILIENHAUS,
            anschrift,
            new BigDecimal("450000"),
            new BigDecimal("25000"),
            2015,
            120.5,
            4,
            Nutzungsart.EIGENNUTZUNG,
            null
        ));
    }

    @Test
    void sollteAusgabenNachFinanzierungsobjektErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, Instant.now()),
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
        .expectEvents(new AusgabenErfasstEvent(
            antragsnummer,
            new BigDecimal("1800"),
            new BigDecimal("1200"),
            new BigDecimal("350"),
            new BigDecimal("120"),
            new BigDecimal("600"),
            new BigDecimal("180"),
            null
        ));
    }

    @Test
    void sollteEinkommenNachAusgabenErfassenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, Instant.now()),
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
        .expectEvents(new EinkommenErfasstEvent(
            antragsnummer,
            new BigDecimal("5200"),
            new BigDecimal("4000"),
            new BigDecimal("3500"),
            new BigDecimal("800"),
            new BigDecimal("150"),
            new BigDecimal("300"),
            Beschaeftigungsverhaeltnis.ANGESTELLT,
            null
        ));
    }

    @Test
    void sollteAntragserfassungNachAllenSchrittenAbschliessenKoennen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, Instant.now()),
            new FinanzierungsobjektErfasstEvent(antragsnummer, Objektart.EIGENTUMSWOHNUNG, anschrift, new BigDecimal("300000"), null, null, null, null, null, Instant.now()),
            new AusgabenErfasstEvent(antragsnummer, new BigDecimal("2000"), null, null, null, null, null, Instant.now()),
            new EinkommenErfasstEvent(antragsnummer, new BigDecimal("4000"), null, null, null, null, null, null, Instant.now())
        )
        .when(new AntragserfassungAbschliessenCommand(antragsnummer, "Vollständig erfasst und geprüft"))
        .expectEvents(new AntragserfassungAbgeschlossenEvent(
            antragsnummer,
            "Vollständig erfasst und geprüft",
            null
        ));
    }

    @Test
    void sollteValidierungsfehlerBeiLeeremBenutzerkennungWerfen() {
        UUID antragsnummer = UUID.randomUUID();
        
        fixture.givenNoPriorActivity()
            .when(new StarteAntragCommand(antragsnummer, ""))
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
                null, null, anschrift, null, null
            ))
            .expectException(IllegalArgumentException.class);
    }

    @Test
    void sollteValidierungsfehlerBeiNegativemKaufpreisWerfen() {
        UUID antragsnummer = UUID.randomUUID();
        Anschrift anschrift = new Anschrift("Test", "1", "12345", "Test", "DE");
        
        fixture.given(
            new AntragGestartetEvent(antragsnummer, "testBenutzer", Instant.now()),
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, Instant.now())
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
            new AntragstellerErfasstEvent(antragsnummer, "Max", "Test", LocalDate.now(), null, null, anschrift, null, null, Instant.now()),
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
            null, null, anschrift, null, null
        ))
        .expectException(IllegalStateException.class);
    }
}
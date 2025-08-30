package com.bigpugloans.antragserfassung.infrastructure;

import com.bigpugloans.antragserfassung.domain.model.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
@InfrastructureRing
public class SampleDataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);
    
    private final CommandGateway commandGateway;

    @Autowired
    public SampleDataLoader(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Lade Beispieldaten für Antragserfassung...");
        
        try {
            UUID beispielAntragsnummer = UUID.randomUUID();
            String beispielBenutzerId = "demo.user";
            
            // 1. Antrag starten
            StarteAntragCommand starteAntrag = new StarteAntragCommand(
                beispielAntragsnummer, 
                beispielBenutzerId
            );
            commandGateway.sendAndWait(starteAntrag);
            logger.info("Beispiel-Antrag gestartet: {}", beispielAntragsnummer);
            
            // 2. Antragsteller erfassen
            Anschrift anschrift = new Anschrift(
                "Musterstraße", 
                "42", 
                "12345", 
                "Musterstadt",
                "Deutschland"
            );
            
            AntragstellerErfassenCommand antragstellerCommand = new AntragstellerErfassenCommand(
                beispielAntragsnummer,
                "Max",
                "Mustermann", 
                LocalDate.of(1985, 6, 15),
                "+49 123 456789",
                "max.mustermann@example.com",
                anschrift,
                Familienstand.VERHEIRATET,
                2
            );
            commandGateway.sendAndWait(antragstellerCommand);
            logger.info("Antragsteller erfasst");
            
            // 3. Finanzierungsobjekt erfassen
            Anschrift objektAdresse = new Anschrift(
                "Immobilienstraße", 
                "123", 
                "54321", 
                "Objektstadt",
                "Deutschland"
            );
            
            FinanzierungsobjektErfassenCommand objektCommand = new FinanzierungsobjektErfassenCommand(
                beispielAntragsnummer,
                Objektart.EIGENTUMSWOHNUNG,
                objektAdresse,
                new BigDecimal("350000"),
                new BigDecimal("25000"),
                2015, // Baujahr
                95.0, // Wohnfläche
                4,    // Anzahl Zimmer
                Nutzungsart.EIGENNUTZUNG
            );
            commandGateway.sendAndWait(objektCommand);
            logger.info("Finanzierungsobjekt erfasst");
            
            // 4. Ausgaben erfassen
            AusgabenErfassenCommand ausgabenCommand = new AusgabenErfassenCommand(
                beispielAntragsnummer,
                new BigDecimal("1800"), // Lebenshaltungskosten
                new BigDecimal("950"),  // Miete
                new BigDecimal("450"),  // Private Krankenversicherung
                new BigDecimal("180"),  // Sonstige Versicherungen
                new BigDecimal("320"),  // Kreditraten
                new BigDecimal("250")   // Sonstige Ausgaben
            );
            commandGateway.sendAndWait(ausgabenCommand);
            logger.info("Ausgaben erfasst");
            
            // 5. Einkommen erfassen
            EinkommenErfassenCommand einkommenCommand = new EinkommenErfassenCommand(
                beispielAntragsnummer,
                new BigDecimal("4200"),  // Netto-Einkommen
                new BigDecimal("2800"),  // Urlaubsgeld (jährlich)
                new BigDecimal("3500"),  // Weihnachtsgeld (jährlich)
                new BigDecimal("850"),   // Mieteinnahmen
                new BigDecimal("120"),   // Kapitalerträge
                new BigDecimal("200"),   // Sonstige Einkommen
                Beschaeftigungsverhaeltnis.ANGESTELLT
            );
            commandGateway.sendAndWait(einkommenCommand);
            logger.info("Einkommen erfasst");
            
            // Antrag bewusst NICHT abschließen, sodass er noch bearbeitet werden kann
            logger.info("Beispiel-Antrag vollständig erfasst aber noch nicht abgeschlossen - bereit zur Bearbeitung!");
            logger.info("Antragsnummer: {}", beispielAntragsnummer);
            logger.info("Benutzer-ID: {}", beispielBenutzerId);
            
            // ===== ZWEITER BEISPIEL-ANTRAG (ABGESCHLOSSEN) =====
            createAbgeschlossenenAntrag();
            
            // ===== DRITTER BEISPIEL-ANTRAG (TEILWEISE AUSGEFÜLLT) =====
            createPartiellenAntrag();
            
        } catch (Exception e) {
            logger.error("Fehler beim Laden der Beispieldaten", e);
        }
    }
    
    private void createAbgeschlossenenAntrag() throws Exception {
        UUID abgeschlossenerAntragsnummer = UUID.randomUUID();
        String benutzerId = "demo.user";
        
        // Antrag starten
        commandGateway.sendAndWait(new StarteAntragCommand(abgeschlossenerAntragsnummer, benutzerId));
        
        // Antragsteller
        Anschrift anschrift2 = new Anschrift("Beispielweg", "7", "67890", "Beispielort", "Deutschland");
        commandGateway.sendAndWait(new AntragstellerErfassenCommand(
            abgeschlossenerAntragsnummer, "Anna", "Schmidt", LocalDate.of(1990, 3, 22),
            "+49 987 654321", "anna.schmidt@example.com", anschrift2,
            Familienstand.LEDIG, 0
        ));
        
        // Finanzierungsobjekt
        Anschrift objektAdresse2 = new Anschrift("Eigenheimstraße", "88", "98765", "Wohnort", "Deutschland");
        commandGateway.sendAndWait(new FinanzierungsobjektErfassenCommand(
            abgeschlossenerAntragsnummer, Objektart.EINFAMILIENHAUS, objektAdresse2,
            new BigDecimal("280000"), new BigDecimal("18000"),
            2010, 120.0, 5, Nutzungsart.EIGENNUTZUNG
        ));
        
        // Ausgaben
        commandGateway.sendAndWait(new AusgabenErfassenCommand(
            abgeschlossenerAntragsnummer, new BigDecimal("1400"), new BigDecimal("0"), 
            new BigDecimal("0"), new BigDecimal("120"), new BigDecimal("180"), new BigDecimal("150")
        ));
        
        // Einkommen
        commandGateway.sendAndWait(new EinkommenErfassenCommand(
            abgeschlossenerAntragsnummer, new BigDecimal("3200"), new BigDecimal("1800"), 
            new BigDecimal("2200"), new BigDecimal("0"), new BigDecimal("80"), 
            new BigDecimal("0"), Beschaeftigungsverhaeltnis.ANGESTELLT
        ));
        
        // Antrag abschließen
        commandGateway.sendAndWait(new AntragserfassungAbschliessenCommand(
            abgeschlossenerAntragsnummer, "Vollständige Unterlagen eingereicht - bereit zur Prüfung"
        ));
        
        logger.info("Zweiter Beispiel-Antrag (abgeschlossen) erstellt: {}", abgeschlossenerAntragsnummer);
    }
    
    private void createPartiellenAntrag() throws Exception {
        UUID partiellerAntragsnummer = UUID.randomUUID();
        String benutzerId = "demo.user";
        
        // Antrag starten
        commandGateway.sendAndWait(new StarteAntragCommand(partiellerAntragsnummer, benutzerId));
        
        // Nur Antragsteller erfassen - Rest bleibt leer
        Anschrift anschrift3 = new Anschrift("Teilstraße", "15", "11111", "Teilstadt", "Deutschland");
        commandGateway.sendAndWait(new AntragstellerErfassenCommand(
            partiellerAntragsnummer, "Julia", "Weber", LocalDate.of(1988, 11, 5),
            "+49 555 123456", "julia.weber@example.com", anschrift3,
            Familienstand.GESCHIEDEN, 1
        ));
        
        logger.info("Dritter Beispiel-Antrag (nur Antragsteller erfasst) erstellt: {}", partiellerAntragsnummer);
        logger.info("=== BEISPIELDATEN ERFOLGREICH GELADEN ===");
        logger.info("✅ Vollständiger Antrag (nicht abgeschlossen) - bereit zur Bearbeitung");
        logger.info("✅ Abgeschlossener Antrag - zur Ansicht");
        logger.info("✅ Partieller Antrag - zur Weiterbearbeitung");
    }
}
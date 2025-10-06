package com.bigpugloans.antragserfassung.domain.model;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jmolecules.architecture.onion.classical.DomainModelRing;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.Instant;
import java.util.UUID;

@Aggregate
@AggregateRoot
@DomainModelRing
public class Antragserfassung {

    @AggregateIdentifier
    @Identity
    private UUID antragsnummer;
    
    private AntragserfassungsStatus status;
    private String benutzerId;
    private Instant gestartetAm;
    
    private boolean antragstellerErfasst;
    private boolean finanzierungsobjektErfasst;
    private boolean ausgabenErfasst;
    private boolean einkommenErfasst;

    protected Antragserfassung() {
        // Required by Axon
    }

    @CommandHandler
    public Antragserfassung(StarteAntragCommand command) {
        System.out.println("StarteAntragCommand im Aggregate empfangen: " + command);
        if (command.benutzerId() == null || command.benutzerId().trim().isEmpty()) {
            throw new IllegalArgumentException("BenutzerID darf nicht leer sein");
        }
        
        UUID antragsnummer = UUID.randomUUID();
        System.out.println("Neue Antragsnummer generiert: " + antragsnummer);
        
        AggregateLifecycle.apply(new AntragGestartetEvent(
            antragsnummer,
            command.benutzerId(),
            Instant.now()
        ));
        System.out.println("AntragGestartetEvent angewendet für Antragsnummer: " + antragsnummer);
    }

    @CommandHandler
    public void handle(AntragstellerErfassenCommand command) {
        pruefeObAntragBearbeitbar();
        
        if (command.vorname() == null || command.vorname().trim().isEmpty()) {
            throw new IllegalArgumentException("Vorname darf nicht leer sein");
        }
        if (command.nachname() == null || command.nachname().trim().isEmpty()) {
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        }
        if (command.geburtsdatum() == null) {
            throw new IllegalArgumentException("Geburtsdatum darf nicht null sein");
        }
        if (command.anschrift() == null) {
            throw new IllegalArgumentException("Anschrift darf nicht null sein");
        }
        
        AggregateLifecycle.apply(new AntragstellerErfasstEvent(
            command.antragsnummer(),
            command.vorname(),
            command.nachname(),
            command.geburtsdatum(),
            command.telefonnummer(),
            command.emailAdresse(),
            command.anschrift(),
            command.familienstand(),
            command.anzahlKinder(),
            command.kundennummer(),
            command.branche(),
            command.berufsart(),
            command.arbeitgeber(),
            command.beschaeftigtSeit(),
            Instant.now()
        ));
    }

    @CommandHandler
    public void handle(FinanzierungsobjektErfassenCommand command) {
        pruefeObAntragBearbeitbar();
        
        if (!antragstellerErfasst) {
            throw new IllegalStateException("Antragsteller muss zuerst erfasst werden");
        }
        if (command.objektart() == null) {
            throw new IllegalArgumentException("Objektart darf nicht null sein");
        }
        if (command.objektAdresse() == null) {
            throw new IllegalArgumentException("Objektadresse darf nicht null sein");
        }
        if (command.kaufpreis() == null || command.kaufpreis().signum() <= 0) {
            throw new IllegalArgumentException("Kaufpreis muss größer als 0 sein");
        }
        
        AggregateLifecycle.apply(new FinanzierungsobjektErfasstEvent(
            command.antragsnummer(),
            command.objektart(),
            command.objektAdresse(),
            command.kaufpreis(),
            command.nebenkosten(),
            command.baujahr(),
            command.wohnflaeche(),
            command.anzahlZimmer(),
            command.nutzungsart(),
            Instant.now()
        ));
    }

    @CommandHandler
    public void handle(AusgabenErfassenCommand command) {
        pruefeObAntragBearbeitbar();
        
        if (!finanzierungsobjektErfasst) {
            throw new IllegalStateException("Finanzierungsobjekt muss zuerst erfasst werden");
        }
        
        AggregateLifecycle.apply(new AusgabenErfasstEvent(
            command.antragsnummer(),
            command.lebenshaltungskosten(),
            command.miete(),
            command.privateKrankenversicherung(),
            command.sonstigeVersicherungen(),
            command.kreditraten(),
            command.sonstigeAusgaben(),
            Instant.now()
        ));
    }

    @CommandHandler
    public void handle(EinkommenErfassenCommand command) {
        pruefeObAntragBearbeitbar();
        
        if (!ausgabenErfasst) {
            throw new IllegalStateException("Ausgaben müssen zuerst erfasst werden");
        }
        if (command.nettoEinkommen() == null || command.nettoEinkommen().signum() <= 0) {
            throw new IllegalArgumentException("Netto-Einkommen muss größer als 0 sein");
        }
        
        AggregateLifecycle.apply(new EinkommenErfasstEvent(
            command.antragsnummer(),
            command.nettoEinkommen(),
            command.urlaubsgeld(),
            command.weihnachtsgeld(),
            command.mieteinnahmen(),
            command.kapitalertraege(),
            command.sonstigeEinkommen(),
            command.beschaeftigungsverhaeltnis(),
            Instant.now()
        ));
    }

    @CommandHandler
    public void handle(AntragserfassungAbschliessenCommand command) {
        pruefeObAntragBearbeitbar();
        
        if (!istErfassungVollstaendig()) {
            throw new IllegalStateException("Antragserfassung kann nur abgeschlossen werden, wenn alle Daten erfasst sind");
        }
        
        AggregateLifecycle.apply(new AntragserfassungAbgeschlossenEvent(
            command.antragsnummer(),
            command.benutzerKommentar(),
            Instant.now()
        ));
    }

    @EventSourcingHandler
    public void on(AntragGestartetEvent event) {
        this.antragsnummer = event.antragsnummer();
        this.benutzerId = event.benutzerId();
        this.gestartetAm = event.zeitpunkt();
        this.status = AntragserfassungsStatus.GESTARTET;
        this.antragstellerErfasst = false;
        this.finanzierungsobjektErfasst = false;
        this.ausgabenErfasst = false;
        this.einkommenErfasst = false;
    }

    @EventSourcingHandler
    public void on(AntragstellerErfasstEvent event) {
        this.antragstellerErfasst = true;
    }

    @EventSourcingHandler
    public void on(FinanzierungsobjektErfasstEvent event) {
        this.finanzierungsobjektErfasst = true;
    }

    @EventSourcingHandler
    public void on(AusgabenErfasstEvent event) {
        this.ausgabenErfasst = true;
    }

    @EventSourcingHandler
    public void on(EinkommenErfasstEvent event) {
        this.einkommenErfasst = true;
    }

    @EventSourcingHandler
    public void on(AntragserfassungAbgeschlossenEvent event) {
        this.status = AntragserfassungsStatus.ABGESCHLOSSEN;
    }

    private void pruefeObAntragBearbeitbar() {
        if (this.status == AntragserfassungsStatus.ABGESCHLOSSEN) {
            throw new IllegalStateException("Antrag wurde bereits abgeschlossen und kann nicht mehr bearbeitet werden");
        }
    }

    private boolean istErfassungVollstaendig() {
        return antragstellerErfasst && finanzierungsobjektErfasst && ausgabenErfasst && einkommenErfasst;
    }
}
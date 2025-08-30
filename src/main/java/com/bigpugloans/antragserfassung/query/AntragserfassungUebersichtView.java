package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.AntragserfassungsStatus;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.time.Instant;
import java.util.UUID;

@InfrastructureRing
public record AntragserfassungUebersichtView(
    UUID antragsnummer,
    String benutzerId,
    AntragserfassungsStatus status,
    Instant gestartetAm,
    Instant abgeschlossenAm,
    boolean antragstellerErfasst,
    boolean finanzierungsobjektErfasst,
    boolean ausgabenErfasst,
    boolean einkommenErfasst,
    boolean abgeschlossen
) {
    public AntragserfassungUebersichtView withAntragstellerErfasst(boolean antragstellerErfasst) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withFinanzierungsobjektErfasst(boolean finanzierungsobjektErfasst) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withAusgabenErfasst(boolean ausgabenErfasst) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withEinkommenErfasst(boolean einkommenErfasst) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withStatus(AntragserfassungsStatus status) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withAbgeschlossenAm(Instant abgeschlossenAm) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public AntragserfassungUebersichtView withAbgeschlossen(boolean abgeschlossen) {
        return new AntragserfassungUebersichtView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm,
            antragstellerErfasst, finanzierungsobjektErfasst, ausgabenErfasst, einkommenErfasst, abgeschlossen
        );
    }

    public double fortschrittsprozenteBerechnen() {
        int erfasstSchritte = 0;
        int gesamtSchritte = 4;
        
        if (antragstellerErfasst) erfasstSchritte++;
        if (finanzierungsobjektErfasst) erfasstSchritte++;
        if (ausgabenErfasst) erfasstSchritte++;
        if (einkommenErfasst) erfasstSchritte++;
        
        return (double) erfasstSchritte / gesamtSchritte * 100.0;
    }
}
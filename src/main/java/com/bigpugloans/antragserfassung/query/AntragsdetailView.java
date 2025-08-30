package com.bigpugloans.antragserfassung.query;

import com.bigpugloans.antragserfassung.domain.model.AntragserfassungsStatus;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;

import java.time.Instant;
import java.util.UUID;

@InfrastructureRing
public record AntragsdetailView(
    UUID antragsnummer,
    String benutzerId,
    AntragserfassungsStatus status,
    Instant gestartetAm,
    Instant abgeschlossenAm,
    String benutzerKommentar,
    AntragstellerDetails antragstellerDetails,
    FinanzierungsobjektDetails finanzierungsobjektDetails,
    AusgabenDetails ausgabenDetails,
    EinkommenDetails einkommenDetails
) {
    public static Builder builder() {
        return new Builder();
    }

    public AntragsdetailView withAntragstellerDetails(AntragstellerDetails antragstellerDetails) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withFinanzierungsobjektDetails(FinanzierungsobjektDetails finanzierungsobjektDetails) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withAusgabenDetails(AusgabenDetails ausgabenDetails) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withEinkommenDetails(EinkommenDetails einkommenDetails) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withStatus(AntragserfassungsStatus status) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withAbgeschlossenAm(Instant abgeschlossenAm) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public AntragsdetailView withBenutzerKommentar(String benutzerKommentar) {
        return new AntragsdetailView(
            antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
            antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
        );
    }

    public static class Builder {
        private UUID antragsnummer;
        private String benutzerId;
        private AntragserfassungsStatus status;
        private Instant gestartetAm;
        private Instant abgeschlossenAm;
        private String benutzerKommentar;
        private AntragstellerDetails antragstellerDetails;
        private FinanzierungsobjektDetails finanzierungsobjektDetails;
        private AusgabenDetails ausgabenDetails;
        private EinkommenDetails einkommenDetails;

        public Builder antragsnummer(UUID antragsnummer) {
            this.antragsnummer = antragsnummer;
            return this;
        }

        public Builder benutzerId(String benutzerId) {
            this.benutzerId = benutzerId;
            return this;
        }

        public Builder status(AntragserfassungsStatus status) {
            this.status = status;
            return this;
        }

        public Builder gestartetAm(Instant gestartetAm) {
            this.gestartetAm = gestartetAm;
            return this;
        }

        public AntragsdetailView build() {
            return new AntragsdetailView(
                antragsnummer, benutzerId, status, gestartetAm, abgeschlossenAm, benutzerKommentar,
                antragstellerDetails, finanzierungsobjektDetails, ausgabenDetails, einkommenDetails
            );
        }
    }
}
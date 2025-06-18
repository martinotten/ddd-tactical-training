package com.bigpugloans.antragserfassung.write;

import com.bigpugloans.antragserfassung.Antragsnummer;
import org.axonframework.eventsourcing.annotations.EventTag;

public record AntragGestartet(
        @EventTag
        Antragsnummer antragsnummer
) {

}

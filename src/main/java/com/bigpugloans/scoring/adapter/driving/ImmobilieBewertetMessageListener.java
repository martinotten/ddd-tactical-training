package com.bigpugloans.scoring.adapter.driving;

import com.bigpugloans.events.ImmobilieBewertet;
import com.bigpugloans.scoring.domain.model.ImmobilienBewertung;
import com.bigpugloans.scoring.application.ports.driving.VerarbeitungImmobilienBewertung;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jmolecules.architecture.onion.classical.InfrastructureRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@InfrastructureRing
@PrimaryPort
public class ImmobilieBewertetMessageListener {
    private final VerarbeitungImmobilienBewertung verarbeitungImmobilienBewertung;

    @Autowired
    public ImmobilieBewertetMessageListener(VerarbeitungImmobilienBewertung verarbeitungImmobilienBewertung) {
        this.verarbeitungImmobilienBewertung = verarbeitungImmobilienBewertung;
    }

    @EventListener
    public void onImmobilieBewertet(ImmobilieBewertet event) {

        verarbeitungImmobilienBewertung.verarbeiteImmobilienBewertung(konvertiereZuImmobilienBewertung(event));
    }

    private ImmobilienBewertung konvertiereZuImmobilienBewertung(ImmobilieBewertet event) {
        return new ImmobilienBewertung(
                event.getAntragsnummer(),
                event.getBeleihungswert(),
                event.getMinimalerMarktwert(),
                event.getMaximalerMarktwert(),
                event.getDurchschnittlicherMarktwertVon(),
                event.getDurchschnittlicherMarktwertBis()
        );
    }

}

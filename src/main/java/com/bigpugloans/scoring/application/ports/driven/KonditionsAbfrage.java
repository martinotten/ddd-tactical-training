package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.domain.model.AuskunfteiErgebnis;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.time.LocalDate;

@ApplicationServiceRing
@SecondaryPort
public interface KonditionsAbfrage {
    AuskunfteiErgebnis konditionsAbfrage(String vorname, String nachname, String strasse, String stadt, String plz, LocalDate geburtsdatum);
}

package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jmolecules.architecture.onion.classical.ApplicationServiceRing;

import java.util.Date;

@ApplicationServiceRing
@SecondaryPort
public interface KonditionsAbfrage {
    public AuskunfteiErgebnis konditionsAbfrage(String vorname, String nachname, String strasse, String stadt, String plz, Date geburtsdatum);
}

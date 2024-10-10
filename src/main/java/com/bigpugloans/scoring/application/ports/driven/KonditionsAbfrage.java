package com.bigpugloans.scoring.application.ports.driven;

import com.bigpugloans.scoring.application.model.AuskunfteiErgebnis;

import java.util.Date;

public interface KonditionsAbfrage {
    public AuskunfteiErgebnis konditionsAbfrage(String vorname, String nachname, String strasse, String stadt, String plz, Date geburtsdatum);
}

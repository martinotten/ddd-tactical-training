package com.bigpugloans.scoring.application.ports.driven;

import java.util.Date;

public interface KonditionsAbfrage {
    public void konditionsAbfrage(String vorname, String nachname, String strasse, String stadt, String plz, Date geburtsdatum);
}

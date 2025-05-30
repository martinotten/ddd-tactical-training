package com.bigpugloans.scoring.domainmodel;

import java.util.Map;

public class Wohnort {
    private static final Map<String, Integer> POINTS = Map.of(
            "Hamburg", 5,
            "München", 5
    );

    private final String wohnort;

    Wohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public Punkte berechnePunkte() {
        return new Punkte(POINTS.getOrDefault(wohnort, 0));
    }
}

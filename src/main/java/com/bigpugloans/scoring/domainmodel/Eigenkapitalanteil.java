package com.bigpugloans.scoring.domainmodel;

public class Eigenkapitalanteil {
    private final Prozentwert value;

    public Eigenkapitalanteil(Prozentwert value) {
        this.value = value;
    }

    public Punkte berechnePunkte() {
        return switch (value) {
            case Prozentwert p when p.groesserAls(new Prozentwert(30))
                -> new Punkte(15);
            case Prozentwert p when p.zwischen(new Prozentwert(20), new Prozentwert(30))
                -> new Punkte(10);
            case Prozentwert p when p.zwischen(new Prozentwert(15), new Prozentwert(20))
                -> new Punkte(5);
            default -> new Punkte(0);
        };
    }
}
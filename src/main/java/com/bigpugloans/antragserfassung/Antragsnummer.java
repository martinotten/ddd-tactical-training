package com.bigpugloans.antragserfassung;

public record Antragsnummer(String nummer) {

    @Override
    public String toString() {
        return nummer;
    }
}

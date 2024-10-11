package com.bigpugloans.scoring.domain.model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

public class AntragstellerID {
    private String antragstellerID;

    public AntragstellerID(String antragstellerID) {
        if (antragstellerID == null) {
            throw new IllegalArgumentException("antragstellerID darf nicht null sein.");
        }
        this.antragstellerID = antragstellerID;
    }

    public String id() {
        return antragstellerID;
    }
    @Override
    public String toString() {
        return antragstellerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntragstellerID that = (AntragstellerID) o;
        return Objects.equals(antragstellerID, that.antragstellerID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(antragstellerID);
    }

    private AntragstellerID(Builder builder) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] shaHash = messageDigest.digest(builder.toString().getBytes());
            this.antragstellerID = convertByteArrayToHexString(shaHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

    public static class Builder {
        private final String vorname;
        private final String nachname;
        private String strasse;
        private String postleitzahl;
        private String stadt;
        private Date geburtsdatum;

        public Builder(String vorname, String nachname) {
            this.vorname = vorname;
            this.nachname = nachname;
        }

        public Builder geburtsdatum(Date geburtsdatum) {
            this.geburtsdatum = geburtsdatum;
            return this;
        }

        public Builder strasse(String strasse) {
            this.strasse = strasse;
            return this;
        }

        public Builder postleitzahl(String postleitzahl) {
            this.postleitzahl = postleitzahl;
            return this;
        }

        public Builder stadt(String stadt) {
            this.stadt = stadt;
            return this;
        }

        @Override
        public String toString() {
            return "AntragstellerID{" +
                    "vorname='" + vorname + '\'' +
                    ", nachname='" + nachname + '\'' +
                    ", strasse='" + strasse + '\'' +
                    ", postleitzahl='" + postleitzahl + '\'' +
                    ", geburtsdatum='" + geburtsdatum + '\'' +
                    ", stadt='" + stadt + '\'' +
                    '}';
        }

        public AntragstellerID build() {
            return new AntragstellerID(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder that = (Builder) o;
            return Objects.equals(vorname, that.vorname) &&
                    Objects.equals(nachname, that.nachname) &&
                    Objects.equals(geburtsdatum, that.geburtsdatum) &&
                    Objects.equals(strasse, that.strasse) &&
                    Objects.equals(postleitzahl, that.postleitzahl) &&
                    Objects.equals(stadt, that.stadt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vorname, nachname, strasse, postleitzahl, stadt, geburtsdatum);
        }
    }
}
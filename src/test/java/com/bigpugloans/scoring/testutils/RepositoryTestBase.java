package com.bigpugloans.scoring.testutils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class RepositoryTestBase {
    protected Connection connection;

    @BeforeEach
    void setupDatabase() throws SQLException {
        // Create in-memory H2 database with unique name per test
        String dbName = "testdb_" + System.nanoTime();
        connection = DriverManager.getConnection("jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1", "sa", "");
        
        // Create schema
        createSchema();
    }

    @AfterEach
    void teardownDatabase() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables based on the actual schema
            stmt.execute("""
                CREATE TABLE SCORING_MONATLICHE_FINANZSITUATION_CLUSTER (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    antragsnummer VARCHAR(255) NOT NULL,
                    einnahmen DECIMAL(19, 2),
                    ausgaben DECIMAL(19, 2),
                    neue_darlehens_belastungen DECIMAL(19, 2),
                    version BIGINT
                )
            """);

            stmt.execute("""
                CREATE TABLE scoring_antragsteller_cluster (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    antragsnummer VARCHAR(255) NOT NULL,
                    wohnort VARCHAR(255),
                    guthaben DECIMAL(19, 2),
                    version BIGINT
                )
            """);

            stmt.execute("""
                CREATE TABLE SCORING_IMMOBILIEN_FINANZIERUNGS_CLUSTER (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    antragsnummer VARCHAR(255) NOT NULL,
                    marktwert DECIMAL(19, 2),
                    eigenmittel DECIMAL(19, 2),
                    summe_darlehen DECIMAL(19, 2),
                    beleihungswert DECIMAL(19, 2),
                    kaufnebenkosten DECIMAL(19, 2),
                    minimaler_marktwert DECIMAL(19, 2),
                    maximaler_marktwert DECIMAL(19, 2),
                    durchschnittlicher_marktwert_von DECIMAL(19, 2),
                    durchschnittlicher_marktwert_bis DECIMAL(19, 2),
                    version BIGINT
                )
            """);

            stmt.execute("""
                CREATE TABLE SCORING_AUSKUNFTEI_ERGEBNIS_CLUSTER (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    antragsteller_id varchar(255) NOT NULL,
                    antragsnummer VARCHAR(255) NOT NULL,
                    anzahl_warnungen INTEGER,
                    anzahl_negativ_merkmale INTEGER,
                    rueckzahlungs_wahrscheinlichkeit DECIMAL(19, 2),
                    version BIGINT
                )
            """);

            stmt.execute("""
                CREATE TABLE SCORING_SCORING_ERGEBNIS (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    antragsnummer VARCHAR(255) NOT NULL,
                    auskunftei_punkte INTEGER,
                    auskunftei_ko_kriterien INTEGER,
                    antragsteller_punkte INTEGER,
                    antragsteller_ko_kriterien INTEGER,
                    monatliche_finanzsituation_punkte INTEGER,
                    monatliche_finanzsituation_ko_kriterien INTEGER,
                    immobilien_finanzierung_punkte INTEGER,
                    immobilien_finanzierung_ko_kriterien INTEGER,
                    gesamt_punkte INTEGER,
                    gesamt_ko_kriterien INTEGER,
                    version BIGINT
                )
            """);
        }
    }
}
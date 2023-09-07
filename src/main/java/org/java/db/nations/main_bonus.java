package org.java.db.nations;

import java.sql.*;
import java.util.Scanner;

public class main_bonus {
    public static void main(String[] args) {
        System.out.println("Inserisci la stringa di ricerca: ");
        Scanner scanner = new Scanner(System.in);
        String searchString = scanner.nextLine();
        try {
            String stringaConnessione = "jdbc:mysql://localhost:3306/nation";
            String username = "root";
            String password = "Nuova_password1!!";
            Connection connessione = DriverManager.getConnection(stringaConnessione, username, password);
            if (connessione == null) {
                throw new SQLException("Connessione al database non riuscita");
            }
            String query = "SELECT countries.name AS Lista_Delle_Nazioni, countries.country_id AS ID_Nazione, " +
                    "regions.name AS Nome_Regione, continents.name AS Nome_Continente FROM countries " +
                    "JOIN regions ON countries.region_id = regions.region_id " +
                    "JOIN continents ON regions.continent_id = continents.continent_id " +
                    "WHERE countries.name LIKE ? " +
                    "ORDER BY countries.name";

            PreparedStatement statement = connessione.prepareStatement(query);
            statement.setString(1, "%" + searchString + "%");

            ResultSet risultati = statement.executeQuery();
            boolean found = false;
            int n = 0;
            while (risultati.next()) {

                System.out.println(risultati.getString("Lista_Delle_Nazioni") + ", ID: "
                        + risultati.getString("ID_Nazione")  + ", Nome regione: "
                        + risultati.getString("Nome_Regione") + ", Nome continente: "
                        + risultati.getString("Nome_Continente"));
                found = true;
                n++;
            }
            if (!found) {
                System.out.println("Nessun risultato trovato.");
            }

            System.out.println(" ");
            System.out.println("Numero di risultati: " + n);
            System.out.println("-----------------------");
            System.out.println("Inserisci ID da ricercare: ");
            int countryID = scanner.nextInt();


            String lenguageQuery = "SELECT languages.language " +
                    "FROM languages " +
                    "JOIN country_languages ON languages.language_id = country_languages.language_id " +
                    "WHERE country_languages.country_id = ?";
            PreparedStatement lenguageStatement = connessione.prepareStatement(lenguageQuery);
            lenguageStatement.setInt(1, countryID);
            ResultSet lenguageResult = lenguageStatement.executeQuery();
            System.out.println("Lingue parlate:");

            while (lenguageResult.next()) {
                System.out.println(lenguageResult.getString("language"));
            }
            String statsQuery = "SELECT * FROM country_stats WHERE country_id = ? ORDER BY year DESC LIMIT 1";
            PreparedStatement statsStatement = connessione.prepareStatement(statsQuery);
            statsStatement.setInt(1, countryID);
            ResultSet statsResult = statsStatement.executeQuery();

            System.out.println("Ecco le statistiche più recenti: ");
            while (statsResult.next()) {
                System.out.println("Anno: " + statsResult.getInt("year"));
                System.out.println("Popolazione: " + statsResult.getInt("population"));
                System.out.println("GDP: " + statsResult.getString("gdp"));
            }

            connessione.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

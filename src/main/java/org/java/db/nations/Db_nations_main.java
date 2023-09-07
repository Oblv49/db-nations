package org.java.db.nations;

import java.sql.*;
import java.util.Scanner;

public class Db_nations_main {
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

                System.out.println(risultati.getString("Lista_Delle_Nazioni") + ", "
                        + risultati.getString("ID_Nazione") + ", "
                        + risultati.getString("Nome_Regione") + ", "
                        + risultati.getString("Nome_Continente"));
                found = true;
                n++;
            }
            System.out.println(" ");
            System.out.println("Numero di risultati: " + n);

            if (!found) {
                System.out.println("Nessun risultato trovato.");
            }
            connessione.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
package org.java.db.nations;

import java.sql.*;

public class Db_nations_main {
    public static void main(String[] args) {
        System.out.println("Nazioni: ");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nation", "root", "Nuova_password1!!");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT countries.name AS Lista_Delle_Nazioni, countries.country_id AS ID_Nazione, " +
                                                           "regions.name AS Nome_Regione, continents.name AS Nome_Continente FROM countries " +
                                                           "JOIN regions ON countries.region_id = regions.region_id " +
                                                           "JOIN continents ON regions.continent_id = continents.continent_id " +
                                                           "ORDER BY countries.name");
            while (results.next()) {
                System.out.println(results.getString("Lista_Delle_Nazioni") + ", "
                        + results.getString("ID_Nazione") + ", "
                        + results.getString("Nome_Regione") + ", "
                        + results.getString("Nome_Continente"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

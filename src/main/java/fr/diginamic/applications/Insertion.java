package fr.diginamic.applications;

import fr.diginamic.entites.MongoManager;
import fr.diginamic.utils.InsererBdD;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Insertion {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        InputStream input = Manipulation.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
        props.load(input);

        String host = props.getProperty("mongodb.host");
        String dbName = props.getProperty("mongodb.database");
        String collName = props.getProperty("mongodb.collection");
        String uri = "mongodb://" + host + "/";
        MongoManager mongoManager = new MongoManager(uri, dbName, collName);

        // Étape 1 : Création de la Base de Données et des Collections
        mongoManager.setDatabase("store");
        mongoManager.setCollection("products");

        // Étape 2 : Importation des Données
        InsererBdD.inserer(mongoManager);

        System.out.println("Database : " + mongoManager.getDatabase());
        System.out.println("Collection : " + mongoManager.getCollection());

        // Close
        mongoManager.closeConnection();

    }

}

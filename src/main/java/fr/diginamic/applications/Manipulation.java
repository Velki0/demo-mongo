package fr.diginamic.applications;

import fr.diginamic.entites.MongoManager;
import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.mongodb.client.model.Sorts.descending;

public class Manipulation {

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

        // Étape 3 : Manipulation des Données
        // A. Insérer des documents
        Document lichi = new Document()
                .append("name", "Lichi")
                .append("category", "Fruit")
                .append("color", "Yellow")
                .append("price", 3.95)
                .append("quantity", 15);
        Document papaye = new Document()
                .append("name", "Papaye")
                .append("category", "Fruit")
                .append("color", "Red")
                .append("price", 6.92)
                .append("quantity", 19);
        Document artichaut = new Document()
                .append("name", "Artichaut")
                .append("category", "Legume")
                .append("color", "Green")
                .append("price", 13.95)
                .append("quantity", 5);
        // Un seul à la fois
        Map<String, Object> createLichi = mongoManager.createOneDocument(lichi);
        System.out.println("create_one_document: Lichi " + createLichi);
        // Plusieurs à la fois
        Map<String, Object> createPapayeAndArtichaut = mongoManager.createManyDocuments(List.of(papaye, artichaut));
        System.out.println("create_many_document: PapayeAndArtichaut " + createPapayeAndArtichaut);

        // B. Mettre à jour des documents
            // 1. Mettez à jour le prix et la quantité d'un produit spécifique.
        Map<String, Object> updatePapayePrice = mongoManager.updateOneDocument(
                new Document("name", "Papaye"),
                new Document("$set", new Document("price", 7.32))
        );
        System.out.println("update_one_document: PapayePrice " + updatePapayePrice);
        Map<String, Object> updatePapayeQuantity = mongoManager.updateOneDocument(
                new Document("name", "Papaye"),
                new Document("$set", new Document("quantity", 12))
        );
        System.out.println("update_one_document: PapayeQuantity " + updatePapayeQuantity);

            // 2. Ajoutez une nouvelle propriété à un produit existant.
        Map<String, Object> updateKiwiAllergy = mongoManager.updateOneDocument(
                new Document("name", "Kiwi"),
                new Document("$set", new Document("allergy", "WARNING"))
        );
        System.out.println("update_one_document: KiwiAllergy " + updateKiwiAllergy);

            // 3. Supprimez une propriété d'un produit existant.
        Map<String, Object> updatePeachColor = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$unset", new Document("color", ""))
        );
        System.out.println("update_one_document: PeachColor " + updatePeachColor);

        // C. Manipuler des tableaux
            // 1. Ajoutez un élément à un tableau. Exemple : 'alternative_colors' égal à 'Green'.
        Map<String, Object> updatePeachAlternativeColor = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$set", new Document("alternative_color", new ArrayList<>()))
        );
        System.out.println("update_one_document: PeachAlternativeColor " + updatePeachAlternativeColor);
        Map<String, Object> updatePeachAlternativeColorWhite = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$push", new Document("alternative_color", "Orange"))
        );
        System.out.println("update_one_document: PeachAlternativeColorWhite " + updatePeachAlternativeColorWhite);

            // 2. Ajoutez plusieurs éléments à un tableau.
        Map<String, Object> updatePeachAlternativeColors = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$push", new Document("alternative_color", new Document("$each", Arrays.asList("White", "Green"))))
        );
        System.out.println("update_one_document: PeachAlternativeColors " + updatePeachAlternativeColors);

            // 3. Supprimez un élément d'un tableau.

        Map<String, Object> updatePeachDeleteGreenAlternativeColors = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$pull", new Document("alternative_color", "Green"))
        );
        System.out.println("update_one_document: PeachDeleteGreenAlternativeColors " + updatePeachDeleteGreenAlternativeColors);

            // 4. Supprimez le dernier élément d'un tableau.
        Map<String, Object> updatePeachDeleteLastAlternativeColors = mongoManager.updateOneDocument(
                new Document("name", "Peach"),
                new Document("$pop", new Document("alternative_color", 1))
        );
        System.out.println("update_one_document: PeachDeleteLastAlternativeColors " + updatePeachDeleteLastAlternativeColors);

        // D. Supprimer des documents
            // 1. Supprimez un fruit spécifique grâce à son _id de la collection `products`.
        Map<String, Object> findPepper = mongoManager.readOneDocument(new Document("name", "Pepper"));
        Map<String, Object> deletePepper = mongoManager.deleteOneDocument(
                new Document("_id", findPepper.get("_id"))
        );
        System.out.println("delete_one_document: Pepper " + deletePepper);

            // 2. Supprimer tous les fruits et légumes qui sont Green.
        Map<String, Object> deleteAllGreens = mongoManager.deleteManyDocuments(
                new Document("color", "Green")
        );
        System.out.println("delete_one_document: AllGreens " + deleteAllGreens);

        // E. Requêtes de recherche
            // 1. Recherchez tous les produits de couleur rouge.
        List<Document> selectAllReds = mongoManager.readManyDocuments(
                new Document("color", "Red")
        );
        System.out.println("Liste des produits rouges :");
        for (Document red : selectAllReds) {
            System.out.println(red);
        }

            // 2. Recherchez tous les produits dont le prix est inférieur à 2.00.
        List<Document> selectAllPriceLT2 = mongoManager.readManyDocuments(
                new Document("price", new Document("$lt", 2))
        );
        System.out.println("Liste des produits avec un prix inférieur à 2.00€ :");
        for (Document priceLT2 : selectAllPriceLT2) {
            System.out.println(priceLT2);
        }

            // 3. Recherchez le fruit qui a la plus grande quantité.
        Document selectProductGreatestQuantity = mongoManager.getCollection().find().sort(descending("quantity")).first();
        System.out.println("Produits le plus abondant : " + selectProductGreatestQuantity);

        // Close
        mongoManager.closeConnection();

    }

}

package fr.diginamic.utils;

import fr.diginamic.entites.MongoManager;
import fr.diginamic.entites.FruitLegume;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public class InsererBdD {

    public static void inserer(MongoManager mongoManager) throws IOException {

        List<FruitLegume> listeFruitsLegumes = JsonUtils.lireJSON();
        for (FruitLegume fruitLegume:listeFruitsLegumes) {

            Document document = new Document()
                    .append("name", fruitLegume.getName())
                    .append("category", fruitLegume.getCategory())
                    .append("color", fruitLegume.getColor())
                    .append("price", fruitLegume.getPrice())
                    .append("quantity", fruitLegume.getQuantity());

            mongoManager.createOneDocument(document);

        }

    }

}

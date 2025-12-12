package fr.diginamic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.entites.FruitLegume;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class JsonUtils {

    public static List<FruitLegume> lireJSON() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = mapper.readTree(Paths.get("src/main/resources/fruits_vegetables.json").toFile());
        String jsonString = mapper.writeValueAsString(jsonNode);
        return mapper.readValue(jsonString, new TypeReference<>() {});

    }

}

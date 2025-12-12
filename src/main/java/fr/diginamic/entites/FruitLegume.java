package fr.diginamic.entites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FruitLegume {

    private String name;
    private String category;
    private String color;
    private double price;
    private int quantity;

    @JsonCreator
    private FruitLegume(@JsonProperty("name") final String name,
                        @JsonProperty("category") final String category,
                        @JsonProperty("color") final String color,
                        @JsonProperty("price") final double price,
                        @JsonProperty("quantity") final int quantity) {

        this.name = name;
        this.category = category;
        this.color = color;
        this.price = price;
        this.quantity = quantity;

    }

    public String getName() { return name; }
    public String getCategory() {return category; }
    public String getColor() { return color; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "{" + name + ", " + category + ", " + color + ", " + price + ", " + quantity + "}";
    }

}

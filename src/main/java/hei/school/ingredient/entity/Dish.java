package hei.school.ingredient.entity;

import java.util.List;

public class Dish {

    private Integer id;
    private String name;
    private double sellingPrice;
    private String category;
    private List<Ingredient> ingredients;

    public Dish() {}

    public Dish(Integer id, String name, Double sellingPrice, String category, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
}
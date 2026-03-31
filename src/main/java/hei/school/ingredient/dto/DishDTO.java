package hei.school.ingredient.dto;

public class DishDTO {
    private String name;
    private String category;
    private Double sellingprice;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getSellingPrice() { return sellingprice; }
    public void setSellingPrice(Double Sellingprice) { this.sellingprice = Sellingprice; }
}
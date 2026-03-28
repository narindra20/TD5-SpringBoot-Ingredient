package hei.school.ingredient.dto;

public class StockResponse {
    private String unit;
    private double value;

    public StockResponse(String unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }
}

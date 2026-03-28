package hei.school.ingredient.entity;

import java.time.Instant;

public class StockMovement {

    private Integer id;
    private Integer ingredientId;
    private Double quantity;
    private UnitEnum unit;
    private MovementTypeEnum type;
    private Instant datetime;

    public StockMovement() {}

    public StockMovement(Integer id, Integer ingredientId, Double quantity,
                         UnitEnum unit, MovementTypeEnum type, Instant datetime) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
        this.type = type;
        this.datetime = datetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public UnitEnum getUnit() {
        return unit;
    }

    public void setUnit(UnitEnum unit) {
        this.unit = unit;
    }

    public MovementTypeEnum getType() {
        return type;
    }

    public void setType(MovementTypeEnum type) {
        this.type = type;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public void setDateTime(Instant date) {
    }
}
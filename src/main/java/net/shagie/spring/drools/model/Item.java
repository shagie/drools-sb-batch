package net.shagie.spring.drools.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * Example from Drools Book.
 */
public class Item implements Serializable {

    public enum Category {
        NA, LOW_RANGE, MID_RANGE, HIGH_RANGE,
        SPECIAL_MIDHIGH_RANGE //used in chapter 4
    };
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Double cost;
    private Double salePrice;
    private Category category;

    public Item() {
    }

    public Item(Item copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.cost = copy.cost;
        this.salePrice = copy.salePrice;
        this.category = copy.category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name=" + name + ", cost=" + cost
                + ", salePrice=" + salePrice + ", category=" + category + '}';
    }
}
package com.mginda.domain.product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductItem {

    final private String title;
    final private String description;
    final private int kcal_per_100g;
    final private BigDecimal unit_price;

    public ProductItem(String title, String description, int kcal_per_100g, BigDecimal unit_price) {
        this.title = title;
        this.kcal_per_100g = kcal_per_100g;
        this.unit_price = unit_price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getKcal_per_100g() {
        return kcal_per_100g;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductItem)) return false;
        ProductItem that = (ProductItem) o;
        return kcal_per_100g == that.kcal_per_100g &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(unit_price, that.unit_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, kcal_per_100g, unit_price);
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "title='" + title +
                ", description='" + description  +
                ", kcal_per_100g=" + kcal_per_100g +
                ", unit_price=" + unit_price +
                '}';
    }
}

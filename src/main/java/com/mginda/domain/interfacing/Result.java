package com.mginda.domain.interfacing;

import com.mginda.domain.product.ProductItem;
import com.mginda.domain.product.ProductItemsSummary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Result {
    private List<ProductItem> results;
    private Total total;

    public Result(List<ProductItem> results) {
        this.results = results;
        this.total = new Total(results);

    }

    @Override
    public String toString() {
        return "Result{" +
                "results=" + results.toString() +
                ", total=" + total.toString() +
                '}';
    }

    public List<ProductItem> getResults() {
        return results;
    }

    public Total getTotal() {
        return total;
    }
}

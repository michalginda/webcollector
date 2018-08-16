package com.mginda.webcollector.interfacing;

import com.mginda.webcollector.domain.product.ProductItem;

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

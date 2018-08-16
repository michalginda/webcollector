package com.mginda.webcollector.interfacing;

import com.mginda.webcollector.domain.product.ProductItem;
import com.mginda.webcollector.domain.product.ProductItemsSummary;

import java.math.BigDecimal;
import java.util.List;

public class Total {
    private final BigDecimal gross;
    private final BigDecimal vat;

    public Total(List< ProductItem > results)
    {
        ProductItemsSummary summary = new ProductItemsSummary(results);
        this.gross = summary.getGross();
        this.vat = summary.getVat();
    }

    @Override
    public String toString() {
        return "Total{" +
                "gross=" + gross +
                ", vat=" + vat +
                '}';
    }

    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }
}

package com.mginda.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductItemsSummary
{
    public static BigDecimal TAX_RATE = BigDecimal.valueOf(20).divide(BigDecimal.valueOf(100));
    private List<ProductItem> products;
    private BigDecimal gross;
    private BigDecimal vat;

    public ProductItemsSummary(List<ProductItem> prods)
    {
        if (!productsAreValid(prods))
        {
            throw new IllegalArgumentException("Illegal collection of products passed.");
        }
        this.products = prods;
        this.gross = calculateTotal(this.products);
        this.vat = calculateGross(this.gross);
    }


    public List<ProductItem> getProducts()
    {
        return this.products;
    }

    public void setProducts(ArrayList<ProductItem> products)
    {
        this.products = products;
        this.gross = calculateTotal(this.products);
        this.vat = calculateGross(this.gross);
    }


    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

    private boolean productsAreValid(List<ProductItem> products)
    {
        if (products == null || (products != null & products.size() < 1))
        {
            return false;
        }
        return true;
    }

    private BigDecimal calculateTotal(List<ProductItem> products)
    {
        return products.stream()
                .map(ProductItem::getUnit_price)
                .map(price -> price.setScale(2, BigDecimal.ROUND_DOWN))
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
    }

    private BigDecimal calculateGross(BigDecimal total) {
        return total.multiply(TAX_RATE).setScale(2, BigDecimal.ROUND_DOWN);
    }


    @Override
    public String toString() {
        return "ProductItemsSummary{" +
                "products size=" + products.size() +
                ", gross=" + gross +
                ", vat=" + vat +
                '}';
    }
}

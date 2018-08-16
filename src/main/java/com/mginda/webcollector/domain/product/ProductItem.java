package com.mginda.webcollector.domain.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.mginda.WebCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonFilter("zeroValueFilter")
public class ProductItem
{

    public static final Logger LOGGER = LoggerFactory.getLogger(WebCollector.class);
    public static final int KCAL_NO_VALUE = 0;
    public static final BigDecimal PRICE_NO_VALUE = BigDecimal.ZERO;
    final private String title;
    final private String description;
    final private int kcal_per_100g;
    final private BigDecimal unit_price;

    public ProductItem(String title, String description, String kcal_per_100g, String unit_price)
    {
        int kcal = KCAL_NO_VALUE;
        BigDecimal price = PRICE_NO_VALUE;
        double converted=0;
        String clean ="";
        String clenkcal = "";
        int convKcal =0;
        this.title = title;
        try
        {
            //clenkcal = kcal_per_100g.replace("kcal", "");
            clenkcal = getCaloriesNumber(kcal_per_100g);
            convKcal = Integer.parseInt(clenkcal);
            kcal = convKcal;
        }
        catch (NumberFormatException nfe)
        {
            LOGGER.error("Improper value passed for ProductItem.kcal_per_100g = " + clenkcal);
        }
        this.kcal_per_100g = kcal;
        try
        {
            //clean = unit_price.replace("£", "").replace("/unit", "");
            clean = getPriceValue(unit_price);
            converted = Double.parseDouble(clean);
             price = BigDecimal
                    .valueOf(converted);
        }
        catch (NumberFormatException nfe)
        {
            LOGGER.error("Improper value passed for ProductItem.unit_price = " + clean + " " + converted);
        }
        this.unit_price = price;
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public int getKcal_per_100g()
    {
        return kcal_per_100g;
    }

    public BigDecimal getUnit_price()
    {
        return unit_price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof ProductItem))
            return false;
        ProductItem that = (ProductItem) o;
        return kcal_per_100g == that.kcal_per_100g && Objects.equals(title, that.title)
                && Objects.equals(description, that.description) && Objects.equals(unit_price, that.unit_price);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, description, kcal_per_100g, unit_price);
    }

    @Override
    public String toString()
    {
        return "ProductItem{" + "title='" + title + ", description='" + description + ", kcal_per_100g=" + kcal_per_100g
                + ", unit_price=" + unit_price + '}';
    }

    private String getCaloriesNumber(String originalValue)
    {
        Pattern pattern = Pattern.compile("([0-9]+)(kcal)?");
        Matcher matcher = pattern.matcher(originalValue);
        if (matcher.matches())
        {
            return matcher.group(1);
        }
        return originalValue;
    }

    private String getPriceValue(String originalValue)
    {
        Pattern pattern = Pattern.compile("(£)?([0-9]+.[0-9]{2})(/unit)?");
        Matcher matcher = pattern.matcher(originalValue);
        if (matcher.matches())
        {
            return matcher.group(2);
        }
        return originalValue;
    }
}

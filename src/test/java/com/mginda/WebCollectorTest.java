package com.mginda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mginda.domain.interfacing.Result;
import com.mginda.domain.product.ProductItem;
import com.mginda.domain.product.ProductItemsSummary;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebCollectorTest
{
    public static final Logger LOGGER = LoggerFactory.getLogger(WebCollectorTest.class);

    private static final List<ProductItem> PRODUCTS_FIXTURE = Arrays.asList(

            new ProductItem("Strawberries", "Juicy ones 100g", 100, BigDecimal.valueOf(1.00)),
            new ProductItem("Blueberries", "Tasty ones 200g", 100, BigDecimal.valueOf(2.50)),
            new ProductItem("Bananas", "Healthy ones 500g", 100, BigDecimal.valueOf(5.00)));

    @Test
    public void createDummyProduct()
    {
        LOGGER.info(new ProductItem("Strawberries", "Juicy ones 100g", 100, BigDecimal.valueOf(100)).toString());
    }

    @Test
    public void printFixtureStructure()
    {
        LOGGER.info(PRODUCTS_FIXTURE.toString());
    }

    @Test
    public void printAnalysis()
    {
        //Given
        ProductItemsSummary summary = new ProductItemsSummary(PRODUCTS_FIXTURE);
        //When

        //Then both numbers should be equal
        assertEquals(0, BigDecimal.valueOf(8.50).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getGross()));
        assertEquals(0, BigDecimal.valueOf(1.70).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getVat()));
    }

    @Test
    public void printAnalysisa()
    {
        ProductItemsSummary summary = new ProductItemsSummary(PRODUCTS_FIXTURE);
        //assertThat(BigDecimal.valueOf(8.50)), Matchers.comparesEqualTo(summary.getVat());
        //"Total amount should be correctly calculated", BigDecimal.valueOf(8.50).setScale(2, BigDecimal.ROUND_DOWN), summary.getVat());
        assertEquals(0, BigDecimal.valueOf(8.50).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getGross()));
        assertEquals(0, BigDecimal.valueOf(1.70).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getVat()));
    }

    @Test
    public void JSONcreated()
    {
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result(PRODUCTS_FIXTURE);
        try {
            LOGGER.info("JSON: " + mapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

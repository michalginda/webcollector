package com.mginda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mginda.webcollector.interfacing.Result;
import com.mginda.webcollector.domain.product.ProductItem;
import com.mginda.webcollector.domain.product.ProductItemsSummary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebCollectorTest
{
    public static final Logger LOGGER = LoggerFactory.getLogger(WebCollectorTest.class);
    private static ArrayList<ProductItem> products = new ArrayList<>();
    private static final WebCollector collector = new WebCollector();
    private static final List<ProductItem> PRODUCTS_FIXTURE = Arrays.asList(

            new ProductItem("Sainsbury's Strawberries 400g", "by Sainsbury's strawberries", "33", "1.75"),
            new ProductItem("Sainsbury's Blueberries 200g", "by Sainsbury's blueberries", "45", "1.75"),
            new ProductItem("Sainsbury's Raspberries 225g", "by Sainsbury's raspberries", "32", "1.75"),
            new ProductItem("Sainsbury's Blackberries, Sweet 150g", "by Sainsbury's blackberries", "32", "1.50"),
            new ProductItem("Sainsbury's Blueberries 400g", "by Sainsbury's blueberries", "45", "3.25"),
            new ProductItem("Sainsbury's Blueberries, SO Organic 150g", "So Organic blueberries", "45", "2.00"),
            new ProductItem("Sainsbury's Raspberries, Taste the Difference 150g", "Ttd raspberries", "32", "2.50"),
            new ProductItem("Sainsbury's Cherries 400g", "by Sainsbury's Family Cherry Punnet", "52", "2.50"),
            new ProductItem("Sainsbury's Blackberries, Tangy 150g", "by Sainsbury's blackberries", "32", "1.50"),
            new ProductItem("Sainsbury's Strawberries, Taste the Difference 300g", "Ttd strawberries", "33", "2.50"),
            new ProductItem("Sainsbury's Cherry Punnet 200g", "Cherries", "52", "1.50"),
            new ProductItem("Sainsbury's Mixed Berries 300g", "by Sainsbury's mixed berries", "0", "3.50"),
            new ProductItem("Sainsbury's Mixed Berry Twin Pack 200g", "Mixed Berries", "0", "2.75"),
            new ProductItem("Sainsbury's Redcurrants 150g", "by Sainsbury's redcurrants", "71", "2.50"),
            new ProductItem("Sainsbury's Cherry Punnet, Taste the Difference 200g", "Cherry Punnet", "48", "2.50"),
            new ProductItem("Sainsbury's Blackcurrants 150g", "Union Flag", "0", "1.75"),
            new ProductItem("Sainsbury's British Cherry & Strawberry Pack 600g",
                    "British Cherry & Strawberry Mixed Pack", "0", "4.00"));

    @BeforeAll
    public static void initTestData()
    {
        products = collector.collect();
    }

    @Test
    public void createDummyProduct()
    {
        LOGGER.info(new ProductItem("Strawberries", "Juicy ones 100g", "100", "100").toString());
    }

    @Test
    public void printFixtureStructure()
    {
        LOGGER.info(PRODUCTS_FIXTURE.toString());
    }

    @Test
    public void printAnalysis()
    {
        // Given
        ProductItemsSummary summary = new ProductItemsSummary(PRODUCTS_FIXTURE);
        // When

        // Then both numbers should be equal
        assertEquals(0, BigDecimal.valueOf(39.50).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getGross()));
        assertEquals(0, BigDecimal.valueOf(7.90).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getVat()));
    }

    @Test
    public void printAnalysisa()
    {
        ProductItemsSummary summary = new ProductItemsSummary(PRODUCTS_FIXTURE);
        // assertThat(BigDecimal.valueOf(8.50)), Matchers.comparesEqualTo(summary.getVat());
        // "Total amount should be correctly calculated", BigDecimal.valueOf(8.50).setScale(2,
        // BigDecimal.ROUND_DOWN), summary.getVat());
        assertEquals(0, BigDecimal.valueOf(39.50).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getGross()));
        assertEquals(0, BigDecimal.valueOf(7.90).setScale(2, BigDecimal.ROUND_DOWN).compareTo(summary.getVat()));
    }

    @Test
    public void JSONcreated()
    {
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result(PRODUCTS_FIXTURE);
        try
        {
            LOGGER.info("JSON: " + mapper.writeValueAsString(result));
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void compareTitle()
    {

        for (int index = 0; index < products.size() && index < PRODUCTS_FIXTURE.size(); index++)
        {
            assertEquals(PRODUCTS_FIXTURE.get(index).getTitle(), products.get(index).getTitle());
        }
    }

    @Test
    public void compareDescription()
    {

        for (int index = 0; index < products.size() && index < PRODUCTS_FIXTURE.size(); index++)
        {
            assertEquals(PRODUCTS_FIXTURE.get(index).getDescription(), products.get(index).getDescription());
        }
    }

    @Test
    public void comparePrice()
    {

        for (int index = 0; index < products.size() && index < PRODUCTS_FIXTURE.size(); index++)
        {
//            String pattern = "£1.75/unit";
//            String clean = pattern.replace("£", "").replace("/unit", "");
//            double converted = Double.parseDouble(clean);
//             BigDecimal price = BigDecimal
//                    .valueOf(converted);
            LOGGER.info("PRICE: "+ PRODUCTS_FIXTURE.get(index).getUnit_price() + " & " + products.get(index).getUnit_price());
//            LOGGER.info("Original text = " + pattern);
//            LOGGER.info("Clean text = " + clean);
//            LOGGER.info("Converted text = " + converted);
            assertEquals(PRODUCTS_FIXTURE.get(index).getUnit_price(), products.get(index).getUnit_price());
        }
    }

    @Test
    public void compareKcals()
    {

        for (int index = 0; index < products.size() && index < PRODUCTS_FIXTURE.size(); index++)
        {
            assertEquals(Integer.parseInt(String.valueOf(PRODUCTS_FIXTURE.get(index).getKcal_per_100g())),
                    Integer.parseInt(String.valueOf(products.get(index).getKcal_per_100g())));
        }

    }

    @Test
    public void compareAll()
    {
        assertEquals(PRODUCTS_FIXTURE, collector.collect());
        collector.getResult(products);
    }


}

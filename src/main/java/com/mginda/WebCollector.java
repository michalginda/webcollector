package com.mginda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mginda.webcollector.domain.product.ProductItem;
import com.mginda.webcollector.domain.web.WebDataMiner;
import com.mginda.webcollector.interfacing.Result;
import com.mginda.webcollector.interfacing.ZeroValueFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.util.ArrayList;

public class WebCollector
{

    public static final String baseURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    public static final Logger LOGGER = LoggerFactory.getLogger(WebCollector.class);

    public static void main(String[] args)
    {
        WebCollector collector = new WebCollector();
        collector.collect();
    }

    public ArrayList<ProductItem> collect()
    {
        LOGGER.info("WebCollector booting up...");
        ArrayList<ProductItem> products = new ArrayList<>();
        // Fetch all products URLs
        try
        {
            Document mainPage = Jsoup.connect(baseURL).get();
            //LOGGER.info(mainPage.select(".product").toString());
            Elements webproducts = mainPage.select(".product");
            WebDataMiner miner = new WebDataMiner(baseURL);


            for (Element webproduct : webproducts)
            {

                String webproduct_url = miner.getProductURL(webproduct.selectFirst("a").attr("href"));
                LOGGER.info("product url: " + webproduct_url);
                Document productSite = Jsoup.connect(webproduct_url).get();
                String title = productSite.selectFirst(".productTitleDescriptionContainer").text();
                String desription = miner.mineDescription(productSite);
                String kcal = miner.mineNutrition(productSite);
                String price = productSite.selectFirst(".pricePerUnit").text();
                ProductItem currentProduct = new ProductItem(productSite.selectFirst(".productTitleDescriptionContainer").text(),
                        miner.mineDescription(productSite),
                        miner.mineNutrition(productSite),
                        productSite.selectFirst(".pricePerUnit").text());
                LOGGER.info(currentProduct.toString());
                products.add(currentProduct);
            }
            LOGGER.info("The total collection of: " + products.size());
        }
        catch (IOException e)
        {
            LOGGER.error("Problem occurred while downloading a document", e);
        }
        // For each product
        // Visit it's dedicated URL
        // Collect needed data and populate related object field
        // Return results in JSON object
        LOGGER.info("Processing finished");
        return products;

    }

    public String getResult(ArrayList<ProductItem> products)
    {
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("zeroValueFilter", new ZeroValueFilter());
        mapper.setFilterProvider(filterProvider);
        Result result = new Result(products);
        try
        {
            LOGGER.info("JSON: " + mapper.writeValueAsString(result));
            return mapper.writeValueAsString(result);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return "oops";

    }
}

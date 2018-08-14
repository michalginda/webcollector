package com.mginda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WebCollector {

    public static final String baseURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    public static final Logger LOGGER = LoggerFactory.getLogger( WebCollector.class);

    public static void main(String[] args) {
        LOGGER.info("WebCollector booting up...");
        //Fetch all products URLs
        try {
            Document mainPage = Jsoup.connect(baseURL).get();
            LOGGER.info(mainPage.select(".product").toString());
        } catch (IOException e) {
            LOGGER.error("Problem occured while downloading a document", e);
        }
        //For each product
        // Visit it's dedicated URL
        // Collect needed data and populate related object field
        //Return results in JSON object
        LOGGER.info("Processing finished");
    }
}

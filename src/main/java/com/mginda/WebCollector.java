package com.mginda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebCollector {
    public static final Logger LOGGER = LoggerFactory.getLogger( WebCollector.class);

    public static void main(String[] args) {
        LOGGER.info("WebCollector booting up...");
        //Fetch all products URLs
        //For each product
        // Visit it's dedicated URL
        // Collect needed data and populate related object field
        //Return results in JSON object
        LOGGER.info("Processing finished");
    }
}

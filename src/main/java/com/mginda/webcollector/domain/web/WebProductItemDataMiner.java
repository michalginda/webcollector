package com.mginda.webcollector.domain.web;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.mginda.webcollector.domain.product.ProductItem;

public class WebProductItemDataMiner
{
    private static final String URL_PATH_SEGMENT_SEPARATOR = "/";
    private String baseURL;

    public WebProductItemDataMiner(String url)
    {
        if (!isValidURL(url))
        {
            throw new IllegalArgumentException("Passed url value is invalid: " + url);
        }
        this.baseURL = url;
    }

    public String getProductURL(String relativeURL)
    {
        return normalizeURL(this.baseURL) + relativeURL;
    }

    private String normalizeURL(String url)

    {
        if (!isValidURL(url))
        {
            throw new IllegalArgumentException("Passed url value is invalid: " + url);
        }

        if (url.endsWith(URL_PATH_SEGMENT_SEPARATOR))
        {
            return url;
        }
        else
        {
            return url.substring(0, url.lastIndexOf(URL_PATH_SEGMENT_SEPARATOR) + 1);
        }
    }

    private boolean isValidURL(String url)
    {
        if (url != null)
        {
            if (url.contains(URL_PATH_SEGMENT_SEPARATOR)
                    & (url.lastIndexOf(URL_PATH_SEGMENT_SEPARATOR) > 7))
            {
                return true;
            }
        }
        return false;
    }

    public String mineNutrition(Document productDocument)
    {
        // Element baseTable = productDocument.selectFirst("table.nutritionTable > tbody > tr.tableRow0");
        Element resultValue;// = baseTable;
        if ((resultValue = productDocument.selectFirst("table.nutritionTable > tbody > tr.tableRow0 > td")) != null)
        {
            return resultValue.text();

            /*
             * if ((resultValue = baseTable.selectFirst("td.tableRow0")) != null) { return resultValue.text(); }
             */
            /*
             * else if ((resultValue = baseTable.selectFirst("td.nutritionLevel1")) != null) { return
             * resultValue.text(); }
             */
            /*
             * else if ((resultValue = baseTable.selectFirst("tr.tableRow0:nth-child(2) > td:nth-child(1)")) !=
             * null) { return resultValue.text(); }
             */
            /* else *//*
                       * if ((resultValue = baseTable.selectFirst("td")) != null) { return resultValue.text(); } else {
                       * return "not-implemented-yet"; }
                       */
        }
        // else if ((resultValue = productDocument.selectFirst(".nutritionTable > tbody:nth-child(2) >
        // tr:nth-child(2) > td:nth-child(2)")) != null)
        // else if ((resultValue = productDocument.selectFirst(".nutritionTable > tbody > tr:nth-child(2) >
        // td:nth-child(2)")) != null)
        else if ((resultValue = productDocument
                .selectFirst(".nutritionTable > tbody > tr:nth-child(2) > td:nth-child(2)")) != null)
        {
            return resultValue.text();
        }

        return String.valueOf(ProductItem.KCAL_NO_VALUE);
    }

    public String mineDescription(Document productDocument)
    {
        Element baseDescription = productDocument.selectFirst("div.productText");
        Element deeper;
        Element oneLine;
        if (baseDescription != null)
        {
            if ((deeper = baseDescription.selectFirst("div.memo")) != null)
            {
                if ((oneLine = deeper.selectFirst("p")) != null)
                    return oneLine.text();
            }
            else if ((deeper = baseDescription.selectFirst("div.itemTypeGroup")) != null)
            {
                if ((oneLine = deeper.selectFirst("p:nth-child(2)")) != null)
                    return oneLine.text();
            }
            else if ((oneLine = baseDescription.selectFirst("p")) != null)
                return oneLine.text();
            else

                return baseDescription.text();
        }
        else if ((baseDescription = productDocument
                .selectFirst("div.itemTypeGroupContainer.productText > div.itemTypeGroup")) != null)
        {
            if ((oneLine = baseDescription.selectFirst("p")) != null)
                return oneLine.text();
        }
        return "not-implemented-yet";
    }
}

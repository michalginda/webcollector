package com.mginda.domain.web;

public class WebDataMiner {
    private static final String URL_PATH_SEPARATOR = "/";

    private String getProductURL(String baseURL)
    {
        return "";
    }

    private String convertRelativeURLtoAbsolute(String relativeURL)
    {
        if(!validURL(relativeURL))
        {
            throw new IllegalArgumentException("Passed URL is not valid");
        }
        return  relativeURL.substring(0, relativeURL.lastIndexOf(URL_PATH_SEPARATOR) + 1);
    }

    private boolean validURL(String relativeURL) {
        if (relativeURL != null & relativeURL.length() > 0 & relativeURL.contains(URL_PATH_SEPARATOR) & (relativeURL.lastIndexOf(URL_PATH_SEPARATOR) > 7)) {
            return true;
        }
        else {
            return false;
        }
    }
}

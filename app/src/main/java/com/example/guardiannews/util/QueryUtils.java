package com.example.guardiannews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.guardiannews.models.Article;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * class to manage extracting data from json to ones usable in java
 */
public final class QueryUtils {


    private QueryUtils() {

    }

    private static final String startUrl = "https://content.guardianapis.com/search";

    /**
     * parses json and returns list of 'Articles'
     * @param inputUrl: url of json response
     */
    public static List<Article> getArticles(String inputUrl) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        //used to associate Article with 'thumbnail' string to convert the string to a bitmap before setting Article field
        Map<Article,String> imageAssocation = new HashMap<>();

        try {
            String fullUrl = startUrl+inputUrl;
            URL url = new URL(fullUrl);

            JsonNode rootNode = mapper.readTree(url);
            JsonNode responseNode = rootNode.path("response");
            JsonNode resultsNode = responseNode.path("results");

            Iterator<JsonNode> iterator = resultsNode.elements();

            while(iterator.hasNext()) {
                JsonNode node = iterator.next();
                JsonNode fieldNode = node.path("fields");
                if (fieldNode.get("thumbnail") != null) {
                    String thumbnail = fieldNode.get("thumbnail").toString();
                    Article article = mapper.treeToValue(node, Article.class);
                    imageAssocation.put(article, thumbnail);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Article> articles = mapImagesToBitmap(imageAssocation);
        return articles;
    }

    /**
     * converts String of url to 'URL' object
     * @param urlString: String of url
     * @return URL object
     */
    private static URL createUrlObject(String urlString) {
        URL url;
        try {
            String newUrlString = urlString.replace("\"", "");
            url = new URL(newUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    /**
     * Converts url 'String' to 'Bitmap' format
     * @param urlString: url of image in 'String' form
     * @return Bitmap object of image
     */
    private static Bitmap toBitmap(String urlString) {
        if (urlString == null) {
            return null;
        }

        URL url = null;
        urlString = urlString.replace("\"", "");
        Bitmap image = null;

        try {
            url = new URL(urlString);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * converts a map of {Article and associated String of image} to
     * list {of Articles} with the Bitmap image
     * @return
     */

    private static List<Article> mapImagesToBitmap(Map<Article,String> articleMap) {
        List<Article> bitmapArticles = new ArrayList<>();
        for (Map.Entry<Article,String> entry : articleMap.entrySet()) {
            Article article = entry.getKey();
            Bitmap bitmap = toBitmap(entry.getValue());
            if (bitmap == null) {
                Log.v(article.getWebTitle(), "BITMAP IS NULL FOR THIS ARTICLE");
            }
            article.setImage(bitmap);
            bitmapArticles.add(article);
        }
        return bitmapArticles;
    }

}

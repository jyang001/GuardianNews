package com.example.guardiannews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
     * parses json and returns the Articles
     * @param inputUrl: url of json response
     */
    public static List<Article> getArticles(String inputUrl) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        List<Article> articles = new ArrayList<Article>();

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
                String thumbnail = fieldNode.get("thumbnail").toString();
                Article article = mapper.treeToValue(node, Article.class);
                imageAssocation.put(article,thumbnail);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    /**
     * converts String of url to 'URL' object
     * @param urlString: String of url
     * @return URL object
     */
    private URL createUrlObject(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    /**
     * Converts url 'String' to 'Bitmap' format
     * @param url String of 'url' of image
     * @return Bitmap object of image
     */
    private Bitmap toBitmap(String url) {
        if (url == null) {
            return null;
        }
        URL link = createUrlObject(url);
        try {
            assert link != null;
            return BitmapFactory.decodeStream(link.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * converts a map of {Article and associated String of image} to
     * list {of Articles} with the Bitmap image
     * @return
     */
    private List<Article> mapImagesToBitmap(Map<Article,String> articleMap) {
        List<Article> bitmapArticles = new ArrayList<>();
        for (Map.Entry<Article,String> entry : articleMap.entrySet()) {
            Article article = entry.getKey();
            Bitmap bitmap = toBitmap(entry.getValue());
            Log.d("BITMAP COUNT IS: ", Integer.toString(bitmap.getByteCount()));
            article.setImage(bitmap);
            bitmapArticles.add(article);
        }
        return bitmapArticles;
    }

}

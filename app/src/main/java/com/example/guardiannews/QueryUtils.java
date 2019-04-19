package com.example.guardiannews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class QueryUtils {

    private QueryUtils() {

    }

    private static final String startUrl = "https://content.guardianapis.com/search";

    /**
     * parses json and returns the Articles
     * @param inputUrl url of json response
     */
    public static List<Article> getArticles(String inputUrl) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        List<Article> articles = new ArrayList<Article>();

        try {
            String fullUrl = startUrl+inputUrl;
            URL url = new URL(fullUrl);

            final JsonNode rootNode = mapper.readTree(url);
            JsonNode responseNode = rootNode.path("response");
            JsonNode resultsNode = responseNode.path("results");

            Iterator<JsonNode> iterator = resultsNode.elements();

            while(iterator.hasNext()) {
                JsonNode node = iterator.next();
                Article article = mapper.treeToValue(node, Article.class);
                articles.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;

//        for(Article article: articles) {
//            System.out.println(article);
//        }
    }

    /**
     * converts String of url to 'URL' object
     * @param urlString: String of url
     * @return URL object
     */
    public static URL createUrl(String urlString) {
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
    private static Bitmap getImage(String url) {
        if (url == null) {
            return null;
        }
        URL link = createUrl(url);
        try {
            assert link != null;
            return BitmapFactory.decodeStream(link.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

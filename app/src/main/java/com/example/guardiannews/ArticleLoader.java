package com.example.guardiannews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Class to load the List{of articles} by running network request on a thread
 */
public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ArticleLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     *
     * @param context
     * @param url: url to retrieve data from
     */
    ArticleLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: Article onStartLoading called");
    }

    @Nullable
    @Override
    public List<Article> loadInBackground() {

        if(mUrl == null) {
            return null;
        }
        List<Article> articles = QueryUtils.getArticles(mUrl);
        return articles;
    }

}

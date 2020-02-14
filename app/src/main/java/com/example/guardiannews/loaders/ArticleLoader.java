package com.example.guardiannews.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.guardiannews.models.Article;
import com.example.guardiannews.util.QueryUtils;

import java.util.List;

/**
 * Class to load the List{of articles} by running network request on a thread
 */
public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ArticleLoader.class.getName();

    /** Bundle for tags **/
    private Bundle bundle;

    /**
     *
     * @param context
     * @param bundle: achieve url params
     */
    public ArticleLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: Article onStartLoading called");
    }

    @Nullable
    @Override
    public List<Article> loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground called");
        if(bundle == null) {
            return null;
        }
        List<Article> articles = QueryUtils.getArticles(bundle.getString("uri"));
        return articles;
    }

}

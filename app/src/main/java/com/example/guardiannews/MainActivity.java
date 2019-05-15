package com.example.guardiannews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final int LOADER_ID = 1;

    /** Guardian API Key **/
    private final String GUARDIAN_NEWS_URL = "?api-key=c7771d54-6420-45bf-b2c9-75182b3f2479&show-fields=thumbnail";

    private ArticleAdapter articleAdapter;

    /** displays message if article list is empty **/
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ONCREATE: ","STARTING APP" );

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(checkConnection(this) == true) {
            ListView articleListView = (ListView) findViewById(R.id.frontpage);
            articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
            articleListView.setAdapter(articleAdapter);
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        }

        else {
            emptyTextView = (TextView) findViewById(R.id.connectionCheck);
            emptyTextView.setText(R.string.no_connection);
        }
    }

    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.d("check LOADER: ","LOADER CREATED" );
        return new ArticleLoader(this, GUARDIAN_NEWS_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> articles) {
        articleAdapter.clear();
        if(articles != null && !articles.isEmpty()) {
            articleAdapter.addAll(articles);
        }

        if (articleAdapter.getCount() == 0) {
            emptyTextView.setText(R.string.no_earthquakes_found);
        }
    }

    /**
     * remove reference to data when loader is reset (data is unavailable)
     * @param loader: loader object
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        articleAdapter.clear();
    }

    /**
     * boolean method to determine if device is connected
     * to the internet
     */
    public boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

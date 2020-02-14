package com.example.guardiannews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.guardiannews.adapters.ArticleRecyclerAdapter;
import com.example.guardiannews.loaders.ArticleLoader;
import com.example.guardiannews.models.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>, ArticleRecyclerAdapter.OnArticleListener {

    /** Guardian API Key **/
    private final String GUARDIAN_NEWS_URL = "?api-key=c7771d54-6420-45bf-b2c9-75182b3f2479&show-fields=thumbnail";

    /** displays message if article list is empty **/
    private TextView emptyTextView;

    /** displays message if connection works **/
    private TextView connectionTextView;

    private ArrayList<Article> mArticles = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private ArticleRecyclerAdapter articleRecyclerAdapter;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkConnection(this) == true) {
            mRecyclerView = findViewById(R.id.recyclerView);
            initRecylerView();
            Bundle bundle = new Bundle();
            bundle.putString("uri", GUARDIAN_NEWS_URL);
            LoaderManager.getInstance(this).initLoader(1, bundle, this).forceLoad();
        }
        else {
            connectionTextView = findViewById(R.id.connectionCheck);
            connectionTextView.setText(R.string.no_connection);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.articles_toolbar));
        setTitle("Guardian News");
    }

    /**
     * creates the toolbar menu
     * @param menu: menu object
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newsOption:
                Bundle args = new Bundle();
                loadNewQuery("/tags?section=news" + GUARDIAN_NEWS_URL);
                return true;
            default:
                return true;
        }
    }

    private void loadNewQuery(String query) {
        Bundle args = new Bundle();
        args.putString("uri", query);
        LoaderManager.getInstance(this).restartLoader(1,args,this);
    }


    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.d("check LOADER: ","LOADER CREATED" );
        return new ArticleLoader(this, bundle);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> articles) {
        if(articles != null && !articles.isEmpty()) {
            articleRecyclerAdapter.refreshLayout(articles);
            articleRecyclerAdapter.notifyDataSetChanged();
        }
        else if (articleRecyclerAdapter.getItemCount() == 0) {
            emptyTextView = findViewById(R.id.articles_found);
            emptyTextView.setText(R.string.no_articles_found);
        }
    }

    /**
     * remove reference to data when loader is reset (data is unavailable)
     * @param loader: loader object
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        loader.reset();
    }

    /**
     * boolean method to determine if device is connected to the internet
     */
    public boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * instantiates a new recyclerView linked with the adapter
     */
    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        articleRecyclerAdapter = new ArticleRecyclerAdapter(mArticles, this);
        mRecyclerView.setAdapter(articleRecyclerAdapter);
    }

    @Override
    public void onArticleClick(int position, Article article) {
        Log.d("NOTE CLICK:", article.getWebUrl());
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getWebUrl()));
        startActivity(browserIntent);
    }

}

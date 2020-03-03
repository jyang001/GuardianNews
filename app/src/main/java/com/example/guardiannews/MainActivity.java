package com.example.guardiannews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.guardiannews.adapters.ArticleRecyclerAdapter;
import com.example.guardiannews.loaders.ArticleLoader;
import com.example.guardiannews.models.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>, ArticleRecyclerAdapter.OnArticleListener, NavigationView.OnNavigationItemSelectedListener {

    /** Guardian API Key **/
    private final String GUARDIAN_NEWS_URL = ""; //api key here inside " "

    private ArrayList<Article> mArticles = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private ArticleRecyclerAdapter articleRecyclerAdapter;

    private Bundle bundle;

    private DrawerLayout drawerLayout;

    private String colorTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* displays message if connection works */
        TextView connectionTextView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.articles_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Guardian News");

        colorTheme = "Original Theme";

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(checkConnection(this)) {
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
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * creates the toolbar menu
     * @param menu: menu object
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.theme_1:
                colorTheme = "Standard Theme";
                articleRecyclerAdapter.setColorTheme(colorTheme);
                articleRecyclerAdapter.notifyDataSetChanged();
                return true;
            case R.id.theme_2:
                colorTheme = "Original Theme";
                articleRecyclerAdapter.setColorTheme(colorTheme);
                articleRecyclerAdapter.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

    /**
     * method for DrawerLayout selection
     * @param menuItem: selection that was pressed
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.news_option:
                loadNewQuery("section=news" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.world_option:
                loadNewQuery("section=world" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sports_option:
                loadNewQuery("section=sport" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.culture_option:
                loadNewQuery("section=culture" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.technology_option:
                loadNewQuery("section=technology" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.fashion_option:
                loadNewQuery("section=fashion" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.science_option:
                loadNewQuery("section=science" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.travel_option:
                loadNewQuery("section=travel" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.business_option:
                loadNewQuery("section=business" + GUARDIAN_NEWS_URL);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return true;
        }
    }

    private void loadNewQuery(String query) {
        Bundle args = new Bundle();
        args.putString("uri", query);
        LoaderManager.getInstance(this).restartLoader(1,args,this).forceLoad();
        mRecyclerView.smoothScrollToPosition(0);
    }

    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int i, @Nullable Bundle args) {
        return new ArticleLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> articles) {
        /* displays message if article list is empty **/
        TextView emptyTextView;

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
        articleRecyclerAdapter = new ArticleRecyclerAdapter(mArticles, this, colorTheme, this.getApplicationContext());
        mRecyclerView.setAdapter(articleRecyclerAdapter);
    }

    @Override
    public void onArticleClick(int position, Article article) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getWebUrl()));
        startActivity(browserIntent);
    }

}

package com.example.guardiannews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LOADER_ID = 1;

    /** Guardian API Key **/
    final String GUARDIAN_NEWS_URL = "?api-key=c7771d54-6420-45bf-b2c9-75182b3f2479&show-fields=thumbnail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ONCREATE: ","STARTING APP" );

        List<Article> articles = QueryUtils.getArticles(GUARDIAN_NEWS_URL);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

package com.example.ivan.fetchdata;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    private TextView tvIndex;
    private TextView tvTitle;
    private Button bStart;
    private boolean isConnected = false;
    private ConnectivityManager connectivityManager;

    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getLoaderManager().initLoader(LOADER_ID,null,this);

    }

    private void init() {
        tvIndex = (TextView) findViewById(R.id.indexStr);
        tvTitle = (TextView) findViewById(R.id.titleStr);
        bStart = (Button) findViewById(R.id.bStart);


    }


    private boolean connected() {
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnected();
        return isConnected;
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this,"https://en.wikipedia.org/api/rest_v1/page/related/Main_Page");
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        String index = data.getTitle();
        String title = data.getImdbRating();

        tvIndex.setText(index);
        tvTitle.setText(title);
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {


    }
}

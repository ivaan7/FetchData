package com.example.ivan.fetchdata;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.*;
import android.support.v4.app.LoaderManager;


/**
 * Created by Ivan on 3.7.2017..
 */

public class DataLoader extends AsyncTaskLoader<Movie> {

    private String mUrl;
    public DataLoader(Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Movie loadInBackground() {

        if (mUrl==null)
            return null;

        Movie movie = FetchData.getMovie(mUrl);
        return movie;
    }
}

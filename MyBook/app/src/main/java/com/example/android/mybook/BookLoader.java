package com.example.android.mybook;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Anil on 2017-06-21.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    //LOG_TAG
    private static String LOG_TAG = BookLoader.class.getSimpleName();

    //private variable url;
    private String url = "";

    public BookLoader(Context context, String Url) {
        super(context);
        url = Url;
    }

    @Override
    public List<Book> loadInBackground() {
        if(url == null){
            return null;
        }

        List<Book> books = QueryUtils.fetchBookList(url);
        return books;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

package com.example.android.mybook;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BookList extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<List<Book>> {

    /**Private LOG_TAG for Log massage     */
    private static String LOG_TAG = BookList.class.getSimpleName();

    /**Query url for book     */
    private static String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";

    //private adapter
    private static BookAdapter mBookAdapter;

    /*Constant Loadermanager id */
    private static int BookListId = 1;

    /*Search key*/
    private static  String search;

    /*Text view when Empty list view*/
    private TextView EmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //gettting search key
        Intent intent = getIntent();
        search = intent.getStringExtra("SearchKey");

        //Retrive gridView
        ListView listView = (ListView) findViewById(R.id.Grid_View);

        //set emptytextview
        EmptyTextView = (TextView)findViewById(R.id.Error_text);
        listView.setEmptyView(EmptyTextView);

        //set adapter
        mBookAdapter = new BookAdapter(BookList.this, new ArrayList<Book>());
        listView.setAdapter(mBookAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book books = mBookAdapter.getItem(i);

                Uri bookuri = Uri.parse(books.getUrl());
                Intent website = new Intent(Intent.ACTION_VIEW, bookuri);
                startActivity(website);
            }
        });

        //intialise imageLoader and used in cached
        DisplayImageOptions display_image = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(display_image)
                .build();
        ImageLoader.getInstance().init(config);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = cm.getActiveNetworkInfo();

        if( activenetwork != null && activenetwork.isConnected()){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BookListId, null, this);
        }else {
            //hiding Loading
            ProgressBar mProgressbar = (ProgressBar) findViewById(R.id.progress);
            mProgressbar.setVisibility(View.GONE);

            //Put NO internet connection massage
            EmptyTextView.setText(R.string.No_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        //making http requet
        Uri baseuri = Uri.parse(BOOK_REQUEST_URL);
        Uri.Builder uriBuilder = baseuri.buildUpon();
        //https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10
        uriBuilder.appendQueryParameter("q",search);

        return new BookLoader(BookList.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //progressbar setting
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        //set empty text on empty list
        EmptyTextView.setText(R.string.No_Data_Found);

        // Clear the adapter of previous earthquake data
        mBookAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }
}

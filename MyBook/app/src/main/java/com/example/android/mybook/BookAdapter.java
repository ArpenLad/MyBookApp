package com.example.android.mybook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Anil on 2017-06-21.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * public constructor
     * @param context
     * @param book
     */
    public BookAdapter(Context context, List<Book> book) {
        super(context, 0, book);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridview = convertView;
        //checking for using grid view
        if(gridview == null){
            gridview = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }

        Book books = getItem(position);

        //set book name
        TextView bookName = (TextView) gridview.findViewById(R.id.book_name);
        bookName.setText(books.getBook_Title());

        //set Image
        ImageView imageView = (ImageView) gridview.findViewById(R.id.book_image);
        ImageLoader.getInstance().displayImage(books.getBook_image(), imageView); // Default options will be used

        //set Auther
        TextView auther = (TextView) gridview.findViewById(R.id.book_auther);
        auther.setText(books.getAuther());

        //get Publisher
        TextView publisher = (TextView) gridview.findViewById(R.id.book_publisher);
        publisher.setText(books.getPublisher());

        return gridview;
    }
}

package com.example.gogos.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gogos on 2017-11-30.
 */

public class BookListAdaptor extends ArrayAdapter<BookList> {

    public BookListAdaptor(Context context, List<BookList> bookLists){
        super(context,0,bookLists);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.book_list_items, parent, false);
        }
        BookList currentBookList = getItem(position);
        TextView bookTittleTextView = (TextView) listItemView.findViewById(R.id.book_tittle);
        bookTittleTextView.setText(currentBookList.getBookTittle());
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
        authorTextView.setText(currentBookList.getBookAuthor());
        TextView publishedDataTextView = (TextView) listItemView.findViewById(R.id.published_date);
        publishedDataTextView.setText(currentBookList.getPublishedDate());
        return listItemView;
    }
}

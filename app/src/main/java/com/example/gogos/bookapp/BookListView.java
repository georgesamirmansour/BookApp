package com.example.gogos.bookapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListView extends AppCompatActivity {


    public static final String BookListRequestUrl =
            "https://www.googleapis.com/books/v1/volumes?q=" + MainActivity.userSearch + "&maxResults=15";

    ArrayList<BookList> bookListArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_items);
        BookListAsyncTask bookListAsyncTask = new BookListAsyncTask();
        bookListAsyncTask.execute(BookListRequestUrl);
    }

    private class BookListAsyncTask extends AsyncTask<String, Void, ArrayList<BookList>> {

        @Override
        protected ArrayList<BookList> doInBackground(String... strings) {
            bookListArrayList = QueryUtils.extractBookLists(strings[0]);
            return bookListArrayList;
        }

        protected void onPostExecute(ArrayList<BookList> bookLists) {
            super.onPostExecute(bookLists);
            BookListAdaptor bookListAdaptor = new BookListAdaptor(getApplicationContext(), bookListArrayList);
            ListView bookListView = findViewById(R.id.list);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            bookListView.setAdapter(bookListAdaptor);
            if (bookListView.getAdapter() == null) {
                TextView textView = findViewById(R.id.empty_text);
                textView.setVisibility(View.VISIBLE);
            }

        }

    }
}

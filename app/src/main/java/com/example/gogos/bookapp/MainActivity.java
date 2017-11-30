package com.example.gogos.bookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFiledEditText(v);
                Intent intent = new Intent(MainActivity.this, BookListView.class);
                startActivity(intent);
            }
        });
    }

    public void searchFiledEditText(View view) {
        EditText searchFiled = (EditText) findViewById(R.id.search);
        BookListView.userSearch = searchFiled.getText().toString();
    }
}

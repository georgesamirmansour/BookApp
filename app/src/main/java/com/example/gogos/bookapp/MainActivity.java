package com.example.gogos.bookapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static String userSearch = "";
    BookList bookList;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfConnectedToInternet();
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfConnectedToInternet()) {

                    Toast.makeText(MainActivity.this, getString(R.string.connection) +
                            checkIfConnectedToInternet(), Toast.LENGTH_SHORT).show();
                    searchFiledEditText(v);
                    Intent intent = new Intent(MainActivity.this, BookListView.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_internet,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void searchFiledEditText(View view) {
        EditText searchFiled = findViewById(R.id.search);
        userSearch = searchFiled.getText().toString();
    }

    private boolean checkIfConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo
                != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }
}

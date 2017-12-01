package com.example.gogos.bookapp;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by gogos on 2017-11-30.
 */

public final class QueryUtils {

    public static final String TAG = QueryUtils.class.getSimpleName();
    public static final ArrayList<BookList> bookListArrayList = new ArrayList<>();
    private static final int readTimeout = 10000;
    private static final int connectTimeout = 15000;
    private static final String requestMethod = "GET";
    private static final int responseCode = 200;
    private static View view;

    private QueryUtils() {
    }

    private static URL createUrl(String urls) {
        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            Log.e(TAG, "error with creating urls", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String newLine = bufferedReader.readLine();
            while (newLine != null) {
                stringBuilder.append(newLine);
                newLine = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = " ";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(readTimeout);
            connection.setConnectTimeout(connectTimeout);
            connection.setRequestMethod(requestMethod);
            connection.connect();
            if (connection.getResponseCode() == responseCode) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "problem retrieving book JSON results : ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String fetchBookListData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "error closing input stream: ", e);
        }
        return jsonResponse;
    }

    public static ArrayList<BookList> extractBookLists(String bookListJSON) {
        if (TextUtils.isEmpty(bookListJSON)) {
            return null;
        }

        String bookTittle;
        String bookAuthor;
        String publishedData;
        try {
            String jsonResult = fetchBookListData(bookListJSON);
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.optJSONArray("items");
            if (jsonObject.has("items")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject bookListFeatures = jsonArray.getJSONObject(i);
                    JSONObject volumeInfo = bookListFeatures.getJSONObject("volumeInfo");
                    if (volumeInfo.has("title")) {
                        bookTittle = volumeInfo.getString("title");
                    } else {
                        bookTittle = "No Title Found";
                    }
                    if (volumeInfo.has("authors")) {
                        bookAuthor = volumeInfo.getString("authors");
                    } else {
                        bookAuthor = "No Author Found";
                    }
                    if (volumeInfo.has("publishedDate")) {
                        publishedData = volumeInfo.getString("publishedDate");
                    } else {
                        publishedData = "No Publish Date Found";
                    }
                    bookListArrayList.add(new BookList(bookTittle, bookAuthor, publishedData));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "problem in parsing the book list json result: ", e);
        }
        return bookListArrayList;
    }
}

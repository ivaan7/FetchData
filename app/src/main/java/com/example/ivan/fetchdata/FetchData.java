package com.example.ivan.fetchdata;

import android.util.Log;

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

/**
 * Created by Ivan on 3.7.2017..
 */

public class FetchData {

    public static Movie getMovie(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Movie movie = extractMovie(jsonResponse);

        return movie;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Log", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Log", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();

            }
        }
        return jsonResponse;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("LOG", "Error with creating URL ", e);
        }
        return url;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static Movie extractMovie(String jsonResponse) {
/*
        {
            "pages": [
            {
                "pageid": 14072,
                    "ns": 0,
                    "index": 5,
                    "title": "History_of_Wikipedia",
                    "displaytitle": "History of Wikipedia",
                    "extract": "Wikipedia began with its launch on 15 January 2001, two days after the domain was registered by Jimmy Wales and Larry Sanger. Its technological and conceptual underpinnings predate this; the earliest known proposal for an online encyclopedia was made by Rick Gates in 1993, but the concept of a free-as-in-freedom online encyclopedia (as distinct from mere open source) was proposed by Richard Stallman in December 2000.\nCrucially, Stallman's concept specifically included the idea that no central organization should control editing. This characteristic was in stark contrast to contemporary digital encyclopedias such as Microsoft Encarta, Encyclopædia Britannica, and even Bomis's Nupedia, which was Wikipedia's direct predecessor. In 2001, the license for Nupedia was changed to GFDL, and Wales and Sanger launched Wikipedia using the concept and technology of a wiki pioneered in 1995 by Ward Cunningham. Initially, Wikipedia was intended to complement Nupedia, an online encyclopedia project edited solely by experts, by providing additional draft articles and ideas for it.",
                    "extract_html": "<p>Wikipedia began with its launch on 15 January 2001, two days after the domain was registered by Jimmy Wales and Larry Sanger. Its technological and conceptual underpinnings predate this; the earliest known proposal for an online encyclopedia was made by Rick Gates in 1993, but the concept of a free-as-in-freedom online encyclopedia (as distinct from mere open source) was proposed by Richard Stallman in December 2000.</p>\n<p>Crucially, Stallman's concept specifically included the idea that no central organization should control editing. This characteristic was in stark contrast to contemporary digital encyclopedias such as Microsoft Encarta, <i>Encyclopædia Britannica</i>, and even Bomis's Nupedia, which was Wikipedia's direct predecessor. In 2001, the license for Nupedia was changed to GFDL, and Wales and Sanger launched Wikipedia using the concept and technology of a wiki pioneered in 1995 by Ward Cunningham. Initially, Wikipedia was intended to complement Nupedia, an online encyclopedia project edited solely by experts, by providing additional draft articles and ideas for it.</p>",
                    "thumbnail": {
                "source": "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/EnwikipediaArt.PNG/320px-EnwikipediaArt.PNG",
                        "width": 320,
                        "height": 229,
                        "original": "http://upload.wikimedia.org/wikipedia/commons/2/26/EnwikipediaArt.PNG"
            },*/

        String title = "";
        String index = "";
        String original = "";
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray currentPage = baseJsonResponse.getJSONArray("pages");
            JSONObject page = currentPage.getJSONObject(0);

            title = page.getString("title");

            JSONObject thumbnail = page.getJSONObject("thumbnail");
            original = thumbnail.getString("width");
            index = page.getString("index");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Movie(title, original);
    }

}

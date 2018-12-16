package com.mynotes.mynotes;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

public class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    public Activity activity;
    GetUrlContentTask(Activity _activity) {
        this.activity = _activity;
    }
    protected String doInBackground(String... urls) {
        URL url = null;
        String content = "", line;
        try {
            url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            if (connection.getResponseCode() != 200) {
                throw new Exception();
            }
            return content;
        }
         catch (Exception e) {
            e.printStackTrace();
             return null;
        }

    }

    protected void onPostExecute(String result) {
        try {
            Random random = new Random();
            int index = random.nextInt(100);
            JSONArray res = new JSONArray(result);
            TextView randomNote = (TextView) this.activity.findViewById(R.id.randomNote);
            randomNote.setText(res.getJSONObject(index).getString("body"));
        } catch (Exception e) {
            e.printStackTrace();
            GetRandomNoteActivity.backgroundToast(activity, activity.getResources().getString(R.string.toast_error));
        }
    }


}
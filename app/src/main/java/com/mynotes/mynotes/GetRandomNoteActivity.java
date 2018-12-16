package com.mynotes.mynotes;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRandomNoteActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.mynotes.mynotes.MESSAGE";
    public static void backgroundToast(final Context context,
                                       final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_random_note);
    }
    public void getNote(View view) {
            new GetUrlContentTask(this).execute("https://jsonplaceholder.typicode.com/posts");
    }
    public void saveNote(View view) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        TextView note = (TextView) findViewById(R.id.randomNote);
        String message = note.getText().toString();
        if (!message.isEmpty()) {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.empty_note_warning),
                    Toast.LENGTH_SHORT).show();
        }
    }
}

package com.mynotes.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.mynotes.mynotes.MESSAGE";
    public static final String LOG_TAG = "AddNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Intent intent = getIntent();
        if(intent != null) {
            String note = intent.getStringExtra(DisplayNoteActivity.EXTRA_NOTE);
            EditText editText = (EditText) findViewById(R.id.editText);
            editText.setText(note);
        }
        Log.i(LOG_TAG, "Creating AddNoteActivity");
    }

    public void saveNote(View view) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        if (!message.isEmpty()) {
            DisplayNoteActivity.deleteNoteFromPref(message);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.empty_note_warning),
                    Toast.LENGTH_SHORT).show();
        }
        Log.i(LOG_TAG, "Saving note");
    }
}

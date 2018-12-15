package com.mynotes.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.mynotes.mynotes.MESSAGE";

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
    }
    public void saveNote(View view) {
        Intent intent = new Intent(this, DisplayNoteActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}

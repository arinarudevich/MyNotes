package com.mynotes.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayNoteActivity extends AppCompatActivity {
    private ListView lv;
    private Set<String> notesSet;
    private ArrayList<String> notes = new ArrayList<>();
    public static final String SHARED_PREFS_FILE = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        Intent intent = getIntent();
        String message = intent.getStringExtra(AddNoteActivity.EXTRA_MESSAGE);
        lv = (ListView) findViewById(R.id.listView);
        notes = this.getNotesFromPref();
        notes.add(0, message);
        this.saveNotesToPref(notes);
        System.out.println(notes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                notes);

        lv.setAdapter(arrayAdapter);
    }

    public void saveNotesToPref(ArrayList<String> currentNotes) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("Notes", ObjectSerializer.serialize(currentNotes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public ArrayList<String> getNotesFromPref() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        try {
            notes = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("Notes",
                    ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return notes;
        }
    }

}

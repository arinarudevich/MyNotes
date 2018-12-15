package com.mynotes.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayNoteActivity extends AppCompatActivity {
    public ListView lv;
    private ArrayList<String> notes = new ArrayList<>();
    public static final String SHARED_PREFS_FILE = "MyNotes";
    public static SharedPreferences prefs;
    public static final String EXTRA_NOTE = "com.mynotes.mynotes.NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        Intent intent = getIntent();
        String message = intent.getStringExtra(AddNoteActivity.EXTRA_MESSAGE);
        lv = (ListView) findViewById(R.id.listView);
        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        notes = this.getNotesFromPref();
        notes.add(0, message);
        this.saveNotesToPref(notes);
        CustomAdapter myAdapter = new CustomAdapter(this, notes, lv);

        lv.setAdapter(myAdapter);

    }

    public static void saveNotesToPref(ArrayList<String> currentNotes) {
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("Notes", ObjectSerializer.serialize(currentNotes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public ArrayList<String> getNotesFromPref() {
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
    public void editNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);
        View parentRow = (View) view.getParent().getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        String message = notes.get(position);
        notes.remove(message); //TODO: add func to delete from pref
        DisplayNoteActivity.saveNotesToPref(notes);
        intent.putExtra(EXTRA_NOTE, message);
        startActivity(intent);
    }
}

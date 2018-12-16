package com.mynotes.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayNoteActivity extends AppCompatActivity {
    public ListView lv;
    private ArrayList<String> notes = new ArrayList<>();
    public static final String SHARED_PREFS_FILE = "MyNotes";
    public static SharedPreferences prefs;
    public static final String EXTRA_NOTE = "com.mynotes.mynotes.NOTE";
    public static final String LOG_TAG = "DisplayNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        Intent intent = getIntent();
        lv = (ListView) findViewById(R.id.listView);
        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        notes = this.getNotesFromPref();
        String message = intent.getStringExtra(AddNoteActivity.EXTRA_MESSAGE);

        if(prefs.getString("WasDestroyed", "false") != "true"){
            if(message != null){
                notes.add(0, message);
            }
        }

        this.saveNotesToPref(notes);
        CustomAdapter myAdapter = new CustomAdapter(this, notes, lv);
        lv.setAdapter(myAdapter);
        setDestroyFlag("WasDestroyed", "false");
        Log.i(LOG_TAG, "Creating DisplayNoteActivity");
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

    public static ArrayList<String> getNotesFromPref() {
        ArrayList<String> notes = new ArrayList<>();
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
    public static void deleteNoteFromPref(String message){
        ArrayList<String> notes =  DisplayNoteActivity.getNotesFromPref();
        notes.remove(message);
        DisplayNoteActivity.saveNotesToPref(notes);
    }
    public void editNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        String message = notes.get(position);
        DisplayNoteActivity.saveNotesToPref(notes);
        intent.putExtra(EXTRA_NOTE, message);
        startActivity(intent);
        Log.i("DisplayNoteActivity", "Editing note");
    }
    public void addNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, "");
        startActivity(intent);
        Log.i(LOG_TAG, "Go to AddNoteActivity");
    }

    public void addRandomNote(View view) {
        Intent intent = new Intent(this, GetRandomNoteActivity.class);
        startActivity(intent);
        Log.i(LOG_TAG, "Go to GetRandomNoteActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setDestroyFlag("WasDestroyed","true");
    }
    public static void setDestroyFlag(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
}

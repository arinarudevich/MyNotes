package com.mynotes.mynotes;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<String> objects;
    ListView listView;

    CustomAdapter(Context context, ArrayList<String> notes, ListView lv) {
        ctx = context;
        listView = lv;
        objects = notes;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.note, parent, false);
        }

        final String myNote = getNote(position);


        ((TextView) view.findViewById(R.id.note)).setText(myNote);

        ImageButton delete = (ImageButton) view.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Do you really want to delete this note :O?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                objects.remove(myNote);
                                DisplayNoteActivity.saveNotesToPref(objects);
                                listView.invalidateViews();
                                Toast.makeText(ctx, "Note was deleted :(", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                }
        });

        return view;
    }

    String getNote(int position) {
        return (String) getItem(position);
    }
}

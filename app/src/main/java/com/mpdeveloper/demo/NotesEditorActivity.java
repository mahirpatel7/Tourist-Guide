package com.mpdeveloper.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NotesEditorActivity extends AppCompatActivity {

    int notesID;

    EditText notesTitle, notesDescription;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        notesTitle = findViewById(R.id.notesTitle);
        notesDescription = findViewById(R.id.notesDescription);

        Intent intent = getIntent();
        notesID = intent.getIntExtra("notesID", -1);
        if(notesID != -1) {
            notesTitle.setText(NotesActivity.notes.get(notesID));
            notesDescription.setText(NotesActivity.notes.get(notesID));
        }
        else {
            NotesActivity.notes.add("");
            notesID = NotesActivity.notes.size()-1;
            NotesActivity.arrayAdapter.notifyDataSetChanged();
        }
        notesTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(notesID,String.valueOf(charSequence));
                NotesActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.mpdeveloper.demo", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(NotesActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        notesDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(notesID,String.valueOf(charSequence));
                NotesActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.mpdeveloper.demo", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(NotesActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
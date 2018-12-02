package com.example.stefan.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the intent and retrieve the journal entry that was passed along
        Intent intent = getIntent();
        JournalEntry journalEntry = (JournalEntry) intent.getSerializableExtra("entry");

        // Variables for the views in the detail activity
        TextView title = findViewById(R.id.textViewTitle);
        TextView timestamp = findViewById(R.id.textViewTimestamp);
        TextView mood = findViewById(R.id.textViewMood);
        TextView content = findViewById(R.id.textViewContent);

        // Set the values of the views based on the values of the journal entry
        title.setText(journalEntry.getTitle());
        timestamp.setText(journalEntry.getTimestamp());
        mood.setText(journalEntry.getMood());
        content.setText(journalEntry.getContent());
    }
}

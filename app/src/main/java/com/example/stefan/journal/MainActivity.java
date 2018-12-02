package com.example.stefan.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import static com.example.stefan.journal.EntryDatabase.COLUMN_CONTENT;
import static com.example.stefan.journal.EntryDatabase.COLUMN_ID;
import static com.example.stefan.journal.EntryDatabase.COLUMN_MOOD;
import static com.example.stefan.journal.EntryDatabase.COLUMN_TIMESTAMP;
import static com.example.stefan.journal.EntryDatabase.COLUMN_TITLE;

public class MainActivity extends AppCompatActivity {

    // Private variables
    private EntryDatabase db;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the database
        db = EntryDatabase.getInstance(getApplicationContext());

        // Set the adapter
        adapter = new EntryAdapter(this, db.selectAll());

        // Find the grid view and add the Adapter, OnItemClickListener and OnItemLongClickListener
        ListView listView = findViewById(R.id.listViewEntries);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener());
        listView.setOnItemLongClickListener(new OnItemLongClickListener());
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Retrieve the cursor that was clicked
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            // Get the info from the cursor
            String titleText = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String contentText = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
            String moodText = cursor.getString(cursor.getColumnIndex(COLUMN_MOOD));
            String timestampText = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));

            // Put the info in a JournalEntry so it can be passed easily
            JournalEntry journalEntry = new JournalEntry(titleText, contentText, moodText, timestampText);

            // Go to the DetailActivity with the JournalEntry
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("entry", journalEntry);
            startActivity(intent);
        }
    }

    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // Retrieve the cursor that was clicked
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            // Get the id for the database entry
            long column_id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

            // Remove the entry from the database
            db.removeEntry(column_id);

            // Update in interface so the deleted item also disappears
            updateData();

            return true;
        }
    }

    public void addEntry(View view) {
        // Open new activity where the entry can be created
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    private void updateData() {
        // Update the adapter
        adapter.swapCursor(db.selectAll());
    }
}

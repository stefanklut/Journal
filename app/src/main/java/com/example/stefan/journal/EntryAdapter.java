package com.example.stefan.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import static com.example.stefan.journal.EntryDatabase.COLUMN_MOOD;
import static com.example.stefan.journal.EntryDatabase.COLUMN_TIMESTAMP;
import static com.example.stefan.journal.EntryDatabase.COLUMN_TITLE;

public class EntryAdapter extends ResourceCursorAdapter {

    public EntryAdapter(Context context, Cursor cursor){
        super(context, R.layout.entry_row, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Retrieve the info from the cursor
        String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        String mood = cursor.getString(cursor.getColumnIndex(COLUMN_MOOD));
        String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));

        // Variables for the views in the entry row
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewTimestamp = view.findViewById(R.id.textViewTimestamp);
        TextView textViewMood = view.findViewById(R.id.textViewMood);
        ImageView imageViewMood = view.findViewById(R.id.imageViewMood);

        // Update the views with the info from the cursor
        textViewTitle.setText(title);
        textViewTimestamp.setText(timestamp);
        textViewMood.setText(mood);

        // Also set the image
        switch (mood) {
            case "Happy":
                imageViewMood.setImageResource(R.drawable.happy);
                break;
            case "Sad":
                imageViewMood.setImageResource(R.drawable.sad);
                break;
            case "Neutral":
                imageViewMood.setImageResource(R.drawable.neutral);
                break;
            case "Angry":
                imageViewMood.setImageResource(R.drawable.angry);
                break;
        }
    }
}

package com.example.stefan.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void submitEntry(View view) {
        // Get the edit text field and radio buttons for mood
        EditText inputTitle = findViewById(R.id.editTextTitle);
        EditText inputContent = findViewById(R.id.editTextContent);
        RadioGroup inputMood = findViewById(R.id.radioGroup);

        // Retrieve the text from the text fields
        String inputTitleText = inputTitle.getText().toString();
        String inputContentText = inputContent.getText().toString();

        // Retrieve the mood from the radio buttons
        String inputMoodText = "";

        switch (inputMood.getCheckedRadioButtonId()){
            case R.id.radioButton1:
                inputMoodText = "Happy";
                break;
            case R.id.radioButton2:
                inputMoodText = "Sad";
                break;
            case R.id.radioButton3:
                inputMoodText = "Neutral";
                break;
            case R.id.radioButton4:
                inputMoodText = "Angry";
                break;
        }


        // Display a toast if nothing was typed and return so that the values are not used
        if (inputTitleText.isEmpty() || inputContentText.isEmpty() || inputMoodText.isEmpty()) {
            String errorMessage;
            if (inputTitleText.isEmpty()) {
                errorMessage = "Please provide a <b>Title</b>";
            } else if (inputContentText.isEmpty()) {
                errorMessage = "Please fill in some <b>Content</b>";
            } else {
                errorMessage = "Please select a <b>Mood</b>";
            }
            Toast toast = Toast.makeText(this, Html.fromHtml(errorMessage,
                    Html.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            return;
        }

        // Create a new journal entry with the title, content and mood
        JournalEntry journalEntry = new JournalEntry(inputTitleText, inputContentText, inputMoodText);

        // Add the entry to the database
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        db.insertEntry(journalEntry);

        // Go back to the main activity
        Intent intent = new Intent(InputActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

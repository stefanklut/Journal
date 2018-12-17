package com.example.stefan.journal;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener());
        updateRadioGroup(radioGroup);
    }

    private class OnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // If the selected radio button is changed update what button is visible as clicked
            updateRadioGroup(group);
        }
    }

    private void updateRadioGroup(RadioGroup group) {
        // Get what button is checked
        int checkedRadioButtonId = group.getCheckedRadioButtonId();

        // Loop over all buttons in the radio button group
        for (int i = 0; i<group.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) group.getChildAt(i);

            if (radioButton.getId() == checkedRadioButtonId) {
                // If the button was the checked button set the foreground to a border
                radioButton.setForeground(ContextCompat.getDrawable(this, R.drawable.button_border));
            } else {
                // Otherwise set the foreground to null to remove the border
                radioButton.setForeground(null);
            }
        }
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

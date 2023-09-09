package com.example.notetaking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class add_note extends AppCompatActivity {
    private NoteDatabaseHelper dbHelper; // Initialize the database helper
    private Button del_btn, add_btn;
    private TextView head, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        dbHelper = new NoteDatabaseHelper(this); // Initialize the database helper

        del_btn = findViewById(R.id.del_btn);
        add_btn = findViewById(R.id.add_btn);
        head = findViewById(R.id.Heading);
        body = findViewById(R.id.Body);

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                head.setText(null);
                body.setText(null);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = head.getText().toString();
                String noteText = body.getText().toString();

                // Create a new item object
                item newItem = new item(title, noteText);

                // Create an intent to send the item back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newItem", newItem);
                setResult(RESULT_OK, resultIntent);

                // Insert the new note into the database
                long insertedId = dbHelper.insertNote(newItem);

                // Finish the activity
                finish();
            }
        });
    }
}

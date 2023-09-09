package com.example.notetaking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<item> items;
    MyAdapter myAdapter;
    List<Long> noteIds;
    NoteDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        dbHelper = new NoteDatabaseHelper(this);
        items = dbHelper.getAllNotes();
        noteIds = dbHelper.getAllNoteIds(); // Retrieve note IDs from the database

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(getApplicationContext(), items, noteIds, dbHelper);
        recyclerView.setAdapter(myAdapter);



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(myAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, add_note.class);
                startActivityForResult(intent, 1);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                // Retrieve the item object from the intent
                item newItem = (item) data.getSerializableExtra("newItem");

                // Add the new item to the list and update the RecyclerView
                items.add(newItem);
                myAdapter.notifyDataSetChanged();
            }
        }
}
}

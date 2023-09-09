package com.example.notetaking;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "notes";
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";

    // Define your database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text not null);";

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public long insertNote(item newItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newItem.getTitle());
        values.put(COLUMN_DESCRIPTION, newItem.getDescription());

        // Insert the new note into the database and get the inserted row's ID
        long insertedId = db.insert(TABLE_NOTES, null, values);
        db.close();
        return insertedId;
    }

    public void deleteNoteById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Long> getAllNoteIds() {
        List<Long> noteIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                noteIds.add(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return noteIds;
    }


    public List<item> getAllNotes() {
        List<item> notes = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

                item note = new item(title, description);
                notes.add(note);
            }

            cursor.close();
        }

        db.close();
        return notes;
    }
}


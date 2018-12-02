package com.example.stefan.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance;

    // Return the instance of the database
    public static EntryDatabase getInstance(Context context){
        if (instance == null){
            instance = new EntryDatabase(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Static final variables
    public static final String DATABASE_NAME = "entries";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_MOOD = "mood";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create database string
    public static final String DATABASE_CREATE_ENTRIES = "create table " + DATABASE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " string, " +
            COLUMN_CONTENT + " string, " +
            COLUMN_MOOD + " string, " +
            COLUMN_TIMESTAMP + " datetime default current_timestamp)";

    // Insert some test entries
    private static final String INSERT_ENTRIES = "insert into " + DATABASE_NAME + "(" +
            COLUMN_TITLE+", " + COLUMN_CONTENT +", " + COLUMN_MOOD +")" + " values (" +
            "'Bad day', 'i had a bad day', 'Sad'), ('Good day', 'i had a good day', 'Happy'), " +
            "('Meh day', 'meh', 'Ok')";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database and add some entries
        db.execSQL(DATABASE_CREATE_ENTRIES);
        db.execSQL(INSERT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete the old database and create a new one
        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);
    }

    public Cursor selectAll() {
        // Return all the entries as a cursor
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        return cursor;
    }

    public void insertEntry(JournalEntry journalEntry) {
        // Add an entry to the database from a journal entry
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, journalEntry.getTitle());
        values.put(COLUMN_CONTENT, journalEntry.getContent());
        values.put(COLUMN_MOOD, journalEntry.getMood());
        db.insert(DATABASE_NAME, null, values);
    }

    public void removeEntry(long id) {
        // Remove the entry from the database where the id matches
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + DATABASE_NAME + " where " + COLUMN_ID + " == " + id);
    }
}

package com.bgrummitt.notes.controller.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = DatabaseHelper.class.getSimpleName();

    private final static String TO_COMPLETE_TABLE_NAME = "NOTES_TO_COMPLETE";
    private final static String COMPLETED_TABLE_NAME = "NOTES_COMPLETED";
    public final static String SUBJECT_COLUMN_NAME = "SUBJECT";
    public final static String NOTE_COLUMN_NAME = "NOTE";
    public final static String DATE_COLUMN_NAME = "DATE_COMPLETED";

    private String DB_PATH;
    private Context mContext;
    private SQLiteDatabase mDataBase;

    public DatabaseHelper(Context context, String db_name) {
        super(context, db_name, null, 1);

        //Get the DB_PATH
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableNotesToComplete = "CREATE TABLE " + TO_COMPLETE_TABLE_NAME + "(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_COLUMN_NAME + " TEXT, " + NOTE_COLUMN_NAME + " TEXT)";
        String CreateTableNotesCompleted = "CREATE TABLE " + COMPLETED_TABLE_NAME + "(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT_COLUMN_NAME + " TEXT, " + NOTE_COLUMN_NAME + " TEXT, " + DATE_COLUMN_NAME + " DATE)";

        db.execSQL(CreateTableNotesCompleted);
        db.execSQL(CreateTableNotesToComplete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TO_COMPLETE_TABLE_NAME);

        onCreate(db);
    }

    public boolean addNoteToBeCompleted(String subject, String note){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SUBJECT_COLUMN_NAME, subject);
        cv.put(NOTE_COLUMN_NAME, note);

        long result = db.insert(TO_COMPLETE_TABLE_NAME, null, cv);

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public void moveNoteToCompleted(int id){

    }

    public Cursor getNotesToBeCompleted(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM %s";

        return db.rawQuery(String.format(query, TO_COMPLETE_TABLE_NAME), null);
    }

}

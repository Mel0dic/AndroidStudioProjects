package com.bgrummitt.notes.controller.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bgrummitt.notes.model.CompletedNote;
import com.bgrummitt.notes.model.Note;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = DatabaseHelper.class.getSimpleName();

    private final static String TO_COMPLETE_TABLE_NAME = "NOTES_TO_COMPLETE";
    private final static String COMPLETED_TABLE_NAME = "NOTES_COMPLETED";
    public final static String ID_COLUMN_NAME = "NOTE_ID";
    public final static String SUBJECT_COLUMN_NAME = "SUBJECT";
    public final static String NOTE_COLUMN_NAME = "NOTE";
    public final static String DATE_COLUMN_NAME = "DATE_COMPLETED";

    private Context mContext;
    private int toBeCompletedCurrentMaxID;
    private int completedCurrentMaxID;

    public DatabaseHelper(Context context, String db_name) {
        super(context, db_name, null, 1);

        this.mContext = context;

        //Check if to be completed db is empty
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM  %s";
        String selectIDs = "SELECT " + ID_COLUMN_NAME + " FROM %s ORDER BY " + ID_COLUMN_NAME + " DESC;";
        Cursor mCursor = db.rawQuery(String.format(count, TO_COMPLETE_TABLE_NAME), null);
        mCursor.moveToFirst();
        if(mCursor.getInt(0) > 0){
            // If the db is not empty select all columns order by largest first descending order
            Cursor tempCursor = db.rawQuery(String.format(selectIDs, TO_COMPLETE_TABLE_NAME), null);
            tempCursor.moveToFirst();
            // First item is the largest ID so set max id of off its id column
            toBeCompletedCurrentMaxID = tempCursor.getInt(0);
            tempCursor.close();
            mCursor.close();
        }else {
            // If empty set to 0
            toBeCompletedCurrentMaxID = 0;
        }

        //Check if completed db is empty
        mCursor = db.rawQuery(String.format(count, COMPLETED_TABLE_NAME), null);
        mCursor.moveToFirst();
        if(mCursor.getInt(0) > 0){
            // If the db is not empty select all columns order by largest first descending order
            Cursor tempCursor = db.rawQuery(String.format(selectIDs, COMPLETED_TABLE_NAME), null);
            tempCursor.moveToFirst();
            // First item is the largest ID so set max id of off its id column
            completedCurrentMaxID = tempCursor.getInt(0);
            Log.d(TAG, Integer.toString(toBeCompletedCurrentMaxID));
            tempCursor.close();
            mCursor.close();
        }else {
            completedCurrentMaxID = 0;
        }

    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableNotesToComplete = "CREATE TABLE " + TO_COMPLETE_TABLE_NAME + "(" + ID_COLUMN_NAME +" INTEGER PRIMARY KEY, " + SUBJECT_COLUMN_NAME + " TEXT, " + NOTE_COLUMN_NAME + " TEXT)";
        String CreateTableNotesCompleted = "CREATE TABLE " + COMPLETED_TABLE_NAME + "(" + ID_COLUMN_NAME +" INTEGER PRIMARY KEY, " + SUBJECT_COLUMN_NAME + " TEXT, " + NOTE_COLUMN_NAME + " TEXT, " + DATE_COLUMN_NAME + " DATE)";

        db.execSQL(CreateTableNotesCompleted);
        db.execSQL(CreateTableNotesToComplete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TO_COMPLETE_TABLE_NAME);

        onCreate(db);
    }

    /**
     * Add a note to the database of completed notes
     * @param db writable database
     * @param subject string of subject of note
     * @param note string of body of the note
     * @param ID int of id of new note
     * @return if completed successfully return true else false
     */
    public boolean addNoteToBeCompleted(SQLiteDatabase db, String subject, String note, int ID){
        ContentValues cv = new ContentValues();
        cv.put(ID_COLUMN_NAME, ID);
        toBeCompletedCurrentMaxID += 1;
        cv.put(SUBJECT_COLUMN_NAME, subject);
        cv.put(NOTE_COLUMN_NAME, note);

        long result = db.insert(TO_COMPLETE_TABLE_NAME, null, cv);

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    /**
     * Insert a note in a given position in the T.O.D.O db
     * @param db the db to be inserted to
     * @param note the note to be inserted
     * @param oldPosition the position being inserted into
     */
    public void insertNoteIntoTODO(SQLiteDatabase db, Note note, int oldPosition){
        changeDbIds(db, note.getDatabaseID() - 1, 1);

        addNoteToBeCompleted(db, note.getSubject(), note.getNoteBody(), note.getDatabaseID());
    }

    /**
     * Add a note to the database of completed notes
     * @param db writable database
     * @param note note to be written to db
     * @return if completed successfully return true else false
     */
    public boolean addCompletedNote(SQLiteDatabase db, CompletedNote note){
        ContentValues cv = new ContentValues();
        cv.put(ID_COLUMN_NAME, completedCurrentMaxID + 1);
        completedCurrentMaxID += 1;
        cv.put(SUBJECT_COLUMN_NAME, note.getSubject());
        cv.put(NOTE_COLUMN_NAME, note.getNoteBody());
        cv.put(DATE_COLUMN_NAME, note.convertDateToString(note.getDateNoteCompleted()));

        note.setDatabaseID(completedCurrentMaxID);

        long result = db.insert(COMPLETED_TABLE_NAME, null, cv);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Function to move a given note from the T.O.D.O db to the completed db
     * @param db to add and remove notes from
     * @param note to be removed from one and added to another
     */
    public void moveNoteToCompleted(SQLiteDatabase db, Note note){

        deleteNoteFromTODO(db, note.getDatabaseID());

        changeDbIds(db, note.getDatabaseID(), -1);

        Log.d(TAG, Boolean.toString(addCompletedNote(db, new CompletedNote(note, Calendar.getInstance().getTime()))));

        toBeCompletedCurrentMaxID -= 1;
    }

    /**
     * Function to change all ids > than id of given noteID by a given value
     * @param db writable datable
     * @param noteID id of the where all notes with > id's changed
     * @param changeByX the value to change the id's by
     */
    public void changeDbIds(SQLiteDatabase db, int noteID, int changeByX){
        String query = "SELECT * FROM " + TO_COMPLETE_TABLE_NAME + " WHERE " + ID_COLUMN_NAME  + " > " + noteID;

        Cursor mCursor = db.rawQuery(query, null);

        if(mCursor.moveToFirst()){
            int columnID = mCursor.getColumnIndex(ID_COLUMN_NAME);
            int posID;
            while(!mCursor.isAfterLast()){
                posID = mCursor.getInt(columnID);
                query = "UPDATE " + TO_COMPLETE_TABLE_NAME + " SET " + ID_COLUMN_NAME + " = " + (posID + changeByX) + " WHERE " + ID_COLUMN_NAME  + " = " + posID;
                db.execSQL(query);
                mCursor.moveToNext();
            }
        }
        mCursor.close();
    }

    /**
     * Delete the given note from the db of notes to be completed using the database id in the note
     * @param db db to be used for deletion (use getWritableDatabase)
     * @param dbID of the entry in the db to delete
     */
    public void deleteNoteFromTODO(SQLiteDatabase db, int dbID){
        boolean complete = db.delete(TO_COMPLETE_TABLE_NAME, ID_COLUMN_NAME + " = " + dbID, null) > 0;

        Log.d(TAG, String.format("ID deleting = %d, SUCCESS = %b", dbID, complete));
    }

    /**
     * Delete the given note from the db of completed notes using the database id in the note
     * @param db db to be used for deletion (use getWritableDatabase)
     * @param dbID of the entry in the db to delete
     */
    public void deleteCompletedNote(SQLiteDatabase db, int dbID){
        boolean complete = db.delete(TO_COMPLETE_TABLE_NAME, ID_COLUMN_NAME + " = " + dbID, null) > 0;

        Log.d(TAG, String.format("ID deleting = %d, SUCCESS = %b", dbID, complete));
    }

    private String tableQuery = "SELECT * FROM %s";

    /**
     * Get the Cursor of all the notes in the db of notes to be completed
     * Close db after use .close()
     * @param db A readable database to execute query
     * @return Cursor of the data
     */
    public Cursor getNotesToBeCompleted(SQLiteDatabase db){
        return db.rawQuery(String.format(tableQuery, TO_COMPLETE_TABLE_NAME), null);
    }

    /**
     * Get the Cursor of all the notes in the db of completed notes
     * Close db after use .close()
     * @param db A readable database to execute query
     * @return Cursor of the data
     */
    public Cursor getNotesCompleted(SQLiteDatabase db){
        return db.rawQuery(String.format(tableQuery, COMPLETED_TABLE_NAME), null);
    }

    public int getToBeCompletedCurrentMaxID(){
        return toBeCompletedCurrentMaxID;
    }

}

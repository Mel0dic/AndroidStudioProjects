package com.bgrummitt.compscisimpleapp.Other;

import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

public class TestAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    //Template sql query
    private final String mQueryNumberSql ="SELECT _id FROM QUESTIONS_TABLE WHERE QUESTION LIKE '%s'";
    private final String mQueryAnswerSql ="SELECT ANSWER FROM QUESTIONS_TABLE WHERE QUESTION LIKE '%s'";


    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    //Try to create a database and then return this class
    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    //Open the database and then close it to check if it is working and then get a readable database from it
    public TestAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    //Close the db
    public void close() {
        mDbHelper.close();
    }

    //Get the questions from the database
    public String[] getQuestions(){
        //Using an array list to easily add more questions
        List<String> stringArr = new ArrayList<String>();
        Cursor mCur;
        try{
            //Try selecting all questions from the database
            String sql = "SELECT QUESTION FROM QUESTIONS_TABLE";

            mCur = mDb.rawQuery(sql, null);

            if(mCur != null){
                mCur.moveToNext();
            }
        }catch (SQLException mSQLException){
            Log.e(TAG, "ERROR GETTING QUESTIONS === " + mSQLException.toString());
            throw mSQLException;
        }

        //For all the questions in the Database
        for(int i = 0; i < getQuestionsCount(); i++){
            Log.d(TAG, "Question " + i + " = " + mCur.getString(0));
            //Get the question at column 0 and add it to the list
            stringArr.add(mCur.getString(0));
            //Move to the next row
            mCur.moveToNext();
        }

        Log.d(TAG, stringArr.size() + "");

        //Turn the List into an Array
        String[] tempArr = new String[stringArr.size()];
        tempArr = stringArr.toArray(tempArr);

        return tempArr;
    }

    //Get the number of entries into the database
    public long getQuestionsCount(){
        long count = DatabaseUtils.queryNumEntries(mDb, "QUESTIONS_TABLE");
        return count;
    }

    //Giving the question get the number in the database
    public int getNumber(String question){
        try {
            Cursor mCur = mDb.rawQuery(String.format(mQueryNumberSql, question), null);
            if (mCur!=null) {
                mCur.moveToNext();
            }
            return mCur.getInt(0);
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    //Given the question get the Answer from the database
    public String getAnswer(String question){
        try {
            //Query the database with the formatted sql query
            Cursor mCur = mDb.rawQuery(String.format(mQueryAnswerSql, question), null);
            if (mCur!=null) {
                mCur.moveToNext();
            }
            //Return the string at position 0
            return mCur.getString(0);
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

}
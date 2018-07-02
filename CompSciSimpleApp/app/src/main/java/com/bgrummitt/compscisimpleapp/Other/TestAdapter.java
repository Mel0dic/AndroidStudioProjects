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

    private final String mQueryNumberSql ="SELECT _id FROM QUESTIONS_TABLE WHERE QUESTION LIKE '%s'";
    private final String mQueryAnswerSql ="SELECT ANSWER FROM QUESTIONS_TABLE WHERE QUESTION LIKE '%s'";

    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

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

    public void close() {
        mDbHelper.close();
    }

    public String[] getQuestions(){
        List<String> stringArr = new ArrayList<String>();
        Cursor mCur;
        try{
            String sql = "SELECT QUESTION FROM QUESTIONS_TABLE";

            mCur = mDb.rawQuery(sql, null);

            if(mCur != null){
                mCur.moveToNext();
            }
        }catch (SQLException mSQLException){
            Log.e(TAG, "ERROR GETTING QUESTIONS === " + mSQLException.toString());
            throw mSQLException;
        }

        for(int i = 0; i < getQuestionsCount(); i++){
            Log.d(TAG, "Question " + i + " = " + mCur.getString(0));
            stringArr.add(mCur.getString(0));
            mCur.moveToNext();
        }

        Log.d(TAG, stringArr.size() + "");

        String[] tempArr = new String[stringArr.size()];
        tempArr = stringArr.toArray(tempArr);

        return tempArr;
    }

    public long getQuestionsCount(){
        long count = DatabaseUtils.queryNumEntries(mDb, "QUESTIONS_TABLE");
        return count;
    }

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

    public String getAnswer(String question){
        try {
            Cursor mCur = mDb.rawQuery(String.format(mQueryAnswerSql, question), null);
            if (mCur!=null) {
                mCur.moveToNext();
            }
            return mCur.getString(0);
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

}
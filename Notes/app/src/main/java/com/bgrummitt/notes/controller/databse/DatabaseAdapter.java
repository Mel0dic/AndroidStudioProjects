package com.bgrummitt.notes.controller.databse;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {

//    private static final String TAG = DatabaseAdapter.class.getSimpleName();
//
//    private final Context mContext;
//    private String mDBName;
//    private String mDBFileName;
//    private SQLiteDatabase mDb;
//    private DatabaseHelper mDbHelper;
//
//    public DatabaseAdapter(Context context, String DBFile, String DBName){
//        mContext = context;
//        mDBName = DBName;
//        mDBFileName = String.format("%s.db", DBFile);
//        mDbHelper = new DatabaseHelper(mContext, mDBFileName);
//    }
//
//    public DatabaseAdapter createDatabase() throws SQLException {
//        try {
//            mDbHelper.createDataBase();
//        } catch (Error error) {
//            Log.e(TAG, error.toString() + "  UnableToCreateDatabase");
//            throw new Error("UnableToCreateDatabase");
//        }
//        return this;
//    }
//
//    public DatabaseAdapter open() throws SQLException {
//        try {
//            // Open then close to check if it is working properly then retrieve the readable db
//            mDbHelper.openDatabase();
//            mDbHelper.close();
//            mDb = mDbHelper.getReadableDatabase();
//        } catch (SQLException mSQLException) {
//            Log.e(TAG, "open >>"+ mSQLException.toString());
//            throw mSQLException;
//        }
//        return this;
//    }
//
//    public void close(){
//        mDb.close();
//    }
//
//    public SQLiteDatabase getDb() {
//        return mDb;
//    }

}

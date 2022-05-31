package com.example.androidstudio_homework2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public Cursor getUserByDayOfSQL(int year, int month) {
        String date= year + "/" +month;
        String sql= String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                UserContract.Users.TABLE_NAME,
                UserContract.Users.KEY_SHOW_DAY,
                date);
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public void insertUserBySQL(String show_day, String title, String start_hour, String finish_hour,
                                String address, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) " +
                            "VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_SHOW_DAY,
                    UserContract.Users.KEY_TITLE,
                    UserContract.Users.KEY_START_HOUR,
                    UserContract.Users.KEY_FINISH_HOUR,
                    UserContract.Users.KEY_ADDRESS,
                    UserContract.Users.KEY_MEMO,
                    show_day,
                    title,
                    start_hour,
                    finish_hour,
                    address,
                    memo);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getUserByTitleOfSQL(String title) {
        String sql= String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                UserContract.Users.TABLE_NAME,
                UserContract.Users.KEY_TITLE,
                title);
        return getReadableDatabase().rawQuery(sql,null);
    }

    public void deleteUserBySQL(String title) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = '%s'",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users.KEY_TITLE,
                    title);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateUserBySQL(String show_day, String title, String start_hour, String finish_hour,
                                String address, String memo) {
        try {
            String sql = String.format (
                    "UPDATE  %s SET %s = '%s', %s = '%s' WHERE %s = '%s'",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_SHOW_DAY, show_day,
                    UserContract.Users.KEY_TITLE, title,
                    UserContract.Users.KEY_START_HOUR, start_hour,
                    UserContract.Users.KEY_FINISH_HOUR, finish_hour,
                    UserContract.Users.KEY_ADDRESS, address,
                    UserContract.Users.KEY_MEMO, memo) ;
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in updating recodes");
        }
    }

}
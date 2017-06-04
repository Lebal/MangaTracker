package com.tracker.filip.mangatracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Filip on 2017-06-01.
 */

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION =5;
    private static final String DATABASE_NAME="entries.db";
    public static final String TABLE_ENTRIES ="entries";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_NAME ="name";
    public static final String COLUMN_CHAPTER ="chapter";
    public static final String COLUMN_PICTURE ="picture";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_ENTRIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CHAPTER + " INTEGER, " +
                COLUMN_PICTURE + " STRING "
                +");";

        SQLiteStatement stmt = db.compileStatement(query);
        stmt.execute();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_ENTRIES;
        SQLiteStatement stmt = db.compileStatement(query);
        stmt.execute();
        onCreate(db);
    }


    public long addEntry(MangaEntry entry){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO " + TABLE_ENTRIES + " ("+COLUMN_NAME+","+COLUMN_CHAPTER+","+COLUMN_PICTURE+") VALUES (?,?,?);";
        SQLiteStatement stmt = db.compileStatement(query);

        stmt.bindString(1,entry.getName());
        stmt.bindLong(2,entry.getChapter());
        stmt.bindString(3,entry.getPicture());

        long rowID = stmt.executeInsert();
        return rowID;

    }

    public int removeEntry(MangaEntry entry){

        SQLiteDatabase db = getWritableDatabase();
        String query =
                "DELETE FROM " + TABLE_ENTRIES +
                " WHERE " +
                COLUMN_NAME + " = ? AND " + COLUMN_CHAPTER +"=? AND " + COLUMN_PICTURE + "=?;";

        SQLiteStatement stmt = db.compileStatement(query);
        stmt.bindString(1,entry.getName());
        stmt.bindLong(2,entry.getChapter());
        stmt.bindString(3,entry.getPicture());

        int numberOfRowsAffected = stmt.executeUpdateDelete();

        db.close();
        stmt.close();

        return numberOfRowsAffected;

    }

    public int updateEntry(MangaEntry oldEntry, MangaEntry newEntry){

        SQLiteDatabase db = getWritableDatabase();

        String query = "UPDATE " + TABLE_ENTRIES +
                " SET " + COLUMN_NAME + " =?, "  +COLUMN_CHAPTER + "=?, " + COLUMN_PICTURE +
                "=? WHERE "
                +  COLUMN_NAME + " =? AND " + COLUMN_CHAPTER + "=? AND " + COLUMN_PICTURE + "=?;";

        SQLiteStatement stmt = db.compileStatement(query);

        stmt.bindString(1,newEntry.getName());;
        stmt.bindLong(2,newEntry.getChapter());
        stmt.bindString(3,newEntry.getPicture());

        stmt.bindString(4,oldEntry.getName());;
        stmt.bindLong(5,oldEntry.getChapter());
        stmt.bindString(6,oldEntry.getPicture());

        int numberOfRowsAffected = stmt.executeUpdateDelete();

        db.close();
        stmt.close();
        return numberOfRowsAffected;
    }


    public List<MangaEntry> databaseToList(){
        List<MangaEntry> list= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ENTRIES + " WHERE 1";


        Cursor c = db.rawQuery(query,null);
        //c.moveToFirst();

        while (!c.isAfterLast()){

            if (c.moveToNext()){
                list.add(new MangaEntry(c.getString(c.getColumnIndex("name")), c.getInt(c.getColumnIndex("chapter")),c.getString(c.getColumnIndex("picture")) ));

            }

        }
        c.close();
        db.close();
        return list;
    }

}

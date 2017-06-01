package com.tracker.filip.mangatracker;

import android.content.Entity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Filip on 2017-06-01.
 */

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION =1;
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
                COLUMN_PICTURE + " INTEGER "
                +");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }


    public void addEntry(MangaEntry entry){

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME,entry.getName());
        values.put(COLUMN_CHAPTER,entry.getChapter());
        values.put(COLUMN_PICTURE,entry.getPicture());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ENTRIES,null,values);
        db.close();
    }

    public void removeEntry(MangaEntry entry){

        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_ENTRIES + " WHERE " + COLUMN_NAME + " =\"" +entry.getName() + "\"" + " AND " + COLUMN_CHAPTER +"=\"" + entry.getChapter() + "\"" +" AND "
                + COLUMN_PICTURE + "=\"" + entry.getPicture() + "\";";
        db.execSQL(query);

    }


    public List<MangaEntry> databaseToList(){
        List<MangaEntry> list= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ENTRIES + " WHERE 1";


        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            if (c.moveToNext()){
                list.add(new MangaEntry(c.getString(c.getColumnIndex("name")), c.getInt(c.getColumnIndex("chapter")),c.getInt(c.getColumnIndex("picture")) ));

            }

        }

        db.close();
        return list;
    }

}

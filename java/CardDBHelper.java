package com.example.janus.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardDBHelper extends SQLiteOpenHelper {
    public CardDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE today2(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "received TEXT, "                 + "contents TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF exists today2;");
        onCreate(db);
    }
}

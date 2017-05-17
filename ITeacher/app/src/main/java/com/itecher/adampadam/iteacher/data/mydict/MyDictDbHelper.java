package com.itecher.adampadam.iteacher.data.mydict;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itecher.adampadam.iteacher.ProfileActivity;
import com.itecher.adampadam.iteacher.adapter.Word;

import java.util.ArrayList;

public class MyDictDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_main_dict.db";
    private static final int DATABASE_VERSION = 3;

    public MyDictDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void add_word_in_db(SQLiteDatabase db, Word w) {

        ContentValues newValues = new ContentValues();

        newValues.put(MyDictContract.MyMainDict._ID, w.getid());
        newValues.put(MyDictContract.MyMainDict.ENG_NAME, w.getEng_word());
        newValues.put(MyDictContract.MyMainDict.ENG_ABBREV, w.getEng_abbrev());
        newValues.put(MyDictContract.MyMainDict.RUS_NAME, w.getRus_word());
        newValues.put(MyDictContract.MyMainDict.RUS_ABBREV, w.getRus_abbrev());
        newValues.put(MyDictContract.MyMainDict.GROUP_NAME, w.getGroup());
        newValues.put(MyDictContract.MyMainDict.LEARN, w.getLevel());

        db.insert(MyDictContract.MyMainDict.TABLE_NAME, null, newValues);

    }

    public void del_word_from_db(SQLiteDatabase db, Word w) {

        db.delete(MyDictContract.MyMainDict.TABLE_NAME, "id = ?", new String[]{String.valueOf(w.getid())});

    }

    public void update_word_from_db(SQLiteDatabase db, Word w) {

        ContentValues newValues = new ContentValues();

        newValues.put(MyDictContract.MyMainDict.LEARN, String.valueOf(w.getLevel()));

        db.update(MyDictContract.MyMainDict.TABLE_NAME, newValues, "id = ?", new String[]{String.valueOf(w.getid())});

    }

    public ArrayList<Word> get_word_from_db(SQLiteDatabase db) {

        ArrayList<Word> list = new ArrayList<Word>();

        Cursor cursor = db.query(MyDictContract.MyMainDict.TABLE_NAME, new String[]{
                        String.valueOf(MyDictContract.MyMainDict._ID),
                        String.valueOf(MyDictContract.MyMainDict.ENG_NAME),
                        String.valueOf(MyDictContract.MyMainDict.ENG_ABBREV),
                        String.valueOf(MyDictContract.MyMainDict.RUS_NAME),
                        String.valueOf(MyDictContract.MyMainDict.RUS_ABBREV),
                        String.valueOf(MyDictContract.MyMainDict.GROUP_NAME),
                        String.valueOf(MyDictContract.MyMainDict.LEARN)},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {

                Word orderDetail = new Word(false,
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict._ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.ENG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.ENG_ABBREV)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.RUS_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.RUS_ABBREV)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.GROUP_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyDictContract.MyMainDict.LEARN)));

                list.add(orderDetail);

            } while (cursor.moveToNext());

        }

        return list;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_DICT_TABLE = "CREATE TABLE " + MyDictContract.MyMainDict.TABLE_NAME + " ("
                + MyDictContract.MyMainDict._ID + " INTEGER NOT NULL DEFAULT 0, "
                + MyDictContract.MyMainDict.ENG_NAME + " TEXT NOT NULL, "
                + MyDictContract.MyMainDict.ENG_ABBREV + " TEXT NOT NULL, "
                + MyDictContract.MyMainDict.RUS_NAME + " TEXT NOT NULL, "
                + MyDictContract.MyMainDict.RUS_ABBREV + " TEXT NOT NULL, "
                + MyDictContract.MyMainDict.GROUP_NAME + " TEXT NOT NULL, "
                + MyDictContract.MyMainDict.LEARN + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_DICT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("SQLite - " + MyDictContract.MyMainDict.TABLE_NAME, "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + MyDictContract.MyMainDict.TABLE_NAME);

        onCreate(db);

    }

}
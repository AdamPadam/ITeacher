package com.itecher.adampadam.itecher.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itecher.adampadam.itecher.ProfileActivity;
import com.itecher.adampadam.itecher.adapter.Word;

import java.util.ArrayList;

public class DictDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "main_dict.db";
    private static final int DATABASE_VERSION = 2;

    public DictDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void add_word_in_db(SQLiteDatabase db, Word w) {

        ContentValues newValues = new ContentValues();

        newValues.put(DictContract.MainDict._ID, w.getid());
        newValues.put(DictContract.MainDict.ENG_NAME, w.getEng_word());
        newValues.put(DictContract.MainDict.RUS_NAME, w.getRus_word());
        newValues.put(DictContract.MainDict.GROUP_NAME, w.getGroup());
        newValues.put(DictContract.MainDict.LEARN, w.getLevel());

        db.insert(DictContract.MainDict.TABLE_NAME, null, newValues);

        ProfileActivity.updateProfile();

    }

    public void del_word_from_db(SQLiteDatabase db, Word w) {

        db.delete(DictContract.MainDict.TABLE_NAME, "id = ?", new String[]{String.valueOf(w.getid())});

        ProfileActivity.updateProfile();

    }

    public void update_word_from_db(SQLiteDatabase db, Word w) {

        ContentValues newValues = new ContentValues();

        newValues.put(DictContract.MainDict.LEARN, String.valueOf(w.getLevel()));

        db.update(DictContract.MainDict.TABLE_NAME, newValues, "id = ?", new String[]{String.valueOf(w.getid())});

    }

    public ArrayList<Word> get_word_from_db(SQLiteDatabase db) {

        ArrayList<Word> list = new ArrayList<Word>();

        Cursor cursor = db.query(DictContract.MainDict.TABLE_NAME, new String[]{
                        String.valueOf(DictContract.MainDict._ID),
                        String.valueOf(DictContract.MainDict.ENG_NAME),
                        String.valueOf(DictContract.MainDict.RUS_NAME),
                        String.valueOf(DictContract.MainDict.GROUP_NAME),
                        String.valueOf(DictContract.MainDict.LEARN)},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Word orderDetail = new Word(false,
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.ENG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.RUS_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.GROUP_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DictContract.MainDict._ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DictContract.MainDict.LEARN)));

                list.add(orderDetail);
            } while (cursor.moveToNext());
        }

        return list;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_DICT_TABLE = "CREATE TABLE " + DictContract.MainDict.TABLE_NAME + " ("
                + DictContract.MainDict._ID + " INTEGER NOT NULL DEFAULT 0, "
                + DictContract.MainDict.ENG_NAME + " TEXT NOT NULL, "
                + DictContract.MainDict.RUS_NAME + " TEXT NOT NULL, "
                + DictContract.MainDict.GROUP_NAME + " TEXT NOT NULL, "
                + DictContract.MainDict.LEARN + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_DICT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + DictContract.MainDict.TABLE_NAME);

        onCreate(db);

    }

}
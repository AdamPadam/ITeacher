package com.itecher.adampadam.iteacher.data.dict;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itecher.adampadam.iteacher.R;
import com.itecher.adampadam.iteacher.adapter.Word;

import java.util.ArrayList;

public class DictDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "main_dict.db";
    private static final int DATABASE_VERSION = 4;
    private static Context context;

    public DictDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;

    }

    public void add_word_in_db(SQLiteDatabase db, Word w) {

        ContentValues newValues = new ContentValues();

        newValues.put(DictContract.MainDict._ID, w.getid());
        newValues.put(DictContract.MainDict.ENG_NAME, w.getEng_word());
        newValues.put(DictContract.MainDict.ENG_ABBREV, w.getEng_abbrev());
        newValues.put(DictContract.MainDict.RUS_NAME, w.getRus_word());
        newValues.put(DictContract.MainDict.RUS_ABBREV, w.getRus_abbrev());
        newValues.put(DictContract.MainDict.GROUP_NAME, w.getGroup());
        newValues.put(DictContract.MainDict.LEARN, w.getLevel());

        db.insert(DictContract.MainDict.TABLE_NAME, null, newValues);

    }

    public void del_word_from_db(SQLiteDatabase db, Word w) {

        db.delete(DictContract.MainDict.TABLE_NAME, "id = ?", new String[]{String.valueOf(w.getid())});

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
                        String.valueOf(DictContract.MainDict.ENG_ABBREV),
                        String.valueOf(DictContract.MainDict.RUS_NAME),
                        String.valueOf(DictContract.MainDict.RUS_ABBREV),
                        String.valueOf(DictContract.MainDict.GROUP_NAME),
                        String.valueOf(DictContract.MainDict.LEARN)},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {

                Word orderDetail = new Word(false,
                        cursor.getInt(cursor.getColumnIndexOrThrow(DictContract.MainDict._ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.ENG_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.ENG_ABBREV)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.RUS_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.RUS_ABBREV)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DictContract.MainDict.GROUP_NAME)),
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
                + DictContract.MainDict.ENG_ABBREV + " TEXT NOT NULL, "
                + DictContract.MainDict.RUS_NAME + " TEXT NOT NULL, "
                + DictContract.MainDict.RUS_ABBREV + " TEXT NOT NULL, "
                + DictContract.MainDict.GROUP_NAME + " TEXT NOT NULL, "
                + DictContract.MainDict.LEARN + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_DICT_TABLE);

        fill_db(db);

    }

    private void fill_db(SQLiteDatabase db) {

        String[] list_s = context.getResources().getStringArray(R.array.dict);

        for (int i = 0; i < list_s.length; i++) {

            String[] element = list_s[i].split(";");

            if (element[5].equals("1")) {

                add_word_in_db(db, new Word(false, i + 1, element[1], element[2], element[3], element[4], context.getResources().getString(R.string.useful_verbs), 0));

            } else if (element[5].equals("2")) {

                add_word_in_db(db, new Word(false, i + 1, element[1], element[2], element[3], element[4], context.getResources().getString(R.string.computer), 0));

            } else if (element[5].equals("3")) {

                add_word_in_db(db, new Word(false, i + 1, element[1], element[2], element[3], element[4], context.getResources().getString(R.string.programming), 0));

            } else {

                add_word_in_db(db, new Word(false, i + 1, element[1], element[2], element[3], element[4], context.getResources().getString(R.string.net), 0));

            }

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("SQLite - " + DictContract.MainDict.TABLE_NAME, "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + DictContract.MainDict.TABLE_NAME);

        onCreate(db);

    }
}


package com.itecher.adampadam.itecher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.itecher.adampadam.itecher.data.DictDbHelper;

public class ProfileActivity extends AppCompatActivity {

    private static TextView know;
    private static TextView practice;
    private static TextView unknown;

    public static int ItemCount = 0;
    public static int MAX_ID = 167;
    public static Context context;
    private static DictDbHelper dictDbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dictDbHelper = new DictDbHelper(getApplicationContext());
        db = dictDbHelper.getWritableDatabase();

        know = (TextView) findViewById(R.id.know);
        practice = (TextView) findViewById(R.id.practice);
        unknown = (TextView) findViewById(R.id.unknown);

        context = getApplicationContext();

        updateProfile();


    }

    public static void updateProfile() {

        ItemCount = (dictDbHelper.get_word_from_db(db)).size();

        know.setText(context.getResources().getString(R.string.know) + "\n" + getKnow() + getEnd(getKnow(), 1));

        practice.setText(context.getResources().getString(R.string.practice) + "\n" + getPractice() + getEnd(getPractice(), 2));

        unknown.setText(context.getResources().getString(R.string.unknown) + "\n" + getUnknown() + getEnd(getUnknown(), 1));

    }

    private static String getEnd(int num, int i) {

        if (i == 1) {

            if ((num % 100 >= 11 && num % 100 <= 14) || (num % 10 >= 5 && num % 10 <= 9) || num % 10 == 0) {

                return " " + context.getResources().getString(R.string.word1);

            } else if (num % 10 == 1) {

                return " " + context.getResources().getString(R.string.word2);

            } else {

                return " " + context.getResources().getString(R.string.word3);

            }

        } else if (i == 2) {

            if ((num % 100 >= 11 && num % 100 <= 14) || (num % 10 >= 5 && num % 10 <= 9) || num % 10 == 0) {

                return " " + context.getResources().getString(R.string.pract1);

            } else if (num % 10 == 1) {

                return " " + context.getResources().getString(R.string.pract2);

            } else {

                return " " + context.getResources().getString(R.string.pract3);

            }

        } else {

            return " " + context.getResources().getString(R.string.err);

        }

    }

    private static int getKnow() {

        return ItemCount;

    }

    private static int getPractice() {

        return 0;

    }

    private static int getUnknown() {

        return MAX_ID - ItemCount;

    }

}

package com.itecher.adampadam.iteacher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.filippudak.ProgressPieView.ProgressPieView;
import com.itecher.adampadam.iteacher.data.dict.DictDbHelper;
import com.itecher.adampadam.iteacher.data.mydict.MyDictDbHelper;

public class ProfileActivity extends AppCompatActivity {

    public static Context context;
    private static MyDictDbHelper mydictdbh;
    private static SQLiteDatabase mydb;
    private static DictDbHelper dictdbh;
    private static SQLiteDatabase db;

    private static ProgressPieView pie_word;
    private static ProgressPieView pie_answer;
    private static ProgressPieView pie_practice;

    public static int word_p;
    public static int answer_p_good = 0;
    public static int answer_p_bad = 0;
    public static int practice_p_good = 0;
    public static int practice_p_bad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = getApplicationContext();

        mydictdbh = new MyDictDbHelper(context);
        mydb = mydictdbh.getWritableDatabase();
        dictdbh = new DictDbHelper(context);
        db = dictdbh.getWritableDatabase();

        pie_word = (ProgressPieView) findViewById(R.id.count_word_pie);

        pie_word.setOnProgressListener(new ProgressPieView.OnProgressListener() {
            @Override
            public void onProgressChanged(int progress, int max) {
            }

            @Override
            public void onProgressCompleted() {
                pie_word.setText(getString(R.string.all_word));
            }
        });


        pie_answer = (ProgressPieView) findViewById(R.id.count_right_answer_pie);

        pie_word.setOnProgressListener(new ProgressPieView.OnProgressListener() {
            @Override
            public void onProgressChanged(int progress, int max) {
            }

            @Override
            public void onProgressCompleted() {
                pie_word.setText(getString(R.string.not_errors));
            }
        });


        pie_practice = (ProgressPieView) findViewById(R.id.count_good_practice_pie);

        pie_word.setOnProgressListener(new ProgressPieView.OnProgressListener() {
            @Override
            public void onProgressChanged(int progress, int max) {
            }

            @Override
            public void onProgressCompleted() {
                pie_word.setText(getString(R.string.all_practice_good));
            }
        });

        updateProfile();


    }

    public static void updateProfile() {

        int mydict = mydictdbh.get_word_from_db(mydb).size();
        int dict = dictdbh.get_word_from_db(db).size();

        if (mydict + dict == 0 || mydict == 0) {

            pie_word.setProgress(0);
            pie_word.setText("0%");

        } else {

            word_p = (mydict * 100) / (mydict + dict);

            Log.d("err", "--------------" + dict + "------------------" + mydict + "---------------------" + word_p);

            pie_word.setProgress(word_p);
            pie_word.setText(word_p + "%");

        }

        if (answer_p_good + answer_p_bad == 0 || answer_p_good == 0) {

            pie_answer.setProgress(0);
            pie_answer.setText("0%");

        } else {

            pie_answer.setProgress((answer_p_good * 100) / (answer_p_good + answer_p_bad));
            pie_answer.setText((answer_p_good * 100) / (answer_p_good + answer_p_bad) + "%");

        }

        if (practice_p_good + practice_p_bad == 0 || practice_p_good == 0) {

            pie_practice.setProgress(0);
            pie_practice.setText("0%");

        } else {

            pie_practice.setProgress((practice_p_good * 100) / (practice_p_good + practice_p_bad));
            pie_practice.setText((practice_p_good * 100) / (practice_p_good + practice_p_bad) + "%");

        }

    }

}

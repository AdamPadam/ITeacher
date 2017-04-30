package com.itecher.adampadam.itecher.practice;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itecher.adampadam.itecher.MainActivity;
import com.itecher.adampadam.itecher.ProfileActivity;
import com.itecher.adampadam.itecher.R;
import com.itecher.adampadam.itecher.adapter.Speaker;
import com.itecher.adampadam.itecher.adapter.Word;
import com.itecher.adampadam.itecher.data.dict.DictDbHelper;
import com.itecher.adampadam.itecher.data.mydict.MyDictDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SelectListenActivity extends AppCompatActivity {

    private static Context context;
    private static ImageButton back;
    private static ImageButton question;
    private static TextView right;
    private static Button answer1;
    private static Button answer2;
    private static Button answer3;
    private static Button answer4;
    private static Button next;
    public static ArrayList<Word> list_all_word;
    public static ArrayList<Word> list_learn_word;
    protected static MyDictDbHelper mydictdbh;
    protected static SQLiteDatabase mydb;
    protected static DictDbHelper dictdbh;
    protected static SQLiteDatabase db;
    protected static int right_answer;
    protected static ArrayList<Word> list;
    public static boolean first_begin = true;
    protected static Speaker speaker;
    private static int x = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_listen);
        context = getApplicationContext();
        speaker = new Speaker(context);
        back = (ImageButton) findViewById(R.id.back_sl_imgbtn);
        question = (ImageButton) findViewById(R.id.audio_imgbtn);
        right = (TextView) findViewById(R.id.right_tv);
        answer1 = (Button) findViewById(R.id.answer_1_btn);
        answer2 = (Button) findViewById(R.id.answer_2_btn);
        answer3 = (Button) findViewById(R.id.answer_3_btn);
        answer4 = (Button) findViewById(R.id.answer_4_btn);
        next = (Button) findViewById(R.id.next_btn);

        mydictdbh = new MyDictDbHelper(context);
        mydb = mydictdbh.getReadableDatabase();
        dictdbh = new DictDbHelper(context);
        db = dictdbh.getReadableDatabase();

        list_learn_word = fillData(mydictdbh.get_word_from_db(mydb), PracticeActivity.group_number);
        list_all_word = dictdbh.get_word_from_db(db);

        if (first_begin) {
            update();
        }

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);
                answer4.setClickable(false);
                right.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);

                if (right_answer == 0) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() + 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;
                    ProfileActivity.answer_p_good++;

                } else if (right_answer == 1) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel());
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 2) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                }

                mydictdbh.update_word_from_db(mydb, list.get(right_answer));

            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);
                answer4.setClickable(false);
                right.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);

                if (right_answer == 0) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 1) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() + 1);
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;
                    ProfileActivity.answer_p_good++;

                } else if (right_answer == 2) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                }

                mydictdbh.update_word_from_db(mydb, list.get(right_answer));
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);
                answer4.setClickable(false);
                right.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);

                if (right_answer == 0) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 1) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 2) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() + 1);
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;
                    ProfileActivity.answer_p_good++;

                } else {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                }

                mydictdbh.update_word_from_db(mydb, list.get(right_answer));

            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);
                answer4.setClickable(false);
                right.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);

                if (right_answer == 0) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 1) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else if (right_answer == 2) {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() - 1);
                    answer1.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer2.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    answer3.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                } else {

                    (list.get(right_answer)).setLevel((list.get(right_answer)).getLevel() + 1);
                    answer4.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.right));
                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;
                    ProfileActivity.answer_p_good++;

                }

                mydictdbh.update_word_from_db(mydb, list.get(right_answer));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list_learn_word = mydictdbh.get_word_from_db(mydb);
                list_all_word = dictdbh.get_word_from_db(db);
                MainActivity.isBack = true;
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                right.setVisibility(view.INVISIBLE);
                next.setVisibility(view.INVISIBLE);
                answer1.setBackground(getResources().getDrawable(R.drawable.round_btn));
                answer2.setBackground(getResources().getDrawable(R.drawable.round_btn));
                answer3.setBackground(getResources().getDrawable(R.drawable.round_btn));
                answer4.setBackground(getResources().getDrawable(R.drawable.round_btn));
                update();

            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                speaker.speak((list.get(right_answer)).getEng_word());

            }
        });

    }

    private static ArrayList<Word> get4Words() {


        ArrayList<Word> list = new ArrayList<>();

        Random rnd = new Random();

        right_answer = rnd.nextInt(list_learn_word.size());

        list.add(list_learn_word.get(right_answer));


        int r1 = rnd.nextInt(list_all_word.size());

        while (((list_all_word.get(r1)).getRus_word()).equals((list_learn_word.get(right_answer)).getRus_word())) {

            r1 = rnd.nextInt(list_all_word.size());

        }

        list.add(list_all_word.get(r1));


        int r2 = rnd.nextInt(list_all_word.size());

        while (((list_all_word.get(r2)).getRus_word()).equals((list_learn_word.get(right_answer)).getRus_word()) || r2 == r1) {

            r2 = rnd.nextInt(list_all_word.size());

        }

        list.add(list_all_word.get(r2));


        int r3 = rnd.nextInt(list_all_word.size());

        while (((list_all_word.get(r3)).getRus_word()).equals((list_learn_word.get(right_answer)).getRus_word()) || r3 == r1 || r3 == r2) {

            r3 = rnd.nextInt(list_all_word.size());

        }

        list.add(list_all_word.get(r3));


        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals(list_learn_word.get(right_answer))) {

                list_learn_word.remove(list_learn_word.get(right_answer));
                right_answer = i;
                break;

            }

        }

        return list;
    }

    private static ArrayList<Word> fillData(ArrayList<Word> list, int b) {

        ArrayList<Word> list2 = new ArrayList<Word>();

        for (int i = 0; i < list.size(); i++) {

            if (b == 1) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.useful_verbs)))
                    list2.add(list.get(i));

            } else if (b == 2) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.computer)))
                    list2.add(list.get(i));

            } else if (b == 3) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.programming)))
                    list2.add(list.get(i));

            } else if (b == 4) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.net)))
                    list2.add(list.get(i));

            } else {

                list2.add(list.get(i));

            }

        }

        return list2;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void update() {

        answer1.setClickable(true);
        answer2.setClickable(true);
        answer3.setClickable(true);
        answer4.setClickable(true);

        if (list_learn_word.size() < 1) end();

        list_learn_word = fillData(list_learn_word, PracticeActivity.group_number);
        list = get4Words();

        answer1.setText((list.get(0)).getEng_word());
        answer2.setText((list.get(1)).getEng_word());
        answer3.setText((list.get(2)).getEng_word());
        answer4.setText((list.get(3)).getEng_word());

        if (list_learn_word.size() < ((fillData(mydictdbh.get_word_from_db(mydb), PracticeActivity.group_number)).size()) - x) {

            speaker.speak((list.get(right_answer)).getEng_word());
            x = 1;

        }

        Log.d("mLog", "");
        Log.d("mLog", "");
        Log.d("mLog", "");
        Log.d("mLog", "---------------------------------------------------------------------------------------------------------");
        Log.d("mLog", "                 answer1: " + answer1.getText().toString());
        Log.d("mLog", "                 answer2: " + answer2.getText().toString());
        Log.d("mLog", "                 answer3: " + answer3.getText().toString());
        Log.d("mLog", "                 answer4: " + answer4.getText().toString());
        Log.d("mLog", "                 right: " + String.valueOf(right_answer + 1));
        Log.d("mLog", "                 lvl: " + String.valueOf((list.get(right_answer).getLevel())));
        Log.d("mLog", "---------------------------------------------------------------------------------------------------------");
        Log.d("mLog", "");
        Log.d("mLog", "");
        Log.d("mLog", "");

    }

    private static void end() {

        list_learn_word = mydictdbh.get_word_from_db(mydb);
        list_all_word = dictdbh.get_word_from_db(db);
        if (!EndPracticeActivity.first_end) EndPracticeActivity.update();
        Intent intent = new Intent(context, EndPracticeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
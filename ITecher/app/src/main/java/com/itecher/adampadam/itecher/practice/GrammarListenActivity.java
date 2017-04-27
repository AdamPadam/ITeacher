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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itecher.adampadam.itecher.MainActivity;
import com.itecher.adampadam.itecher.ProfileActivity;
import com.itecher.adampadam.itecher.R;
import com.itecher.adampadam.itecher.adapter.Speaker;
import com.itecher.adampadam.itecher.adapter.Word;
import com.itecher.adampadam.itecher.data.DictDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GrammarListenActivity extends AppCompatActivity {

    private static Context context;
    private static ImageButton back;
    private static ImageButton question;
    private static TextView right;
    private static EditText answer;
    private static Button ok;
    private static Button next;
    public static ArrayList<Word> list_learn_word;
    protected static DictDbHelper dictdbh;
    protected static SQLiteDatabase db;
    protected static Word right_answer;
    private static int right_answer_number;
    public static boolean first_begin = true;
    private static int MAX_ID = 167;
    protected static Speaker speaker;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_listen);
        context = getApplicationContext();
        back = (ImageButton) findViewById(R.id.back_btn);
        question = (ImageButton) findViewById(R.id.audio_grammar);
        right = (TextView) findViewById(R.id.right);
        answer = (EditText) findViewById(R.id.answer_et);
        ok = (Button) findViewById(R.id.ok);
        next = (Button) findViewById(R.id.next);

        dictdbh = new DictDbHelper(context);
        db = dictdbh.getReadableDatabase();
        speaker = new Speaker(context);

        list_learn_word = dictdbh.get_word_from_db(db);

        if (first_begin) {
            update();
        }

        speaker.speak(right_answer.getEng_word());

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ok.setClickable(false);
                ok.setVisibility(view.INVISIBLE);
                right.setVisibility(view.VISIBLE);
                next.setVisibility(view.VISIBLE);
                next.setClickable(true);
                answer.setClickable(false);

                String s = right_answer.getEng_word();
                s = s.toLowerCase();

                char[] c = s.toCharArray();

                if (c[c.length - 1] == ' ') {

                    s = "";

                    for (int i = 0; i < c.length - 1; i++) {

                        s += c[i];

                    }

                }

                if (s.equals(answer.getText().toString())) {

                    right.setBackgroundColor(getResources().getColor(R.color.green));
                    right.setText(getResources().getString(R.string.right));
                    answer.setBackgroundColor(getResources().getColor(R.color.green));
                    right_answer.setLevel(right_answer.getLevel() + 1);
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;

                } else {

                    right.setBackgroundColor(getResources().getColor(R.color.red));
                    right.setText(getResources().getString(R.string.not_right) + getString(R.string.right_answer_text) + s);
                    answer.setBackgroundColor(getResources().getColor(R.color.red));
                    right_answer.setLevel(right_answer.getLevel() - 1);
                    EndPracticeActivity.all_word_number++;

                }

                dictdbh.update_word_from_db(db, right_answer);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list_learn_word = dictdbh.get_word_from_db(db);
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
                ok.setVisibility(view.VISIBLE);
                right.setBackgroundColor(getResources().getColor(R.color.white));
                answer.setBackgroundColor(getResources().getColor(R.color.white));
                update();

            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                speaker.speak(right_answer.getEng_word());

            }
        });

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

        ok.setClickable(true);
        next.setClickable(false);
        answer.setClickable(true);
        answer.setText("");

        if (list_learn_word.size() < 1) end();

        Random rnd = new Random();

        right_answer_number = rnd.nextInt(list_learn_word.size());

        right_answer = list_learn_word.get(right_answer_number);

        list_learn_word.remove(right_answer);

        list_learn_word = fillData(list_learn_word, PracticeActivity.group_number);

        speaker.speak(right_answer.getEng_word());

    }

    private static void end() {

        ProfileActivity.PracticeCount++;
        list_learn_word = dictdbh.get_word_from_db(db);
        if (!EndPracticeActivity.first_end) EndPracticeActivity.update();
        Intent intent = new Intent(context, EndPracticeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
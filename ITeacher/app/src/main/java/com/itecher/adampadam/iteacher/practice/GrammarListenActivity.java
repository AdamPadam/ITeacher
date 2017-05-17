package com.itecher.adampadam.iteacher.practice;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itecher.adampadam.iteacher.MainActivity;
import com.itecher.adampadam.iteacher.ProfileActivity;
import com.itecher.adampadam.iteacher.R;
import com.itecher.adampadam.iteacher.adapter.Speaker;
import com.itecher.adampadam.iteacher.adapter.Word;
import com.itecher.adampadam.iteacher.data.mydict.MyDictDbHelper;

import java.util.ArrayList;
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
    protected static MyDictDbHelper mydictdbh;
    protected static SQLiteDatabase mydb;
    protected static Word right_answer;
    private static int right_answer_number;
    public static boolean first_begin = true;
    protected static Speaker speaker;
    private static int x = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_listen);
        context = getApplicationContext();
        speaker = new Speaker(context);
        back = (ImageButton) findViewById(R.id.back_gl_imgbtn);
        question = (ImageButton) findViewById(R.id.audio_grammar_imgbtn);
        right = (TextView) findViewById(R.id.right_tv);
        answer = (EditText) findViewById(R.id.answer_et);
        ok = (Button) findViewById(R.id.ok_btn);
        next = (Button) findViewById(R.id.next_btn);

        mydictdbh = new MyDictDbHelper(context);
        mydb = mydictdbh.getReadableDatabase();

        list_learn_word = fillData(mydictdbh.get_word_from_db(mydb), PracticeActivity.group_number);

        if (first_begin) {
            update();
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ok.setClickable(false);
                ok.setVisibility(View.INVISIBLE);
                right.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                next.setClickable(true);
                answer.setActivated(false);

                if (((right_answer.getEng_word()).toLowerCase()).equals(((answer.getText()).toString()).toLowerCase())) {

                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_right));
                    right.setText(getResources().getString(R.string.right));
                    answer.setBackgroundColor(getResources().getColor(R.color.primary));
                    right_answer.setLevel(right_answer.getLevel() + 1);
                    EndPracticeActivity.all_word_number++;
                    EndPracticeActivity.right_word_number++;
                    ProfileActivity.answer_p_good++;

                } else {

                    right.setBackground(getResources().getDrawable(R.drawable.round_btn_notright));
                    right.setText(getResources().getString(R.string.not_right) + getString(R.string.right_answer_text) + (right_answer.getEng_word()).toLowerCase());
                    answer.setBackgroundColor(getResources().getColor(R.color.primary_red));
                    right_answer.setLevel(right_answer.getLevel() - 1);
                    EndPracticeActivity.all_word_number++;
                    ProfileActivity.answer_p_bad++;

                }

                mydictdbh.update_word_from_db(mydb, right_answer);

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list_learn_word = mydictdbh.get_word_from_db(mydb);
                MainActivity.isBack = true;
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                speaker.speak(right_answer.getEng_word());

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                right.setVisibility(view.INVISIBLE);
                next.setVisibility(view.INVISIBLE);
                ok.setVisibility(view.VISIBLE);
                right.setBackground(getResources().getDrawable(R.drawable.round_btn));
                answer.setBackgroundColor(getResources().getColor(R.color.white));
                update();

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
        ok.setVisibility(View.VISIBLE);
        next.setClickable(false);
        answer.setActivated(true);
        answer.setText("");

        if (list_learn_word.size() < 1) end();

        Random rnd = new Random();

        right_answer_number = rnd.nextInt(list_learn_word.size());

        right_answer = list_learn_word.get(right_answer_number);

        list_learn_word.remove(right_answer);

        list_learn_word = fillData(list_learn_word, PracticeActivity.group_number);

        if (list_learn_word.size() < ((fillData(mydictdbh.get_word_from_db(mydb), PracticeActivity.group_number)).size()) - x) {

            speaker.speak(right_answer.getEng_word());
            x = 1;

        }

    }

    private static void end() {
        list_learn_word = mydictdbh.get_word_from_db(mydb);
        if (!EndPracticeActivity.first_end) EndPracticeActivity.update();
        Intent intent = new Intent(context, EndPracticeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
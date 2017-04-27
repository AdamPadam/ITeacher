package com.itecher.adampadam.itecher.practice;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.itecher.adampadam.itecher.R;
import com.itecher.adampadam.itecher.data.DictDbHelper;

public class PracticeActivity extends AppCompatActivity {

    private Spinner group;
    private Spinner type;
    private Button go;
    private Context context;
    public static int group_number = 0;
    public static int type_number = 0;
    private static DictDbHelper dictdbh;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        group = (Spinner) findViewById(R.id.group_practice);
        type = (Spinner) findViewById(R.id.type_practice);
        go = (Button) findViewById(R.id.go_practice);
        context = getApplicationContext();
        dictdbh = new DictDbHelper(context);
        db = dictdbh.getReadableDatabase();

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.type);

                if (choose[selectedItemPosition].equals(getResources().getString(R.string.word_translate_select))) {

                    type_number = 1;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.translate_word_select))) {

                    type_number = 2;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.listen_select))) {

                    type_number = 3;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.listen_grammar))) {

                    type_number = 4;

                } else {

                    type_number = 0;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                type_number = 0;

            }
        });

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.group_my_dict);

                if (choose[selectedItemPosition].equals(getResources().getString(R.string.useful_verbs))) {

                    group_number = 1;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.computer))) {

                    group_number = 2;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.programming))) {

                    group_number = 3;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.net))) {

                    group_number = 4;

                } else {

                    group_number = 0;

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

                group_number = 0;

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if ((dictdbh.get_word_from_db(db)).size() >= 1) {

                    if (type_number == 2 || type_number == 1 || type_number == 0) {

                        if (!SelectWordActivity.first_begin) SelectWordActivity.update();
                        startActivity(new Intent(context, SelectWordActivity.class));

                    } else if (type_number == 3) {

                        if (!SelectListenActivity.first_begin) {
                            SelectListenActivity.update();
                            SelectListenActivity.speaker.speak((SelectListenActivity.list.get(SelectListenActivity.right_answer)).getEng_word());
                        }
                        startActivity(new Intent(context, SelectListenActivity.class));

                    } else if (type_number == 4) {

                        if (!GrammarListenActivity.first_begin) {
                            GrammarListenActivity.update();
                            GrammarListenActivity.speaker.speak(GrammarListenActivity.right_answer.getEng_word());
                        }
                        startActivity(new Intent(context, GrammarListenActivity.class));

                    }

                } else {

                    Toast.makeText(context, getResources().getString(R.string.is_little_word), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}

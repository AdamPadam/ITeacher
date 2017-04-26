package com.itecher.adampadam.itecher;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

            String[] choose = getResources().getStringArray(R.array.group_my_dict);

            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                if (choose[selectedItemPosition].equals(getResources().getString(R.string.word_translate))) {

                    type_number = 1;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.translate_word))) {

                    type_number = 2;

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

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.unknown_word))) {

                    group_number = 5;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.bad_know_word))) {

                    group_number = 6;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.good_know_word))) {

                    group_number = 7;

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.the_best_know_word))) {

                    group_number = 8;

                } else {

                    group_number = 0;

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

                group_number = 0;

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((dictdbh.get_word_from_db(db)).size() >= 4) {

                    if (!BeginPracticeActivity.first) BeginPracticeActivity.update();
                    startActivity(new Intent(context, BeginPracticeActivity.class));

                } else {

                    Toast.makeText(context, getResources().getString(R.string.is_little_word), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}

package com.itecher.adampadam.itecher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import com.itecher.adampadam.itecher.adapter.BoxAdapter;
import com.itecher.adampadam.itecher.adapter.Word;
import com.itecher.adampadam.itecher.data.DictDbHelper;

public class CardActivity extends AppCompatActivity {

    private Spinner spinner;
    private static ArrayList<Word> word = new ArrayList<Word>();
    private static BoxAdapter boxAdapter;
    private static DictDbHelper dictdbh;
    private static SQLiteDatabase db;
    private static ListView lvMain;
    private EditText search_et;
    private ImageButton search_btn;
    private static String search_query;
    public Button del_word;
    public static Context context;
    public static int last = 0;

    public static ArrayList<Word> list_new_word = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        context = getApplicationContext();

        search_et = (EditText) findViewById(R.id.search_edittext_my_dict);
        search_btn = (ImageButton) findViewById(R.id.search_btn_my_dict);

        lvMain = (ListView) findViewById(R.id.lvMain_my_dict);
        spinner = (Spinner) findViewById(R.id.spinner_my_dict);

        dictdbh = new DictDbHelper(context);
        db = dictdbh.getWritableDatabase();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.group_my_dict);

                list_new_word = dictdbh.get_word_from_db(db);

                if (list_new_word.size() > 0) {

                    if (choose[selectedItemPosition].equals(getResources().getString(R.string.useful_verbs))) {

                        word.clear();

                        fillData(list_new_word, 1);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.computer))) {

                        word.clear();

                        fillData(list_new_word, 2);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.programming))) {

                        word.clear();

                        fillData(list_new_word, 3);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.net))) {

                        word.clear();

                        fillData(list_new_word, 4);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.unknown_word))) {

                        word.clear();

                        fillData(list_new_word, 10);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.bad_know_word))) {

                        word.clear();

                        fillData(list_new_word, 20);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.good_know_word))) {

                        word.clear();

                        fillData(list_new_word, 30);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.the_best_know_word))) {

                        word.clear();

                        fillData(list_new_word, 40);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    } else {

                        word.clear();

                        fillData(list_new_word, 0);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    }

                } else {

                    word.clear();

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

                list_new_word = dictdbh.get_word_from_db(db);

                if (list_new_word.size() > 0) {

                    word.clear();


                    fillData(list_new_word, 0);

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);
                } else {

                    word.clear();

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);

                }
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                list_new_word = dictdbh.get_word_from_db(db);

                if (list_new_word.size() > 0) {

                    search_query = (search_et.getText()).toString();

                    word.clear();

                    fillData(list_new_word, -1);

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);
                } else {

                    word.clear();

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);

                }
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    list_new_word = dictdbh.get_word_from_db(db);

                    if (list_new_word.size() > 0) {

                        search_query = (search_et.getText()).toString();

                        word.clear();

                        fillData(list_new_word, -1);

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);
                    } else {

                        word.clear();

                        boxAdapter = new BoxAdapter(context, word, true);
                        lvMain.setAdapter(boxAdapter);

                    }

                    return true;
                }
                return false;
            }
        });

        del_word = (Button) findViewById(R.id.del_word_in_dict_my_dict);

        del_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list_new_word = dictdbh.get_word_from_db(db);

                if (list_new_word.size() > 0) {

                    for (Word w : boxAdapter.getBox()) {

                        if (w.box) {

                            dictdbh.del_word_from_db(db, w);
                            w.box = false;

                        }

                        updateCard();

                    }

                    DictActivity.updateDict();

                } else {

                    word.clear();

                    boxAdapter = new BoxAdapter(context, word, true);
                    lvMain.setAdapter(boxAdapter);

                }
            }
        });

    }

    private static void fillData(ArrayList<Word> list, int b) {

        last = b;

        for (int i = 0; i < list.size(); i++) {

            if (b == 1) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.useful_verbs)))
                    word.add(list.get(i));

            } else if (b == 2) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.computer)))
                    word.add(list.get(i));

            } else if (b == 3) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.programming)))
                    word.add(list.get(i));

            } else if (b == 4) {

                if (((list.get(i)).getGroup()).equals(context.getResources().getString(R.string.net)))
                    word.add(list.get(i));

            } else if (b == -1) {

                if ((((list.get(i)).getEng_word()).contains(search_query)) || (((list.get(i)).getRus_word()).contains(search_query)))
                    word.add(list.get(i));

            } else if (b == 10) {

                if ((list.get(i)).getLevel() < 10) word.add(list.get(i));

            } else if (b == 20) {

                if ((list.get(i)).getLevel() >= 10 && (list.get(i)).getLevel() < 15)
                    word.add(list.get(i));

            } else if (b == 30) {

                if ((list.get(i)).getLevel() >= 15 && (list.get(i)).getLevel() < 20)
                    word.add(list.get(i));


            } else if (b == 40) {

                if ((list.get(i)).getLevel() >= 20) word.add(list.get(i));


            } else {

                word.add(list.get(i));

            }

        }

    }

    public static void updateCard() {

        list_new_word = dictdbh.get_word_from_db(db);

        word.clear();

        fillData(list_new_word, last);

        boxAdapter = new BoxAdapter(context, word, true);
        lvMain.setAdapter(boxAdapter);

    }

}

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

public class DictActivity extends AppCompatActivity {

    private static Spinner spinner;
    public static ArrayList<Word> word = new ArrayList<Word>();
    public static BoxAdapter boxAdapter;
    public static ListView lvMain;
    private static EditText search_et;
    private static ImageButton search_btn;
    private static String search_query;
    public static final int MAX_ID = 172;
    public static Button add_word;
    private static DictDbHelper dictdbh;
    public static SQLiteDatabase db;
    public static ArrayList<Word> list;
    public static int last = 0;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        search_et = (EditText) findViewById(R.id.search_edittext);
        search_btn = (ImageButton) findViewById(R.id.search_btn);

        lvMain = (ListView) findViewById(R.id.lvMain);
        spinner = (Spinner) findViewById(R.id.spinner);

        dictdbh = new DictDbHelper(getApplicationContext());
        db = dictdbh.getWritableDatabase();

        context = getApplicationContext();
        list = getWord_for_list(db);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                list = getWord_for_list(db);

                String[] choose = getResources().getStringArray(R.array.group);

                if (choose[selectedItemPosition].equals(getResources().getString(R.string.useful_verbs))) {

                    word.clear();

                    fillData(list, 1);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.computer))) {

                    word.clear();

                    fillData(list, 2);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.programming))) {

                    word.clear();

                    fillData(list, 3);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.net))) {

                    word.clear();

                    fillData(list, 4);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                } else {

                    word.clear();

                    fillData(list, 0);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

                list = getWord_for_list(db);

                word.clear();

                fillData(list, 0);

                boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                lvMain.setAdapter(boxAdapter);

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                list = getWord_for_list(db);

                search_query = (search_et.getText()).toString();

                word.clear();

                fillData(list, -1);

                boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                lvMain.setAdapter(boxAdapter);

            }
        });

        add_word = (Button) findViewById(R.id.add_word_in_dict);

        add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Word w : boxAdapter.getBox()) {
                    if (w.box) {

                        dictdbh.add_word_in_db(db, w);
                        w.box = false;

                    }

                    updateDict();

                }

            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    list = getWord_for_list(db);

                    search_query = (search_et.getText()).toString();

                    word.clear();

                    fillData(list, -1);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, false);
                    lvMain.setAdapter(boxAdapter);

                    return true;
                }
                return false;
            }
        });

    }

    public static ArrayList<Word> getWord_for_list(SQLiteDatabase db) {

        ArrayList<Word> word = new ArrayList<Word>();
        ArrayList<Word> word_in_dict = dictdbh.get_word_from_db(db);

        String[] list = context.getResources().getStringArray(R.array.dict);

        for (int i = 0; i < MAX_ID; i++) {

            boolean b = true;

            String[] s = list[i].split(";");

            for (Word w : word_in_dict) {

                if ((w.getid() == i + 1)) {

                    b = false;

                }

            }

            if (b) {

                if (Integer.valueOf(s[3]) == 1) {

                    word.add(new Word(false, s[0], s[1], "полезные глаголы", i + 1));

                } else if (Integer.valueOf(s[3]) == 2) {

                    word.add(new Word(false, s[0], s[1], "компютер", i + 1));

                } else if (Integer.valueOf(s[3]) == 3) {

                    word.add(new Word(false, s[0], s[1], "программирование", i + 1));

                } else if (Integer.valueOf(s[3]) == 4) {

                    word.add(new Word(false, s[0], s[1], "интернет", i + 1));

                } else if (Integer.valueOf(s[3]) == 4) {

                    word.add(new Word(false, s[0], s[1], "ОШИБКА", i + 1));

                }

            }

        }

        return word;

    }

    public static void fillData(ArrayList<Word> list, int b) {

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

            } else {

                word.add(list.get(i));

            }

        }

    }

    public static void updateDict() {

        list = getWord_for_list(db);

        word.clear();

        fillData(list, last);

        lvMain.setAdapter(boxAdapter);

        CardActivity.updateCard();

    }

}

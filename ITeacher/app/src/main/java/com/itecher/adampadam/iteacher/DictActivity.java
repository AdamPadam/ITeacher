package com.itecher.adampadam.iteacher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.itecher.adampadam.iteacher.adapter.BoxAdapter;
import com.itecher.adampadam.iteacher.adapter.Word;
import com.itecher.adampadam.iteacher.data.dict.DictDbHelper;
import com.itecher.adampadam.iteacher.data.mydict.MyDictDbHelper;

import java.util.ArrayList;

public class DictActivity extends AppCompatActivity {

    private static Spinner spinner;
    public static ArrayList<Word> word = new ArrayList<Word>();
    public static BoxAdapter boxAdapter;
    public static ListView lvMain;
    private static EditText search_et;
    private static ImageButton search_btn;
    private static String search_query;
    public static Button add_word;
    private static MyDictDbHelper mydictdbh;
    public static SQLiteDatabase mydb;
    private static DictDbHelper dictdbh;
    public static SQLiteDatabase db;
    public static ArrayList<Word> list;
    public static int last = 0;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        search_et = (EditText) findViewById(R.id.search_et);
        search_btn = (ImageButton) findViewById(R.id.search_imgbtn);

        lvMain = (ListView) findViewById(R.id.main_lv);
        spinner = (Spinner) findViewById(R.id.spinner_sp);

        mydictdbh = new MyDictDbHelper(getApplicationContext());
        mydb = mydictdbh.getWritableDatabase();
        dictdbh = new DictDbHelper(getApplicationContext());
        db = dictdbh.getWritableDatabase();

        context = getApplicationContext();
        list = dictdbh.get_word_from_db(db);

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                list = dictdbh.get_word_from_db(db);

                String[] choose = getResources().getStringArray(R.array.group);

                if (choose[selectedItemPosition].equals(getResources().getString(R.string.useful_verbs))) {

                    word.clear();

                    fillData(list, 1);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.computer))) {

                    word.clear();

                    fillData(list, 2);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.programming))) {

                    word.clear();

                    fillData(list, 3);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.net))) {

                    word.clear();

                    fillData(list, 4);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.unknown_word))) {

                    word.clear();

                    fillData(list, 5);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.bad_know_word))) {

                    word.clear();

                    fillData(list, 6);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.good_know_word))) {

                    word.clear();

                    fillData(list, 7);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else if (choose[selectedItemPosition].equals(getResources().getString(R.string.the_best_know_word))) {

                    word.clear();

                    fillData(list, 8);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                } else {

                    word.clear();

                    fillData(list, 0);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

                list = dictdbh.get_word_from_db(db);

                word.clear();

                fillData(list, 0);

                boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                lvMain.setAdapter(boxAdapter);

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                list = dictdbh.get_word_from_db(db);

                search_query = (search_et.getText()).toString();

                word.clear();

                fillData(list, -1);

                boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                lvMain.setAdapter(boxAdapter);

            }
        });

        add_word = (Button) findViewById(R.id.add_word_in_dict_btn);

        add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Word w : boxAdapter.getBox()) {
                    if (w.box) {

                        mydictdbh.add_word_in_db(mydb, w);
                        dictdbh.del_word_from_db(db, w);
                        w.box = false;

                    }

                    updateDict();

                }

                ProfileActivity.updateProfile();

            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    list = dictdbh.get_word_from_db(db);

                    search_query = (search_et.getText()).toString();

                    word.clear();

                    fillData(list, -1);

                    boxAdapter = new BoxAdapter(getApplicationContext(), word, true);
                    lvMain.setAdapter(boxAdapter);

                    return true;
                }
                return false;
            }
        });

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

            } else if (b == 5) {

                if ((list.get(i)).getLevel() < 10) word.add(list.get(i));

            } else if (b == 6) {

                if ((list.get(i)).getLevel() >= 10 && (list.get(i)).getLevel() < 15)
                    word.add(list.get(i));

            } else if (b == 7) {

                if ((list.get(i)).getLevel() >= 15 && (list.get(i)).getLevel() < 20)
                    word.add(list.get(i));

            } else if (b == 8) {

                if ((list.get(i)).getLevel() >= 20) word.add(list.get(i));

            } else {

                word.add(list.get(i));

            }

        }

    }

    public static void updateDict() {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        list = dictdbh.get_word_from_db(db);
        word.clear();
        fillData(list, last);
        lvMain.setAdapter(boxAdapter);
        CardActivity.updateCard();

    }

}

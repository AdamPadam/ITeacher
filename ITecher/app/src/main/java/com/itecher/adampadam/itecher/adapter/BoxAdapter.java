package com.itecher.adampadam.itecher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.itecher.adampadam.itecher.R;

import java.util.ArrayList;


public class BoxAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater lInflater;
    public ArrayList<Word> object;
    public ArrayList<Integer> id_list;
    public boolean isLearn;

    public BoxAdapter(Context context, ArrayList<Word> object, boolean learn) {
        this.context = context;
        this.object = object;
        this.isLearn = learn;
        this.lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return object.size();

    }

    @Override
    public Object getItem(int position) {

        return this.object.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View view = convertview;
        if (view == null) {
            view = lInflater.inflate(R.layout.word_id_dict, parent, false);
        }

        id_list = new ArrayList<Integer>();

        Word word = getWord(position);

        ((TextView) view.findViewById(R.id.group)).setText(word.getGroup());
        ((TextView) view.findViewById(R.id.eng)).setText(word.getEng_word());
        ((TextView) view.findViewById(R.id.rus)).setText(word.getRus_word());

        if (isLearn) {

            if (word.getLevel() < 10) {

                ((TextView) view.findViewById(R.id.learn)).setText(context.getResources().getString(R.string.unknown_word_text));

            } else if (word.getLevel() >= 10 && word.getLevel() < 15) {

                ((TextView) view.findViewById(R.id.learn)).setText(context.getResources().getString(R.string.bad_know_word_text));

            } else if (word.getLevel() >= 15 && word.getLevel() < 30) {

                ((TextView) view.findViewById(R.id.learn)).setText(context.getResources().getString(R.string.good_know_word_text));

            } else {

                ((TextView) view.findViewById(R.id.learn)).setText(context.getResources().getString(R.string.the_best_know_word_text));

            }

        }

        CheckBox cbAdd = (CheckBox) view.findViewById(R.id.box);
        cbAdd.setOnCheckedChangeListener(myCheckedChangList);
        cbAdd.setTag(position);
        cbAdd.setChecked(word.box);
        return view;

    }

    Word getWord(int position) {

        return ((Word) getItem(position));

    }

    public ArrayList<Word> getBox() {

        ArrayList<Word> box = new ArrayList<Word>();
        for (Word word : object) {

            if (word.box) box.add(word);

        }

        return box;

    }

    CompoundButton.OnCheckedChangeListener myCheckedChangList = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            getWord((Integer) buttonView.getTag()).box = isChecked;

        }
    };

}

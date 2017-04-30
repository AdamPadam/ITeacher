package com.itecher.adampadam.itecher.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itecher.adampadam.itecher.R;

import java.util.ArrayList;


public class BoxAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater lInflater;
    public ArrayList<Word> object;
    public ArrayList<Integer> id_list;
    public boolean isLearn;
    public LinearLayout body;

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

        final Speaker speaker = new Speaker(context);
        final String sp = word.getEng_word();

        final CheckBox cbAdd = (CheckBox) view.findViewById(R.id.adapter_box_cb);

        body = (LinearLayout) view.findViewById(R.id.adapter_layout);
        body.setClickable(true);
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbAdd.isChecked()) {

                    cbAdd.setChecked(false);

                } else {

                    cbAdd.setChecked(true);

                }

            }
        });

        if (word.getEng_abbrev().equals(context.getString(R.string.null_text))) {

            ((TextView) view.findViewById(R.id.adapter_eng_abbrev_tv)).setText("");

        } else {

            ((TextView) view.findViewById(R.id.adapter_eng_abbrev_tv)).setText("(" + word.getEng_abbrev() + ")");

        }

        if (word.getRus_abbrev().equals(context.getString(R.string.null_text))) {

            ((TextView) view.findViewById(R.id.adapter_rus_abbrev_tv)).setText("");

        } else {

            ((TextView) view.findViewById(R.id.adapter_rus_abbrev_tv)).setText("(" + word.getRus_abbrev() + ")");

        }

        ((TextView) view.findViewById(R.id.adapter_eng_word_tv)).setText(word.getEng_word());
        ((TextView) view.findViewById(R.id.adapter_rus_word_tv)).setText(word.getRus_word());
        ((TextView) view.findViewById(R.id.adapter_group_tv)).setText(word.getGroup());

        view.findViewById(R.id.adapter_listen_btn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                speaker.speak(sp);

            }
        });

        if (isLearn) {

            if (word.getLevel() < 10) {

                ((TextView) view.findViewById(R.id.adapter_learn_tv)).setText(context.getResources().getString(R.string.unknown_word_text));

            } else if (word.getLevel() >= 10 && word.getLevel() < 15) {

                ((TextView) view.findViewById(R.id.adapter_learn_tv)).setText(context.getResources().getString(R.string.bad_know_word_text));

            } else if (word.getLevel() >= 15 && word.getLevel() < 30) {

                ((TextView) view.findViewById(R.id.adapter_learn_tv)).setText(context.getResources().getString(R.string.good_know_word_text));

            } else {

                ((TextView) view.findViewById(R.id.adapter_learn_tv)).setText(context.getResources().getString(R.string.the_best_know_word_text));

            }

        }

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

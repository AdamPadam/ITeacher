package com.itecher.adampadam.itecher.adapter;

public class Word {

    public boolean box;
    private String eng_word;
    private String rus_word;
    private String group;
    private int id;
    private int level;

    public Word(boolean box, String eng_word, String rus_word, String group, int id) {
        this.box = box;
        this.eng_word = eng_word;
        this.rus_word = rus_word;
        this.group = group;
        this.id = id;
        this.level = 0;
    }

    public Word(boolean box, String eng_word, String rus_word, String group, int id, int level) {
        this.box = box;
        this.eng_word = eng_word;
        this.rus_word = rus_word;
        this.group = group;
        this.id = id;
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String getEng_word() {
        return eng_word;
    }

    public String getRus_word() {
        return rus_word;
    }

    public String getGroup() {
        return group;
    }

    public int getid() {
        return id;
    }
}

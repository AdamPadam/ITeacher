package com.itecher.adampadam.itecher.adapter;

public class Word {

    public boolean box;
    private int id;
    private String eng_word;
    private String eng_abbrev;
    private String rus_word;
    private String rus_abbrev;
    private String group;
    private int level;

    public Word(boolean box, int id, String eng_word, String eng_abbrev, String rus_word, String rus_abbrev, String group, int level) {

        this.box = box;
        this.id = id;
        this.eng_word = eng_word;
        this.eng_abbrev = eng_abbrev;
        this.rus_word = rus_word;
        this.rus_abbrev = rus_abbrev;
        this.group = group;
        this.level = level;

    }

    public int getid() {

        return this.id;

    }

    public String getEng_word() {

        return this.eng_word;

    }

    public String getEng_abbrev() {

        return this.eng_abbrev;

    }

    public String getRus_abbrev() {

        return this.rus_abbrev;

    }

    public String getRus_word() {

        return this.rus_word;

    }

    public String getGroup() {

        return this.group;

    }

    public int getLevel() {

        return this.level;

    }

    public void setLevel(int level) {

        this.level = level;

    }

}

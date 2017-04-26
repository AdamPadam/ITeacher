package com.itecher.adampadam.itecher.data;

import android.provider.BaseColumns;

public final class DictContract {

    private DictContract() {
    }

    public static final class MainDict implements BaseColumns {

        public final static String TABLE_NAME = "MyDict";

        public final static String _ID = "id";
        public final static String ENG_NAME = "eng";
        public final static String RUS_NAME = "rus";
        public final static String GROUP_NAME = "group_name";
        public final static String LEARN = "learn";

    }

}
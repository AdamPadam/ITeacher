package com.itecher.adampadam.iteacher.data.dict;

import android.provider.BaseColumns;

public class DictContract {

    private DictContract() {
    }

    public static final class MainDict implements BaseColumns {

        public final static String TABLE_NAME = "Dict";

        public final static String _ID = "id";
        public final static String ENG_NAME = "eng";
        public final static String ENG_ABBREV = "eng_abbrev";
        public final static String RUS_NAME = "rus";
        public final static String RUS_ABBREV = "rus_abbrev";
        public final static String GROUP_NAME = "group_name";
        public final static String LEARN = "learn";

    }

}

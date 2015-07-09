package com.example.spaceshipnavigator.Database;

import android.provider.BaseColumns;

public class ScoreDBContract {
    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_PERSON = "name";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }
}

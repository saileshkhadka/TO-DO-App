package com.example.todo.task_database;

import android.provider.BaseColumns;

public class TaskContract {   //It is used to connect to the database where db name and db version is compulsory

    public static final String DB_NAME = "com.example.todo.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}

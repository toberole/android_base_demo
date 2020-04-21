package com.xiaoge.org.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBTest {
    public void add(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        // database.insert()
    }
}

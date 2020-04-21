package com.xiaoge.org;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestContentProvider extends ContentProvider {
    public static final String TAG = TestContentProvider.class.getSimpleName();

    public static final String AUTHUORITY = "com.xiaoge.org";

    // ContentProvider的协议固定是：content
    public static final Uri TEST_QUERY = Uri.parse("content://" + AUTHUORITY + "/test_query");
    public static final Uri TEST_INSERT = Uri.parse("content://" + AUTHUORITY + "/test_insert");
    public static final Uri TEST_DELETE = Uri.parse("content://" + AUTHUORITY + "/test_delete");
    public static final Uri TEST_UPDATE = Uri.parse("content://" + AUTHUORITY + "/test_update");

    public static final int QUERY_CODE = 0;
    public static final int INSERT_CODE = 1;
    public static final int DELETE_CODE = 2;
    public static final int UPDATE_CODE = 3;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHUORITY, "test_query", QUERY_CODE);
        uriMatcher.addURI(AUTHUORITY, "test_insert", INSERT_CODE);
        uriMatcher.addURI(AUTHUORITY, "test_delete", DELETE_CODE);
        uriMatcher.addURI(AUTHUORITY, "test_update", UPDATE_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "TestContentProvider#onCreate " + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "TestContentProvider#query " + Thread.currentThread().getName() + " uri: " + String.valueOf(uri));

        switch (uriMatcher.match(uri)) {
            case QUERY_CODE:
                break;
            default:
                Log.i(TAG, "query default");
                break;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(TAG, "TestContentProvider#insert " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "TestContentProvider#delete " + Thread.currentThread().getName());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "TestContentProvider#update " + Thread.currentThread().getName());
        return 0;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("test", false);
        Log.i(TAG, "TestContentProvider#call " + Thread.currentThread().getName());
        Log.i(TAG, "TestContentProvider#call " + "method: " + method + " arg: " + arg + " extras: " + String.valueOf(extras));
        return bundle;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(TAG, "TestContentProvider#getType " + Thread.currentThread().getName());
        return null;
    }
}

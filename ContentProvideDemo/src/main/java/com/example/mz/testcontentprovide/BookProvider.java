package com.example.mz.testcontentprovide;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by MZ on 2016/8/1.
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";
    public static final String AUTHORITY = "mz.privider.test";
    private Context mContext;
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMathcer.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriMathcer.addURI(AUTHORITY,"user",USER_URI_CODE);
    }
    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMathcer.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TALBE_NAME;
                break;
        }
        return tableName;
    }
    @Override
    public boolean onCreate() {
        Log.d("BookProvider","onCreate,current thread:"+Thread.currentThread().getName());
        mContext = getContext();
        //这里执行的是主线程，一般不再这里执行耗时操作，这只是演示
        initProviderData();
        return true;
    }
    private SQLiteDatabase mDB;
  private void initProviderData(){
    mDB = new DbOpenHelper(mContext).getWritableDatabase();
    mDB.execSQL("delete from "+ DbOpenHelper.BOOK_TABLE_NAME);
    mDB.execSQL("delete from "+ DbOpenHelper.USER_TALBE_NAME);
   /* mDB.execSQL("insert into book values(3,'Android');");
    mDB.execSQL("insert into book values(4,'Ios');");
    mDB.execSQL("insert into book values(5,'Html5');");
    mDB.execSQL("insert into user values(1,'jake',1);");
    mDB.execSQL("insert into user values(2,'ljs',0);");*/
}
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d("BookProvider","query,current thread:"+Thread.currentThread().getName());
        String table = getTableName(uri);
        if(table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        return mDB.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableName(uri);
        if(table==null){
            throw new IllegalArgumentException("Unsupported URI:"+uri);
        }
        long insertResult =  mDB.insert(table,null,values);
        if (insertResult > 0) {
            Uri pUri = ContentUris.withAppendedId(BOOK_CONTENT_URI, insertResult);
            getContext().getContentResolver().notifyChange(pUri, null);
            return pUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

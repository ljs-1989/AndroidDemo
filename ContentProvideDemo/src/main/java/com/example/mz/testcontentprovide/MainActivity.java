package com.example.mz.testcontentprovide;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mz.testcontentprovide.bean.Book;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView = (TextView)this.findViewById(R.id.provider_msg);
            StringBuilder stringBuilder = new StringBuilder();
        Uri bookUri = Uri.parse("content://mz.privider.test/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计的艺术");
        getContentResolver().insert(bookUri,values);

        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"_id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.setBookId(bookCursor.getInt(0));
            book.setBookName(bookCursor.getString(1));
           stringBuilder.append(book.toString()+"\n");
            Log.d("MainActivity","query book:"+book.toString());
        }
        bookCursor.close();
        mTextView.setText(stringBuilder);
    }
}

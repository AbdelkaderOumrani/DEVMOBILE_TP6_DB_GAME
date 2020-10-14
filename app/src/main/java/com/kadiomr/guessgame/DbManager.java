package com.kadiomr.guessgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WordsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_ANSWERED = "answered";

    DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_WORD + " varchar(200) NOT NULL," +
                COLUMN_ANSWERED + " INTEGER NOT NULL);";

                sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    boolean addWord(Word word){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD, word.getWord());
        contentValues.put(COLUMN_ANSWERED, word.getAnswered());
        return db.insert(TABLE_NAME,null,contentValues)!=-1;
    }

    void wipeAllWords(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
    List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                Word w = new Word();
                w.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                w.setWord(cursor.getString(cursor.getColumnIndex(COLUMN_WORD)));
                w.setAnswered(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWERED)));
                words.add(w);
            }
            while (cursor.moveToNext());
        }
        return words;
    }

    boolean setAnswered(long rowId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ANSWERED, 1);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID+"="+rowId, null)!=-1;
    }


}

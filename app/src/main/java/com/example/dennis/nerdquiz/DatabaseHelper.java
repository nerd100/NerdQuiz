package com.example.dennis.nerdquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dennis on 25.08.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    String DB_PATH = null;
    private static String DB_NAME = "Quiz.db";
    private SQLiteDatabase myDatabase;
    private Context context;
    public static  final String DATABASE_NAME = "Quiz.db";
    public static  final String TABLE_NAME = "quiz_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "Category";
    public static  final String COL_3 = "Difficulty";
    public static  final String COL_4 = "Question";
    public static  final String COL_5 = "RightAnswer";
    public static  final String COL_6 = "FalseAnswer1";
    public static  final String COL_7 = "FalseAnswer2";
    public static  final String COL_8 = "FalseAnswer3";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }



    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            Toast.makeText(context.getApplicationContext(), "No Database", Toast.LENGTH_SHORT).show();
            try {
                SQLiteDatabase checkDB = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
                if(checkDB != null){

                    checkDB.close();

                }
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        Toast.makeText(context.getApplicationContext(), "Database", Toast.LENGTH_SHORT).show();
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Category TEXT, Difficulty TEXT, Question TEXT, RightAnswer TEXT, FalseAnswer1 TEXT," +
                " FalseAnswer2 TEXT, FalseAnswer3 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String category, String difficulty, String question, String RA, String FA1, String FA2, String FA3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, category);
        contentValues.put(COL_3, difficulty);
        contentValues.put(COL_4, question);
        contentValues.put(COL_5, RA);
        contentValues.put(COL_6, FA1);
        contentValues.put(COL_7, FA2);
        contentValues.put(COL_8, FA3);
        long result = db.insert(TABLE_NAME,null,contentValues); //insert returns -1 if fails
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String category, String diff){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Question,RightAnswer,FalseAnswer1,FalseAnswer2,FalseAnswer3" +
                " from "+ TABLE_NAME+" WHERE (Category =  \""+category+"\") AND (Difficulty=\""+diff+"\")",null);
        return res;
    }
    public Cursor getAllData(String diff){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Question,RightAnswer,FalseAnswer1,FalseAnswer2,FalseAnswer3" +
                " from "+ TABLE_NAME+" WHERE Difficulty=\""+diff+"\"",null);
        return res;
    }
}

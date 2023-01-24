package com.koliy82.prtt8.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id_food";
    public static final String COLUMN_NAME = "food_name";
    public static final String COLUMN_DETAIL = "food_detail";
    public static final String COLUMN_STUDENT = "student_fio";
    public static final String COLUMN_SCORE = "student_score";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DETAIL + " TEXT, " +
                COLUMN_STUDENT + " TEXT, " +
                COLUMN_SCORE + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addFood(FoodJSON food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DETAIL, food.getDetail());
        values.put(COLUMN_STUDENT, food.getStudent());
        values.put(COLUMN_SCORE, food.getScore());

        db.insert(TABLE_NAME, null, values);
    }
    public void deleteFood(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public void updateFood(int id, String name, String detail, String student, int score) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_DETAIL, detail);
            values.put(COLUMN_STUDENT, student);
            values.put(COLUMN_SCORE, score);
            db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public void deleteAllFood() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, null, null);
    }
    public ArrayList<FoodJSON> getAllFood() {
        ArrayList<FoodJSON> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(result.moveToFirst())
            while (result.moveToNext())
                foodList.add(new FoodJSON(result.getInt(0), result.getString(1), result.getString(2), result.getString(3), result.getInt(4)));
        result.close();
        return foodList;
    }
}

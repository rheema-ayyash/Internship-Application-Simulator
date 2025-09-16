package it372.rayyash.finalayyash;
//Rheema Ayyash
//June 3, 2025
//It372
//creates a sql database to store information submitted by user
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    //datebase and table info
    private static final String DATABASE_NAME = "internships.db";
    private static final int DATABASE_VERSION = 1;

    // table/column names
    private static final String TABLE_NAME = "applications";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_MAJOR = "major";
    private static final String COL_YEAR = "year";
    private static final String COL_SKILLS = "skills";
    private static final String COL_OTHER_SKILLS = "other_skills";
    private static final String COL_GPA = "gpa";
    private static final String COL_DATE = "availability_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table with 8 data columns + id primary key when database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_MAJOR + " TEXT, "
                + COL_YEAR + " TEXT, "
                + COL_SKILLS + " TEXT, "
                + COL_OTHER_SKILLS + " TEXT, "
                + COL_GPA + " TEXT, "
                + COL_DATE + " TEXT"
                + ")";
        db.execSQL(createTable);
    }

    // Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new internship application
    public boolean insertApplication(String name, String email, String major, String year,
                                     String skills, String otherSkills, String gpa, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, name);
        cv.put(COL_EMAIL, email);
        cv.put(COL_MAJOR, major);
        cv.put(COL_YEAR, year);
        cv.put(COL_SKILLS, skills);
        cv.put(COL_OTHER_SKILLS, otherSkills);
        cv.put(COL_GPA, gpa);
        cv.put(COL_DATE, date);

        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;  // returns true if insert successful
    }

    // Retrieve all applications as formatted strings
    public ArrayList<String> getAllApplications() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String entry = "Name: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)) + "\n"
                        + "Email: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)) + "\n"
                        + "Major: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_MAJOR)) + "\n"
                        + "Year: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_YEAR)) + "\n"
                        + "Skills: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_SKILLS)) + "\n"
                        + "Other Skills: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_OTHER_SKILLS)) + "\n"
                        + "GPA: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_GPA)) + "\n"
                        + "Available Date: " + cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                list.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}

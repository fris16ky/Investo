package com.senior.capstone.Investo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applandeo.materialcalendarview.EventDay;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Investo.db";
    public static final int DATABASE_VERSION = 2;
    private static DbHelper instance;
    private static final int DEFAULT_PROFILE_PICTURE = R.drawable.ic_launcher;
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //not sure if this is needed or not ATM
    public static synchronized DbHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating User Table
        //ADD PICTURE COLUMN***
        String CREATE_USER_TABLE = "CREATE TABLE " + Contract.User.TABLE_NAME + " (" + Contract.User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.User.COLUMN_FIRSTNAME + " TEXT, " +
                Contract.User.COLUMN_LASTNAME + " TEXT, " +
                Contract.User.COLUMN_EMAIL + " TEXT, " +
                Contract.User.COLUMN_USERNAME + " TEXT, " +
                Contract.User.COLUMN_PROFILEPIC + " BLOB, " +
                Contract.User.COLUMN_PASSWORD + " TEXT)";

        db.execSQL(CREATE_USER_TABLE);

        //Creating Budget Table
        String CREATE_BUDGET_TABLE = "CREATE TABLE " + Contract.Budget.TABLE_NAME + " (" + Contract.Budget._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Budget.COLUMN_AMOUNT + " INTEGER, " +
                Contract.Budget.COLUMN_NOTE + " TEXT, " +
                Contract.Budget.COLUMN_USERID + " TEXT, " +
                " FOREIGN KEY (" + Contract.Budget.COLUMN_USERID + ") REFERENCES " + Contract.User.TABLE_NAME + ")";

        db.execSQL(CREATE_BUDGET_TABLE);

        //Creating Bills Table
        String CREATE_BILLS_TABLE = "CREATE TABLE " + Contract.Bills.TABLE_NAME + " (" + Contract.Bills._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Bills.COLUMN_AMOUNT + " INTEGER, " +
                Contract.Bills.COLUMN_NOTE + " TEXT, " +
                Contract.Bills.COLUMN_USERID + " TEXT, " +
                Contract.Bills.COLUMN_DATE + " TEXT, " +
                " FOREIGN KEY (" + Contract.Bills.COLUMN_USERID + ") REFERENCES " + Contract.User.TABLE_NAME + ")";

        db.execSQL(CREATE_BILLS_TABLE);

        //Creating Daily Transaction Table
        String CREATE_DAILY_TRANS_TABLE = "CREATE TABLE " + Contract.DailyTrans.TABLE_NAME + " (" + Contract.DailyTrans._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.DailyTrans.COLUMN_AMOUNT + " INTEGER, " +
                Contract.DailyTrans.COLUMN_NOTE + " TEXT, " +
                Contract.DailyTrans.COLUMN_USERID + " TEXT, " +
                " FOREIGN KEY (" + Contract.DailyTrans.COLUMN_USERID + ") REFERENCES " + Contract.User.TABLE_NAME + ")";

        db.execSQL(CREATE_DAILY_TRANS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.User.COLUMN_FIRSTNAME, user.getFirst_name());
        cv.put(Contract.User.COLUMN_LASTNAME, user.getLast_name());
        cv.put(Contract.User.COLUMN_EMAIL, user.getEmail());
        cv.put(Contract.User.COLUMN_USERNAME, user.getUsername());
        cv.put(Contract.User.COLUMN_PASSWORD, user.getPassword());
        cv.put(Contract.User.COLUMN_PROFILEPIC, user.getProfilePic());

        long insert = db.insert(Contract.User.TABLE_NAME, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("Range")
    public ArrayList<User> getUser() {
        ArrayList<User> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.User.TABLE_NAME;
        Cursor cursor = db.rawQuery(SQL, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setId(cursor.getInt(cursor.getColumnIndex(Contract.User._ID)));
                user.setFirst_name(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_FIRSTNAME)));
                user.setLast_name(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_LASTNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_EMAIL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_PASSWORD)));

                returnList.add(user);

            } while (cursor.moveToNext());

        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    @SuppressLint("Range")
    public ArrayList<User> getUserName(String username) {
        ArrayList<User> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(SQL, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setFirst_name(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_FIRSTNAME)));
                user.setLast_name(cursor.getString(cursor.getColumnIndex(Contract.User.COLUMN_LASTNAME)));

                returnList.add(user);

            } while (cursor.moveToNext());

        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    //this method returns the users first name as a string by passing its username as a parameter to the SQL query
    public String getFName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.User.COLUMN_FIRSTNAME + " FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(SQL, new String[]{username}, null);

        String id = null;

        if (cursor.moveToFirst()) {
            id = cursor.getString(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return id;
    }

    public String getLName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.User.COLUMN_LASTNAME + " FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(SQL, new String[]{username}, null);

        String id = null;

        if (cursor.moveToFirst()) {
            id = cursor.getString(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return id;
    }

    public String getUserID(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.User._ID + " FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(SQL, new String[] {username});

        String id = null;

        if (cursor.moveToFirst()) {
            id = cursor.getString(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return id;
    }

    public String getUserEmail(String userid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.User.COLUMN_EMAIL + " FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User._ID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[]{userid});

        String email = null;

        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return email;
    }

    public String getUserPass(String userid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.User.COLUMN_PASSWORD + " FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User._ID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        String pass = null;
        if(cursor.moveToFirst()) {
            pass = cursor.getString(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return pass;
    }

    public void updateUserPass(String userid, String newPass) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.User.COLUMN_PASSWORD, newPass);

        db.update(Contract.User.TABLE_NAME, cv, Contract.User._ID + " =?", new String[]{userid});
        db.close();
    }

    public boolean verifyUserName(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String SQL = "SELECT * FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(SQL, new String[]{username});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String SQL = "SELECT * FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User.COLUMN_USERNAME + " = ? and " + Contract.User.COLUMN_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(SQL, new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;

        } else {
            return false;
        }
    }

    public boolean addBudget(@NonNull Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Budget.COLUMN_USERID, budget.getUserID());
        cv.put(Contract.Budget.COLUMN_AMOUNT, budget.getAmount());
        cv.put(Contract.Budget.COLUMN_NOTE, budget.getNote());

        long insert = db.insert(Contract.Budget.TABLE_NAME, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteBudget(String itemID) {
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "DELETE FROM " + Contract.Budget.TABLE_NAME + " WHERE " + Contract.Budget._ID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[] {itemID});
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    //getting all budget object to array list
    @SuppressLint("Range")
    public ArrayList<Budget> getBudget(String userid) {
        ArrayList<Budget> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.Budget.TABLE_NAME + " WHERE " + Contract.Budget.COLUMN_USERID + " =?";
        //String SQL = "SELECT * FROM " + Contract.Budget.TABLE_NAME;
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();

                budget.setId(cursor.getInt(cursor.getColumnIndex(Contract.Budget._ID)));
                budget.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Budget.COLUMN_USERID)));
                budget.setAmount(cursor.getInt(cursor.getColumnIndex(Contract.Budget.COLUMN_AMOUNT)));
                budget.setNote(cursor.getString(cursor.getColumnIndex(Contract.Budget.COLUMN_NOTE)));

                returnList.add(budget);

            } while (cursor.moveToNext());

        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    public int getUserBudgetTotal(String userid) {
        int total = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT SUM(amount) FROM " + Contract.Budget.TABLE_NAME + " WHERE " + Contract.Budget.COLUMN_USERID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return total;
    }

    public boolean addBill(Bills bill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Bills.COLUMN_USERID, bill.getUserID());
        cv.put(Contract.Bills.COLUMN_AMOUNT, bill.getAmount());
        cv.put(Contract.Bills.COLUMN_NOTE, bill.getNote());
        cv.put(Contract.Bills.COLUMN_DATE, bill.getDate());

        long insert = db.insert(Contract.Bills.TABLE_NAME, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteBill(String itemID) {
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "DELETE FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills._ID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[] {itemID});
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    //old getBills that doesn't include due date
    @SuppressLint("Range")
    public ArrayList<Bills> getBills(String userid) {
        ArrayList<Bills> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_USERID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            do {
                Bills bill = new Bills();

                bill.setId(cursor.getInt(cursor.getColumnIndex(Contract.Bills._ID)));
                bill.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Bills.COLUMN_USERID)));
                bill.setAmount(cursor.getInt(cursor.getColumnIndex(Contract.Bills.COLUMN_AMOUNT)));
                bill.setNote(cursor.getString(cursor.getColumnIndex(Contract.Bills.COLUMN_NOTE)));

                returnList.add(bill);
            } while (cursor.moveToNext());
        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    @SuppressLint("Range")
    public ArrayList<Bills> getBillsV2(String userid) {
        ArrayList<Bills> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_USERID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            do {
                Bills bill = new Bills();

                bill.setId(cursor.getInt(cursor.getColumnIndex(Contract.Bills._ID)));
                bill.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.Bills.COLUMN_USERID)));
                bill.setAmount(cursor.getInt(cursor.getColumnIndex(Contract.Bills.COLUMN_AMOUNT)));
                bill.setNote(cursor.getString(cursor.getColumnIndex(Contract.Bills.COLUMN_NOTE)));
                bill.setDate(cursor.getString(cursor.getColumnIndex(Contract.Bills.COLUMN_DATE)));

                returnList.add(bill);
            } while (cursor.moveToNext());
        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    public int getUserBillTotal(String userid) {
        int total = 0;

        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT SUM(amount) FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_USERID + "=?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return total;
    }

    @SuppressLint("Range")
    public List<String> getBillDates(String userid) {
        List<String> billDates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT " + Contract.Bills.COLUMN_DATE + " FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_USERID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[]{userid});

        while (cursor.moveToNext()) {
            String billDue = cursor.getString(0);
            billDates.add(billDue);
        }

        cursor.close();
        db.close();

        return billDates;
    }

    @SuppressLint("Range")
    public String getBillAmount(String eventDay, String userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT " + Contract.Bills.COLUMN_AMOUNT + " FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_DATE + " =? " + " AND " + Contract.Bills.COLUMN_USERID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[]{eventDay, userid});

        String amount = null;
        if (cursor.moveToNext()) {
            amount = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return amount;
    }

    public String getBillNote(String eventDay, String userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "SELECT " + Contract.Bills.COLUMN_NOTE + " FROM " + Contract.Bills.TABLE_NAME + " WHERE " + Contract.Bills.COLUMN_DATE + " =? " + " AND " + Contract.Bills.COLUMN_USERID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[]{eventDay, userid});

        String note = null;
        if (cursor.moveToNext()) {
            note = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return note;
    }

    public boolean addDailyTrans(DailyTrans dailyTrans) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.DailyTrans.COLUMN_USERID, dailyTrans.getUserID());
        cv.put(Contract.DailyTrans.COLUMN_AMOUNT, dailyTrans.getAmount());
        cv.put(Contract.DailyTrans.COLUMN_NOTE, dailyTrans.getNote());

        long insert = db.insert(Contract.DailyTrans.TABLE_NAME, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteDailyTrans(String itemID) {
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "DELETE FROM " + Contract.DailyTrans.TABLE_NAME + " WHERE " + Contract.DailyTrans._ID + " =?";

        Cursor cursor = db.rawQuery(SQL, new String[] {itemID});
        if (cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }

    }

    public int getUserDailyTransTotal(String userid) {
        int total = 0;

        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT SUM(amount) FROM " + Contract.DailyTrans.TABLE_NAME + " WHERE " + Contract.DailyTrans.COLUMN_USERID + " =?" ;
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return total;
    }

    @SuppressLint("Range")
    public ArrayList<DailyTrans> getDailyTrans(String userid) {
        ArrayList<DailyTrans> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.DailyTrans.TABLE_NAME + " WHERE " + Contract.DailyTrans.COLUMN_USERID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        if (cursor.moveToFirst()) {
            do {
                DailyTrans dailyTrans = new DailyTrans();

                dailyTrans.setId(cursor.getInt(cursor.getColumnIndex(Contract.DailyTrans._ID)));
                dailyTrans.setUserID(cursor.getInt(cursor.getColumnIndex(Contract.DailyTrans.COLUMN_USERID)));
                dailyTrans.setAmount(cursor.getInt(cursor.getColumnIndex(Contract.DailyTrans.COLUMN_AMOUNT)));
                dailyTrans.setNote(cursor.getString(cursor.getColumnIndex(Contract.DailyTrans.COLUMN_NOTE)));

                returnList.add(dailyTrans);
            } while (cursor.moveToNext());
        } else {
            //nothing to do ATM
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return returnList;
    }

    public void updateProfilePicture (String userid, Bitmap image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.User.COLUMN_PROFILEPIC, getBytes(image));

        db.update(Contract.User.TABLE_NAME, cv, Contract.User._ID + " =?", new String[]{userid});
        //db.close();
    }

    public Bitmap getProfilePicture(String userid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL = "SELECT * FROM " + Contract.User.TABLE_NAME + " WHERE " + Contract.User._ID + " =?";
        Cursor cursor = db.rawQuery(SQL, new String[] {userid});

        Bitmap bitmap = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex(Contract.User.COLUMN_PROFILEPIC));
            bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }

        //closing cursor connection to DB
        //closing connection DB
        cursor.close();
        db.close();

        return bitmap;
    }

    //convert Bitmap to byte array for updateProfilePicture func
    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
package com.senior.capstone.Investo;

//container for constants for SQLite operations
//easier to change constant names in one place instead of in 20 different classes

//BaseColumns is an interface: provides ID column for table automatically as _id

import android.provider.BaseColumns;

public class Contract {

    //prevents object creation as this is to hold constants
    private Contract() {}

    //User table static attributes
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_LASTNAME = "lastname";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PROFILEPIC = "profilePicture";
    }

    //Budget table static attributes
    public static class Budget implements BaseColumns {
        public static final String TABLE_NAME = "Budget";
        public static final String COLUMN_AMOUNT = "amount";
        //foreign key
        public static final String COLUMN_USERID = "userID";
        public static final String COLUMN_NOTE = "note";
    }

    //Bills table static attributes
    public static class Bills implements BaseColumns {
        public static final String TABLE_NAME = "Bills";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_USERID = "userID";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_DATE = "date";
    }

    //Daily transactions table static attributes
    public static class DailyTrans implements BaseColumns {
        public static final String TABLE_NAME = "DailyTrans";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_USERID = "userID";
        public static final String COLUMN_NOTE = "note";
    }
}

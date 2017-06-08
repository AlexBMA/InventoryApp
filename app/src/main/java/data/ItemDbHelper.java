package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexandru on 6/8/2017.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;


    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ItemContact.ItemEntry.TABLE_NAME + " ("
                + ItemContact.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + ItemContact.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ItemContact.ItemEntry.COLUMN_STOCK + " INTEGER NOT NULL, "
                + ItemContact.ItemEntry.COLUMN_VALUE + " INTEGER NOT NULL, "
                + ItemContact.ItemEntry.COLUMN_SALES + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

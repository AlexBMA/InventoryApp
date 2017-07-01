package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexandru on 6/8/2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + InventoryContact.ItemEntry.TABLE_NAME + " ("
                + InventoryContact.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + InventoryContact.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + InventoryContact.ItemEntry.COLUMN_STOCK + " INTEGER NOT NULL, "
                + InventoryContact.ItemEntry.COLUMN_VALUE + " INTEGER NOT NULL, "
                + InventoryContact.ItemEntry.COLUMN_SALES + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryContact.ItemEntry.COLUMN_IMG_BYTES + " BLOB NOT NULL );";

        db.execSQL(SQL_CREATE_ITEM_TABLE);


        String SOL_CREATE_SUPPLIER_TABLE = "CREATE TABLE " + InventoryContact.SupplierEntry.TABLE_NAME + " ("
                + InventoryContact.SupplierEntry.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + InventoryContact.SupplierEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + InventoryContact.SupplierEntry.COLUMN_EMAIL + " TEXT NOT NULL );";

        db.execSQL(SOL_CREATE_SUPPLIER_TABLE);


        String SQL_CREATE_ITEM_SUPPLIER_TABLE = "CREATE TABLE " + InventoryContact.ItemSupplierEntry.TABLE_NAME + " ("
                + InventoryContact.ItemSupplierEntry.ID_ITEM + " INTEGER , "
                + InventoryContact.ItemSupplierEntry.ID_SUPPLIER + " INTEGER );";

        db.execSQL(SQL_CREATE_ITEM_SUPPLIER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

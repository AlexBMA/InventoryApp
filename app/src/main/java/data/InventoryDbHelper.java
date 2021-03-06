package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import constactpack.AppConstants;

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

        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + InventoryAppTable.ItemEntry.TABLE_NAME + " ("
                + InventoryAppTable.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + InventoryAppTable.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + InventoryAppTable.ItemEntry.COLUMN_STOCK + " INTEGER NOT NULL, "
                + InventoryAppTable.ItemEntry.COLUMN_VALUE + " INTEGER NOT NULL, "
                + InventoryAppTable.ItemEntry.COLUMN_SALES + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryAppTable.ItemEntry.COLUMN_IMG_BYTES + " BLOB NOT NULL );";

        db.execSQL(SQL_CREATE_ITEM_TABLE);


        String SOL_CREATE_SUPPLIER_TABLE = "CREATE TABLE " + InventoryAppTable.SupplierEntry.TABLE_NAME + " ("
                + InventoryAppTable.SupplierEntry.ID + "  INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + InventoryAppTable.SupplierEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + InventoryAppTable.SupplierEntry.COLUMN_EMAIL + " TEXT NOT NULL );";

        db.execSQL(SOL_CREATE_SUPPLIER_TABLE);


        String SQL_CREATE_ITEM_SUPPLIER_TABLE = "CREATE TABLE " + InventoryAppTable.ItemSupplierEntry.TABLE_NAME + " ("
                + InventoryAppTable.ItemSupplierEntry.ID_ITEM + " INTEGER , "
                + InventoryAppTable.ItemSupplierEntry.ID_SUPPLIER + " INTEGER );";


        db.execSQL(SQL_CREATE_ITEM_SUPPLIER_TABLE);


        String SQL_CREATE_ITEM_SUPPLIER_VIEW = "CREATE VIEW " + AppConstants.ITEM_SUPPLIER_VIEW + " AS "
                + " SELECT " + InventoryAppTable.SupplierEntry.COLUMN_NAME + ", "
                + InventoryAppTable.SupplierEntry.COLUMN_EMAIL + ", "
                + InventoryAppTable.SupplierEntry.ID + ", "
                + InventoryAppTable.ItemSupplierEntry.ID_SUPPLIER + ", "
                + InventoryAppTable.ItemSupplierEntry.ID_ITEM
                + " FROM " + InventoryAppTable.SupplierEntry.TABLE_NAME + ", "
                + InventoryAppTable.ItemSupplierEntry.TABLE_NAME;


        db.execSQL(SQL_CREATE_ITEM_SUPPLIER_VIEW);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

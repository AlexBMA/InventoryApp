package providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import data.InventoryAppTable;
import data.InventoryDbHelper;


/**
 * Created by Alexandru on 7/11/2017.
 */

public class ItemSupplierProvider extends ContentProvider {


    public static final String LOG_TAG = SupplierProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    /**
     * URI matcher code for the content URI for the items_supplier table
     */
    private static final int ITEM_SUPPLIERS = 100;
    /**
     * URI matcher code for the content URI for a single items_supplier in the pets table
     */
    private static final int ITEM_SUPPLIER_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(InventoryAppTable.ItemSupplierEntry.CONTENT_AUTHORITY, InventoryAppTable.ItemSupplierEntry.PATH_ITEMS, ITEM_SUPPLIERS);
        sUriMatcher.addURI(InventoryAppTable.ItemSupplierEntry.CONTENT_AUTHORITY, InventoryAppTable.ItemSupplierEntry.PATH_ITEMS + "/#", ITEM_SUPPLIER_ID);
    }

    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newId = database.insert(InventoryAppTable.ItemSupplierEntry.TABLE_NAME, null, values);

        if (newId == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, newId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);


        switch (match) {
            case ITEM_SUPPLIERS:
                int rezDeleteAll = database.delete(InventoryAppTable.ItemSupplierEntry.TABLE_NAME, null, null);
                if (rezDeleteAll != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteAll;
            case ITEM_SUPPLIER_ID:
                selection = InventoryAppTable.ItemSupplierEntry.ID_SUPPLIER + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int rezDeleteById = database.delete(InventoryAppTable.ItemSupplierEntry.TABLE_NAME, selection, selectionArgs);
                if (rezDeleteById != 0) getContext().getContentResolver().notifyChange(uri, null);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

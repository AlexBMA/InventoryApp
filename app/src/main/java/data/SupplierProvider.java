package data;

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

/**
 * Created by Alexandru on 6/17/2017.
 */

public class SupplierProvider extends ContentProvider {

    public static final String LOG_TAG = SupplierProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    /**
     * URI matcher code for the content URI for the items table
     */
    private static final int SUPPLIERS = 100;
    /**
     * URI matcher code for the content URI for a single item in the pets table
     */
    private static final int SUPPLIER_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(InventoryAppTable.SupplierEntry.CONTENT_AUTHORITY, InventoryAppTable.SupplierEntry.PATH_ITEMS, SUPPLIERS);
        sUriMatcher.addURI(InventoryAppTable.SupplierEntry.CONTENT_AUTHORITY, InventoryAppTable.SupplierEntry.PATH_ITEMS + "/#", SUPPLIER_ID);
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
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case SUPPLIERS:
                cursor = database.query(InventoryAppTable.SupplierEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SUPPLIER_ID:
                selection = InventoryAppTable.SupplierEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryAppTable.SupplierEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SUPPLIERS:
                return InventoryAppTable.SupplierEntry.CONTENT_LIST_TYPE;
            case SUPPLIER_ID:
                return InventoryAppTable.SupplierEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newId = database.insert(InventoryAppTable.SupplierEntry.TABLE_NAME, null, values);

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
            case SUPPLIERS:
                int rezDeleteAll = database.delete(InventoryAppTable.SupplierEntry.TABLE_NAME, null, null);
                if (rezDeleteAll != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteAll;

            //case SUPPLIER_ID:
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

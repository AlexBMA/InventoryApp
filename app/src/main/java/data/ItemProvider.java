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
 * Created by Alexandru on 6/8/2017.
 */

public class ItemProvider extends ContentProvider {

    public static final String LOG_TAG = ItemProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    /**
     * URI matcher code for the content URI for the items table
     */
    private static final int ITEMS = 100;

    /**
     * URI matcher code for the content URI for a single item in the pets table
     */
    private static final int ITEM_ID = 101;


    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(InventoryAppTable.ItemEntry.CONTENT_AUTHORITY, InventoryAppTable.ItemEntry.PATH_ITEMS, ITEMS);
        sUriMatcher.addURI(InventoryAppTable.ItemEntry.CONTENT_AUTHORITY, InventoryAppTable.ItemEntry.PATH_ITEMS + "/#", ITEM_ID);
    }

    private InventoryDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                cursor = database.query(InventoryAppTable.ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = InventoryAppTable.ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryAppTable.ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;


        //Cursor cursor = database.query(InventoryAppTable.ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        // cursor.setNotificationUri(getContext().getContentResolver(), uri);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                return InventoryAppTable.ItemEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return InventoryAppTable.ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long newId = database.insert(InventoryAppTable.ItemEntry.TABLE_NAME, null, values);

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
            case ITEMS:
                int rezDeleteAll = database.delete(InventoryAppTable.ItemEntry.TABLE_NAME, null, null);
                if (rezDeleteAll != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteAll;
            case ITEM_ID:
                selection = InventoryAppTable.ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int rezDeleteById = database.delete(InventoryAppTable.ItemEntry.TABLE_NAME, selection, selectionArgs);
                if (rezDeleteById != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteById;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {


        final int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                return updatePet(uri, values, selection, selectionArgs);
            case ITEM_ID:
                Log.e("##", "here " + String.valueOf(ContentUris.parseId(uri)));

                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = InventoryAppTable.ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int nrRows = database.update(InventoryAppTable.ItemEntry.TABLE_NAME, values, selection, selectionArgs);

        if (nrRows != 0) getContext().getContentResolver().notifyChange(uri, null);

        return nrRows;

    }


}

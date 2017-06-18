package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alexandru on 6/8/2017.
 */

public class ItemProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int ITEMS = 100;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int ITEM_ID = 101;


    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(InventoryContact.CONTENT_AUTHORITY, InventoryContact.PATH_ITEMS, ITEMS);
        sUriMatcher.addURI(InventoryContact.CONTENT_AUTHORITY, InventoryContact.PATH_ITEMS + "/#", ITEM_ID);
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

        // cursor = database.query(PetContact.PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        Cursor cursor = database.query(InventoryContact.ItemEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        //cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
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

        database.insert(InventoryContact.ItemEntry.TABLE_NAME, null, values);

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEMS:
                int rezDeleteAll = database.delete(InventoryContact.ItemEntry.TABLE_NAME, null, null);
                if (rezDeleteAll != 0) getContext().getContentResolver().notifyChange(uri, null);
                return rezDeleteAll;
        }


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        return 0;
    }
}

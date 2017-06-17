package data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alexandru on 6/8/2017.
 */

public class ItemProvider extends ContentProvider {

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

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        return 0;
    }
}

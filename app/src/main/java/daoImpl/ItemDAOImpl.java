package daoImpl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import dao.ItemDAO;
import data.InventoryAppTable;
import model.Item;

;

/**
 * Created by Alexandru on 7/5/2017.
 */

public class ItemDAOImpl implements ItemDAO<Item> {

    @Override
    public void deleteAllItems(ContentResolver contentResolver, Uri uri) {

        contentResolver.delete(uri, null, null);
    }

    @Override
    public void deleteItem(ContentResolver contentResolver, Uri uri, long id) {

        String selection = InventoryAppTable.ItemEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};
        contentResolver.delete(uri, selection, selectionArgs);

    }

    @Override
    public Uri insertItem(ContentResolver contentResolver, Uri uri, Item item) {

        ContentValues values = new ContentValues();
        transformItemInValues(item, values);
        Uri newItemUri = contentResolver.insert(uri, values);

        return newItemUri;
    }

    @Override
    public void updateItem(ContentResolver contentResolver, Uri uri, long id, Item item) {

        ContentValues values = new ContentValues();
        transformItemInValues(item, values);
        String selection = InventoryAppTable.ItemEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};
        contentResolver.update(uri, values, selection, selectionArgs);
    }

    private void transformItemInValues(Item item, ContentValues values) {
        values.put(InventoryAppTable.ItemEntry.COLUMN_NAME, item.getName());
        values.put(InventoryAppTable.ItemEntry.COLUMN_SALES, item.getSales());
        values.put(InventoryAppTable.ItemEntry.COLUMN_VALUE, item.getValue());
        values.put(InventoryAppTable.ItemEntry.COLUMN_STOCK, item.getStock());
        values.put(InventoryAppTable.ItemEntry.COLUMN_IMG_BYTES, item.getImgBytes());
    }

}

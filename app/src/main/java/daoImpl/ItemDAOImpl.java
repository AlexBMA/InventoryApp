package daoImpl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import dao.ItemDAO;
import data.InventoryAppTable;
import model.Item;


/**
 * Created by Alexandru on 7/5/2017.
 */

public class ItemDAOImpl implements ItemDAO<Item> {

    @Override
    public Item getItemById(ContentResolver contentResolver, Uri uri, long id) {


        String selection = InventoryAppTable.ItemEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};
        Cursor c = contentResolver.query(uri, null, selection, selectionArgs, null);

        return transformCursorToObject(c);
    }

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

    private Item transformCursorToObject(Cursor c) {
        if (c.moveToFirst()) {

            long id = c.getLong(c.getColumnIndex(InventoryAppTable.ItemEntry._ID));
            String name = c.getString(c.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_NAME));
            int value = c.getInt(c.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_VALUE));
            int stock = c.getInt(c.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_STOCK));
            int sales = c.getInt(c.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_SALES));
            byte[] bytes = c.getBlob(c.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_IMG_BYTES));

            Item item = new Item();
            item.setId(id);
            item.setImgBytes(bytes);
            item.setPrice(value);
            item.setSales(sales);
            item.setQuantity(stock);
            item.setName(name);

            return item;

        } else return null;

    }

    private void transformItemInValues(Item item, ContentValues values) {
        values.put(InventoryAppTable.ItemEntry.COLUMN_NAME, item.getName());
        values.put(InventoryAppTable.ItemEntry.COLUMN_SALES, item.getSales());
        values.put(InventoryAppTable.ItemEntry.COLUMN_VALUE, item.getValue());
        values.put(InventoryAppTable.ItemEntry.COLUMN_STOCK, item.getStock());
        values.put(InventoryAppTable.ItemEntry.COLUMN_IMG_BYTES, item.getImgBytes());
    }


}

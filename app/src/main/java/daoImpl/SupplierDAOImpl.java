package daoImpl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import dao.SupplierDAO;
import data.InventoryAppTable;
import model.Supplier;

/**
 * Created by Alexandru on 7/8/2017.
 */

public class SupplierDAOImpl implements SupplierDAO<Supplier> {

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
    public Uri insertItem(ContentResolver contentResolver, Uri uri, Supplier supplier) {

        ContentValues values = new ContentValues();
        transformItemInValues(supplier, values);
        Uri newItemUri = contentResolver.insert(uri, values);

        return newItemUri;
    }

    @Override
    public void updateItem(ContentResolver contentResolver, Uri uri, long id, Supplier supplier) {

        ContentValues values = new ContentValues();
        transformItemInValues(supplier, values);
        String selection = InventoryAppTable.ItemEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};
        contentResolver.update(uri, values, selection, selectionArgs);

    }

    private void transformItemInValues(Supplier supplier, ContentValues values) {

        values.put(InventoryAppTable.SupplierEntry.COLUMN_NAME, supplier.getName());
        values.put(InventoryAppTable.SupplierEntry.COLUMN_EMAIL, supplier.getEmail());

    }
}

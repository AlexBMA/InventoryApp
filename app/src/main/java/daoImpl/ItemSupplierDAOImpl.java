package daoImpl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import dao.ItemSupplierDAO;
import data.InventoryAppTable;
import model.ItemSupplier;

/**
 * Created by Alexandru on 7/11/2017.
 */

public class ItemSupplierDAOImpl implements ItemSupplierDAO<ItemSupplier> {


    @Override
    public void deleteAllItems(ContentResolver contentResolver, Uri uri) {

    }

    @Override
    public void deleteItem(ContentResolver contentResolver, Uri uri, long id) {

    }

    @Override
    public Uri insertItem(ContentResolver contentResolver, Uri uri, ItemSupplier item) {

        ContentValues values = new ContentValues();
        transformItemInValues(item, values);
        Uri newItemUri = contentResolver.insert(uri, values);

        return newItemUri;
    }

    @Override
    public void updateItem(ContentResolver contentResolver, Uri uri, long id, ItemSupplier item) {

    }

    private void transformItemInValues(ItemSupplier itemSupplier, ContentValues values) {
        values.put(InventoryAppTable.ItemSupplierEntry.ID_ITEM, itemSupplier.getIdItem());
        values.put(InventoryAppTable.ItemSupplierEntry.ID_SUPPLIER, itemSupplier.getIdSupplier());
    }
}

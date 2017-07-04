package customListiner;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import data.InventoryContact;
import model.Item;




/**
 * Created by Alexandru on 7/3/2017.
 */

public class CustomSaleButtonListener implements View.OnClickListener {

    private Item tempItem;
    private Context context;


    public CustomSaleButtonListener(Item tempItem, Context context) {

        this.tempItem = tempItem;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        int stock = tempItem.getStock();
        int sales = tempItem.getSales();
        if (stock > 0) {
            stock = stock - 1;
            tempItem.setQuantity(stock);
            sales = sales + 1;
            tempItem.setSales(sales);
            updateDatabase();
        }
    }

    private void updateDatabase() {

        long id = tempItem.getId();
        Uri editUri = Uri.withAppendedPath(InventoryContact.ItemEntry.CONTENT_URI, id + "");
        String selection = InventoryContact.ItemEntry._ID + " = ?";
        String[] selectionArgs = {id + ""};

        ContentValues values = new ContentValues();
        setDataForInsert(tempItem, values);

        context.getContentResolver().update(editUri, values, selection, selectionArgs);
    }

    private void setDataForInsert(Item item, ContentValues values) {
        values.put(InventoryContact.ItemEntry.COLUMN_NAME, item.getName());
        values.put(InventoryContact.ItemEntry.COLUMN_SALES, item.getSales());
        values.put(InventoryContact.ItemEntry.COLUMN_VALUE, item.getValue());
        values.put(InventoryContact.ItemEntry.COLUMN_STOCK, item.getStock());
        values.put(InventoryContact.ItemEntry.COLUMN_IMG_BYTES, item.getImgBytes());
    }


}

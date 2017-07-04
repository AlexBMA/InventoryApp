package customListiner;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import data.InventoryContact;
import model.Item;




/**
 * Created by Alexandru on 7/3/2017.
 */

public class CustomSaleButtonListener implements View.OnClickListener {


    Context context;
    private String msg;
    private TextView textView;
    private long id;
    private int price;
    private String name;
    private byte[] bytes;
    private int sales;
    private int stock;


    public CustomSaleButtonListener(int stock, int sales, String msg, TextView textView) {
        this.stock = stock;
        this.sales = sales;
        this.msg = msg;
        this.textView = textView;

    }

    @Override
    public void onClick(View v) {

        Log.e("TAG Stock", stock + " ");
        Log.e("TAG Msg", msg);

        if (stock > 0) {
            stock = stock - 1;
            sales = sales + 1;
        }
        textView.setText(msg + " " + stock);
        updateDatabase();

    }

    private void updateDatabase() {
        Item tempItem = new Item();
        tempItem.setId(id);
        tempItem.setImgBytes(bytes);
        tempItem.setSales(sales);
        tempItem.setPrice(price);
        tempItem.setQuantity(stock);
        tempItem.setName(name);

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


    public void setContext(Context context) {
        this.context = context;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }


}

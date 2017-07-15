package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alexandru.inventoryapp.R;

import customListiner.CustomSaleButtonListener;
import data.InventoryAppTable;
import model.Item;


/**
 * Created by Alexandru on 6/18/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {


    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView tvName = (TextView) view.findViewById(R.id.text_view_product_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.text_view_product_price);
        TextView tvQuantity = (TextView) view.findViewById(R.id.text_view_product_quantity);

        Button button = (Button) view.findViewById(R.id.button_sale);

        String name = cursor.getString(cursor.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_VALUE));
        int quantity = cursor.getInt(cursor.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_STOCK));
        int sales = cursor.getInt(cursor.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_SALES));
        long id = cursor.getLong(cursor.getColumnIndex(InventoryAppTable.ItemEntry._ID));
        byte[] bytes = cursor.getBlob(cursor.getColumnIndex(InventoryAppTable.ItemEntry.COLUMN_IMG_BYTES));

        tvName.setText(context.getString(R.string.text_view_name) + ": " + name);
        tvPrice.setText(context.getString(R.string.text_view_price) + ": " + price);
        tvQuantity.setText(context.getString(R.string.text_view_stock) + ": " + quantity);


        Item tempItem = new Item();
        tempItem.setId(id);
        tempItem.setPrice(price);
        tempItem.setImgBytes(bytes);
        tempItem.setQuantity(quantity);
        tempItem.setSales(sales);
        tempItem.setName(name);

        CustomSaleButtonListener saleListener = new CustomSaleButtonListener(tempItem, context);
        button.setOnClickListener(saleListener);


    }


}

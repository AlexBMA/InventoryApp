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
import data.InventoryContact;


/**
 * Created by Alexandru on 6/18/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {


        TextView tvName = (TextView) view.findViewById(R.id.text_view_product_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.text_view_product_price);
        TextView tvQuantity = (TextView) view.findViewById(R.id.text_view_product_quantity);
        TextView tvSales = (TextView) view.findViewById(R.id.text_view_item_stock);
        Button button = (Button) view.findViewById(R.id.button_sale);

        String name = cursor.getString(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_VALUE));
        int quantity = cursor.getInt(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_STOCK));
        int sales = cursor.getInt(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_SALES));

        tvName.setText(context.getString(R.string.text_view_name) + ": " + name);
        tvPrice.setText(context.getString(R.string.text_view_price) + ": " + price);
        tvQuantity.setText(context.getString(R.string.text_view_stock) + ": " + quantity);
        tvSales.setText(context.getString(R.string.text_view_sales) + ": " + sales);

        //Log.e("TAG Q", quantity + "");
        //Log.e("TAG M", context.getString(R.string.text_view_stock) + "");

        CustomSaleButtonListener saleListener = new CustomSaleButtonListener(quantity, sales, context.getString(R.string.text_view_stock), tvQuantity, tvSales);
        button.setOnClickListener(saleListener);


    }
}

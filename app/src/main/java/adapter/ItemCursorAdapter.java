package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alexandru.inventoryapp.R;

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
    public void bindView(View view, Context context, Cursor cursor) {


        TextView tvName = (TextView) view.findViewById(R.id.text_view_product_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.text_view_product_price);
        TextView tvQuantity = (TextView) view.findViewById(R.id.text_view_product_quantity);

        String name = cursor.getString(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_VALUE));
        int quantity = cursor.getInt(cursor.getColumnIndex(InventoryContact.ItemEntry.COLUMN_STOCK));

        tvName.setText(name);
        tvPrice.setText(price + "");
        tvQuantity.setText(quantity + "");

    }
}

package adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alexandru.inventoryapp.R;

import data.InventoryAppTable;


/**
 * Created by Alexandru on 7/8/2017.
 */

public class SupplierCursorAdapter extends CursorAdapter {

    public SupplierCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.supplier, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView supplierName = (TextView) view.findViewById(R.id.text_view_supplier_name);
        TextView supplierEmail = (TextView) view.findViewById(R.id.text_view_supplier_email);


        if (cursor != null) {
            String name = cursor.getString(cursor.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_EMAIL));

            supplierName.setText(name);
            supplierEmail.setText(email);
        } else {
            Log.e("C", "cursor is null");
        }

    }
}

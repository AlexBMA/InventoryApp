package adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alexandru.inventoryapp.R;


/**
 * Created by Alexandru on 7/8/2017.
 */

public class SupplierCursorAdapter extends CursorAdapter {

    public SupplierCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView supplierName = (TextView) view.findViewById(R.id.edit_text_name_supplier);
        TextView supplierEmail = (TextView) view.findViewById(R.id.edit_text_email_supplier);


        supplierName.setText(context.getString(R.string.text_view_name_supplier) + " ");
        supplierEmail.setText(context.getString(R.string.text_view_email_supplier) + " ");
    }
}

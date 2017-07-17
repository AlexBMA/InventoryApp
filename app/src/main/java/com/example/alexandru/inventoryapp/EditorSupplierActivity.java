package com.example.alexandru.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import constactpack.AppConstants;
import dao.ItemSupplierDAO;
import dao.SupplierDAO;
import daoImpl.ItemSupplierDAOImpl;
import daoImpl.SupplierDAOImpl;
import data.InventoryAppTable;
import model.ItemSupplier;
import model.Supplier;

public class EditorSupplierActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_INDEX = 1;

    private EditText mNameSupplier;
    private EditText mEmailSupplier;

    private String LOG_TAG = "ESA";

    private Uri mItemUri;
    private Uri mSelectedSupplierUri;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_editor);


        Intent intent = getIntent();
        mItemUri = intent.getData();
        if (mItemUri != null) {
            id = intent.getLongExtra(AppConstants.ID_ITEM, -1);

            String uriSupplierString = intent.getStringExtra(AppConstants.SUPPLIER_URI);
            if (uriSupplierString != null) {
                setTitle(R.string.edit_mode_supplier);
                mSelectedSupplierUri = Uri.parse(uriSupplierString);

                Button button = (Button) findViewById(R.id.b_add_supplier_now);
                button.setText(R.string.update_supplier);

            } else {

                setTitle(R.string.add_mode_supplier);
            }
        }

        mNameSupplier = (EditText) findViewById(R.id.et_name_supplier);
        mEmailSupplier = (EditText) findViewById(R.id.et_email_supplier);

        getLoaderManager().initLoader(LOADER_INDEX, null, this);
    }


    public void addOrUpdateSupplier(View view) {

        String name = mNameSupplier.getText().toString();
        String email = mEmailSupplier.getText().toString();

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setEmail(email);

        if (mSelectedSupplierUri != null) {
            long idSupplier = Long.parseLong(mSelectedSupplierUri.getLastPathSegment());
            supplier.setId(idSupplier);
            SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
            supplierDAO.updateItem(getContentResolver(), mSelectedSupplierUri, idSupplier, supplier);

        } else {
            SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
            Uri newSupplierUri = supplierDAO.insertItem(getContentResolver(), InventoryAppTable.SupplierEntry.CONTENT_URI, supplier);

            Log.e(LOG_TAG, "here at middle the end");
            long supplierId = Long.parseLong(newSupplierUri.getLastPathSegment());

            ItemSupplier itemSupplier = new ItemSupplier();
            itemSupplier.setIdItem(id);
            itemSupplier.setIdSupplier(supplierId);

            ItemSupplierDAO<ItemSupplier> itemSupplierDAO = new ItemSupplierDAOImpl();
            itemSupplierDAO.insertItem(getContentResolver(), InventoryAppTable.ItemSupplierEntry.CONTENT_URI, itemSupplier);

        }

        finish();

        Log.e(LOG_TAG, "here at the end");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor_supplier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option

            // Respond to a click on the "Delete all entries" menu option


            case R.id.action_delete: {
                // Log.e("TAG", "delete press");
                deleteMenuClick();
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }


    public void deleteMenuClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_supplier_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSupplier();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void deleteSupplier() {

        if (mSelectedSupplierUri != null) {
            long idSupplier = Long.parseLong(mSelectedSupplierUri.getLastPathSegment());
            SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
            supplierDAO.deleteItem(getContentResolver(), mSelectedSupplierUri, idSupplier);

            Uri itemSupplyUri = Uri.withAppendedPath(InventoryAppTable.ItemSupplierEntry.CONTENT_URI, idSupplier + "");
            ItemSupplierDAO<ItemSupplier> itemSupplierDAO = new ItemSupplierDAOImpl();
            itemSupplierDAO.deleteBySupplierId(getContentResolver(), itemSupplyUri, idSupplier);
        }

        finish();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mSelectedSupplierUri != null)
            return new CursorLoader(this, mSelectedSupplierUri, null, null, null, null);

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {
            String name = data.getString(data.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_NAME));
            String email = data.getColumnName(data.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_EMAIL));

            mNameSupplier.setText(name);
            mEmailSupplier.setText(email);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameSupplier.clearComposingText();
        mEmailSupplier.clearComposingText();
    }
}

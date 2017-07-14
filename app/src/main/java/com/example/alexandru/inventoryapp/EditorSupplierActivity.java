package com.example.alexandru.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private EditText nameSupplier;
    private EditText emailSupplier;
    private String LOG_TAG = "ESA";
    private Uri itemUri;
    private Uri selectedSupplierUri;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_editor);


        Intent intent = getIntent();
        itemUri = intent.getData();
        if (itemUri != null) {
            id = intent.getLongExtra(AppConstants.ID_ITEM, -1);

            String uriSupplierString = intent.getStringExtra(AppConstants.SUPPLIER_URI);
            if (uriSupplierString != null) {
                setTitle(R.string.edit_mode_supplier);
                selectedSupplierUri = Uri.parse(uriSupplierString);

            } else {

                setTitle(R.string.add_mode_supplier);
            }
        }

        nameSupplier = (EditText) findViewById(R.id.edit_text_name_supplier);
        emailSupplier = (EditText) findViewById(R.id.edit_text_email_supplier);

        getLoaderManager().initLoader(LOADER_INDEX, null, this);
    }


    public void addOrUpdateSupplier(View view) {

        String name = nameSupplier.getText().toString();
        String email = emailSupplier.getText().toString();

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setEmail(email);

        if (selectedSupplierUri != null) {
            long idSupplier = Long.parseLong(selectedSupplierUri.getLastPathSegment());
            supplier.setId(idSupplier);
            SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
            supplierDAO.updateItem(getContentResolver(), selectedSupplierUri, idSupplier, supplier);

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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (selectedSupplierUri != null)
            return new CursorLoader(this, selectedSupplierUri, null, null, null, null);

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {
            String name = data.getString(data.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_NAME));
            String email = data.getColumnName(data.getColumnIndex(InventoryAppTable.SupplierEntry.COLUMN_EMAIL));

            nameSupplier.setText(name);
            emailSupplier.setText(email);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        nameSupplier.clearComposingText();
        emailSupplier.clearComposingText();
    }
}

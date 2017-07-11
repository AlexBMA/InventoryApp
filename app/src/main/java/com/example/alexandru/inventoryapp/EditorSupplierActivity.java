package com.example.alexandru.inventoryapp;

import android.content.Intent;
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

public class EditorSupplierActivity extends AppCompatActivity {

    private EditText nameSupplier;
    private EditText emailSupplier;

    private String LOG_TAG = "ESA";
    private Uri itemUri;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_editor);


        Intent intent = getIntent();
        itemUri = intent.getData();
        if (itemUri != null) {
            id = intent.getLongExtra(AppConstants.ID_ITEM, -1);

            Log.e(LOG_TAG, itemUri.toString());
            Log.e(LOG_TAG, id + "");
        }

        nameSupplier = (EditText) findViewById(R.id.edit_text_name_supplier);
        emailSupplier = (EditText) findViewById(R.id.edit_text_email_supplier);


    }


    public void addOrUpdateSupplier(View view) {

        String name = nameSupplier.getText().toString();
        String email = emailSupplier.getText().toString();

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setEmail(email);

        SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
        Uri newSupplierUri = supplierDAO.insertItem(getContentResolver(), InventoryAppTable.SupplierEntry.CONTENT_URI, supplier);

        Log.e(LOG_TAG, "here at middle the end");
        long supplierId = Long.parseLong(newSupplierUri.getLastPathSegment());

        ItemSupplier itemSupplier = new ItemSupplier();
        itemSupplier.setIdItem(id);
        itemSupplier.setIdSupplier(supplierId);

        ItemSupplierDAO<ItemSupplier> itemSupplierDAO = new ItemSupplierDAOImpl();
        itemSupplierDAO.insertItem(getContentResolver(), InventoryAppTable.ItemSupplierEntry.CONTENT_URI, itemSupplier);

        finish();

        Log.e(LOG_TAG, "here at the end");

    }


}

package com.example.alexandru.inventoryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import constactpack.AppConstants;

public class EditorSupplierActivity extends AppCompatActivity {

    private EditText nameSupplier;
    private EditText emailSupplier;

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
        }

        nameSupplier = (EditText) findViewById(R.id.edit_text_name_supplier);
        emailSupplier = (EditText) findViewById(R.id.edit_text_email_supplier);


    }


}

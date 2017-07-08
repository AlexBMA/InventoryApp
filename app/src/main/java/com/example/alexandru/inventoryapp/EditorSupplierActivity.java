package com.example.alexandru.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class EditorSupplierActivity extends AppCompatActivity {

    private EditText nameSupplier;
    private EditText emailSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_editor);


        nameSupplier = (EditText) findViewById(R.id.edit_text_name_supplier);
        emailSupplier = (EditText) findViewById(R.id.edit_text_email_supplier);


    }
}

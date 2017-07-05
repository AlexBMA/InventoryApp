package com.example.alexandru.inventoryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SupplierActivity extends AppCompatActivity {


    private Uri selectedItemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_catalog);
        setTitle(R.string.supplier_catalog_tile);

        Intent intent = getIntent();
        selectedItemUri = intent.getData();


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.button_add_supplier);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //intent.setData()
        super.onBackPressed();
        return;

    }
}

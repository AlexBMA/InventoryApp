package com.example.alexandru.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import data.InventoryAppTable;
import data.SupplierDAO;
import data.SupplierDAOImpl;
import model.Supplier;

public class CatalogSupplierActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


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

                Intent intent = new Intent(CatalogSupplierActivity.this, EditorSupplierActivity.class);
                startActivity(intent);
            }
        });

       /* case android.R.id.home:
        {
            NavUtils.navigateUpFromSameTask(EditorItemActivity.this);
            return  true;
        }
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            /*
            case R.id.action_insert_dummy_data: {
                //  insetItem();
                return true;
            }
            */
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries: {
                deleteAllSuppliers();
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllSuppliers() {

        SupplierDAO<Supplier> supplierDAO = new SupplierDAOImpl();
        supplierDAO.deleteAllItems(getContentResolver(), InventoryAppTable.SupplierEntry.CONTENT_URI);

    }

    @Override
    public void onBackPressed() {
        // Log.e("TAG ", "HERE in BACK");
        Intent intent = new Intent();
        intent.setData(selectedItemUri);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        return;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, InventoryAppTable.SupplierEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

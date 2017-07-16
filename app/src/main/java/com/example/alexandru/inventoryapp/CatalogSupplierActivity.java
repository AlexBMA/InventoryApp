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
import android.widget.AdapterView;
import android.widget.ListView;

import adapter.SupplierCursorAdapter;
import constactpack.AppConstants;
import dao.ItemSupplierDAO;
import dao.SupplierDAO;
import daoImpl.ItemSupplierDAOImpl;
import daoImpl.SupplierDAOImpl;
import data.InventoryAppTable;
import model.ItemSupplier;
import model.Supplier;

public class CatalogSupplierActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int LOADER_INDEX = 1;
    private SupplierCursorAdapter supplierCursorAdapter;
    private Uri selectedItemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_catalog);
        setTitle(R.string.supplier_catalog_tile);

        Intent intent = getIntent();
        selectedItemUri = intent.getData();


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.b_add_supplier);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CatalogSupplierActivity.this, EditorSupplierActivity.class);
                intent.setData(selectedItemUri);
                long id = Long.parseLong(selectedItemUri.getLastPathSegment());
                intent.putExtra(AppConstants.ID_ITEM, id);
                startActivity(intent);
            }
        });

        supplierCursorAdapter = new SupplierCursorAdapter(this, null, selectedItemUri);

        ListView listViewSupplierCatalog = (ListView) findViewById(R.id.list_supplier);
        listViewSupplierCatalog.setAdapter(supplierCursorAdapter);

        listViewSupplierCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CatalogSupplierActivity.this, EditorSupplierActivity.class);
                intent.setData(selectedItemUri);
                Uri editUri = Uri.withAppendedPath(InventoryAppTable.SupplierEntry.CONTENT_URI, id + "");
                String uriSupplierString = editUri.toString();
                intent.putExtra(AppConstants.SUPPLIER_URI, uriSupplierString);
                startActivity(intent);

            }
        });



       /* case android.R.id.home:
        {
            NavUtils.navigateUpFromSameTask(EditorItemActivity.this);
            return  true;
        }
        */

        getLoaderManager().initLoader(LOADER_INDEX, null, this);
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

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LOADER_INDEX, null, this);
    }

    private void deleteAllSuppliers() {

        ItemSupplierDAO<ItemSupplier> itemSupplierDAO = new ItemSupplierDAOImpl();
        itemSupplierDAO.deleteAllItems(getContentResolver(), InventoryAppTable.ItemSupplierEntry.CONTENT_URI);

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


        String[] projection = {InventoryAppTable.SupplierEntry.TABLE_NAME, InventoryAppTable.SupplierEntry.COLUMN_EMAIL};
        String selection = InventoryAppTable.ItemSupplierEntry.ID_SUPPLIER
                + " = " + InventoryAppTable.SupplierEntry.ID
                + " AND " + InventoryAppTable.ItemSupplierEntry.ID_ITEM + " = ?";
        String[] selectionArgs = {"" + selectedItemUri.getLastPathSegment()};

        return new CursorLoader(this, InventoryAppTable.SupplierEntry.CONTENT_URI, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        supplierCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        supplierCursorAdapter.swapCursor(null);
    }
}

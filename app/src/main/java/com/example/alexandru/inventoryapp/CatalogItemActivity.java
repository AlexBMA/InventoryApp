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

import adapter.ItemCursorAdapter;
import constactpack.AppConstants;
import dao.ItemDAO;
import daoImpl.ItemDAOImpl;
import data.InventoryAppTable;
import model.Item;


public class CatalogItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SELECT_PICTURE = 100;
    private final int LOADER_INDEX = 1;
    private ItemCursorAdapter itemCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_catalog);
        setTitle(R.string.item_catalog_title);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImageChooser();

            }
        });


        itemCursorAdapter = new ItemCursorAdapter(this, null);

        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(itemCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogItemActivity.this, EditorItemActivity.class);
                //Log.e("TAG", "in the onItemClickListener " + id);
                Uri editUri = Uri.withAppendedPath(InventoryAppTable.ItemEntry.CONTENT_URI, id + "");
                intent.setData(editUri);
                intent.putExtra(AppConstants.ID_ITEM, id);
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(LOADER_INDEX, null, this);


    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivity(intent);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {

                    //Log.e("IMG", selectedImageUri.toString());
                    Intent intent = new Intent(CatalogItemActivity.this, EditorItemActivity.class);
                    //intent.put
                    intent.putExtra(AppConstants.IMG_URI_STRING, selectedImageUri.toString());
                    //intent.setData(selectedImageUri);
                    startActivity(intent);

                }

            }
        }
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
                deleteAllPets();
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPets() {

        ItemDAO<Item> inventoryDAO = new ItemDAOImpl();
        inventoryDAO.deleteAllItems(getContentResolver(), InventoryAppTable.ItemEntry.CONTENT_URI);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, InventoryAppTable.ItemEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        itemCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);
    }


}

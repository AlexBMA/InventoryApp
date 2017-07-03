package com.example.alexandru.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import adapter.ItemCursorAdapter;
import constactpack.AppConstants;
import data.InventoryContact;
import helperpack.Utils;
import model.Item;


public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SELECT_PICTURE = 100;
    private final int LOADER_INDEX = 1;
    private ItemCursorAdapter itemCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

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
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                Log.e("TAG", "in the onItemClickListener " + id);
                Uri editUri = Uri.withAppendedPath(InventoryContact.ItemEntry.CONTENT_URI, id + "");
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

                    Log.e("IMG", selectedImageUri.toString());
                    Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
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
            case R.id.action_insert_dummy_data: {
                //  insetItem();
                return true;
            }
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries: {
                deleteAllPets();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPets() {

        getContentResolver().delete(InventoryContact.ItemEntry.CONTENT_URI, null, null);
    }


    private void insetItem() {

        Log.e("TAG", "in insert method");
        Item temp = new Item();

        double random = Math.random() * 100 + 1;

        temp.setName("Item name " + random);
        temp.setStock(AppConstants.INITIAL_STOCK);
        temp.setValue((int) random);
        temp.setSales(AppConstants.INITIAL_SALES);

        String uriString = "content://com.android.providers.media.documents/document/image%3A16357";

        Uri imgUriDefault = Uri.parse(uriString);
        try {
            InputStream iStream = getContentResolver().openInputStream(imgUriDefault);
            byte[] imgBytes = Utils.getBytes(iStream);
            temp.setImgBytes(imgBytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        ContentValues valuesForInsert = new ContentValues();
        setDataForInsert(temp, valuesForInsert);

        getContentResolver().insert(InventoryContact.ItemEntry.CONTENT_URI, valuesForInsert);

    }

    @NonNull
    private String[] getStringsProjection() {
        return new String[]{
                InventoryContact.ItemEntry._ID,
                InventoryContact.ItemEntry.COLUMN_NAME,
                InventoryContact.ItemEntry.COLUMN_SALES,
                InventoryContact.ItemEntry.COLUMN_VALUE,
                InventoryContact.ItemEntry.COLUMN_STOCK
        };

    }

    private void getAllItemFromInventory(SQLiteDatabase database, String[] projection) {
        Cursor c = database.query(InventoryContact.ItemEntry.TABLE_NAME, projection, null, null, null, null, null);


    }


    private void setDataForInsert(Item item, ContentValues values) {
        values.put(InventoryContact.ItemEntry.COLUMN_NAME, item.getName());
        values.put(InventoryContact.ItemEntry.COLUMN_SALES, item.getSales());
        values.put(InventoryContact.ItemEntry.COLUMN_VALUE, item.getValue());
        values.put(InventoryContact.ItemEntry.COLUMN_STOCK, item.getStock());
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, InventoryContact.ItemEntry.CONTENT_URI, null, null, null, null);
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

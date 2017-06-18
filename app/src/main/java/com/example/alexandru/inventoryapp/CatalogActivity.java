package com.example.alexandru.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import adapter.ItemCursorAdapter;
import data.InventoryContact;
import model.Item;


public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int LOADER_INDEX = 1;
    ItemCursorAdapter itemCursorAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        itemCursorAdapter = new ItemCursorAdapter(this, null);

        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(itemCursorAdapter);

        getLoaderManager().initLoader(LOADER_INDEX, null, this);


        // Cursor cursor = getContentResolver().query(InventoryContact.ItemEntry.CONTENT_URI, null, null, null, null);


        // putInformationOnScreen(cursor);

        //String[] projection = getStringsProjection();

       /* InventoryDbHelper dbHelper = new InventoryDbHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        getAllItemFromInventory(database, projection);

        dbHelper.close();
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
            case R.id.action_insert_dummy_data: {
                insetItem();
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

        int rez = getContentResolver().delete(InventoryContact.ItemEntry.CONTENT_URI, null, null);

        Log.e("items deleted", rez + "");

        /*Log.e("TAG", "in delete method");
        InventoryDbHelper dbHelper = new InventoryDbHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(InventoryContact.ItemEntry.TABLE_NAME, null, null);
        database.close();
        */

        // tv = (TextView) findViewById(R.id.text_view_all);
        //  tv.setText("");
    }


    private void insetItem() {

        Log.e("TAG", "in insert method");
        Item temp = new Item();

        double random = Math.random() * 100 + 1;

        temp.setName("Item name " + random);
        temp.setStock(20);
        temp.setValue((int) random);
        temp.setSales(0);

        //InventoryDbHelper dbHelper = new InventoryDbHelper(getApplicationContext());
        //SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues valuesForInsert = new ContentValues();
        setDataForInsert(temp, valuesForInsert);

        getContentResolver().insert(InventoryContact.ItemEntry.CONTENT_URI, valuesForInsert);


        //database.insert(InventoryContact.ItemEntry.TABLE_NAME, null, valuesForInsert);
        //String[] projection = getStringsProjection();
        //tv = (TextView) findViewById(R.id.text_view_all);
        //getAllItemFromInventory(database, projection);
        //dbHelper.close();

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

        putInformationOnScreen(c);
    }

    private void putInformationOnScreen(Cursor c) {
        tv.setText("");

        c.moveToFirst();
        int size = c.getCount();
        for (int i = 1; i < size; i++) {
            String text = tv.getText().toString();
            text = text + c.getString(c.getColumnIndex(InventoryContact.ItemEntry.ID)) + " ";
            text = text + c.getString(c.getColumnIndex(InventoryContact.ItemEntry.COLUMN_NAME)) + "\n";
            tv.setText(text);

            c.moveToNext();
        }
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

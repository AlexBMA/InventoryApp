package com.example.alexandru.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import constactpack.AppConstants;
import data.InventoryContact;
import helperpack.Utils;
import model.Item;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    // These are the Contacts rows that we will retrieve
    private static final String[] PROJECTION = null;
    // This is the select criteria
    private static final String SELECTION = null;
    private static final int LOADER_INDEX = 1;

    protected EditText itemName;

    protected EditText itemPrice;
    protected EditText itemSales;
    protected EditText itemQuantity;

    protected ImageView imageView;


    private Uri selectedImageUri;
    private Uri selectedITemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        itemName = (EditText) findViewById(R.id.edit_item_name);
        itemPrice = (EditText) findViewById(R.id.edit_item_price);
        itemQuantity = (EditText) findViewById(R.id.edit_item_quantity);
        itemSales = (EditText) findViewById(R.id.edit_item_sales);
        imageView = (ImageView) findViewById(R.id.image_view_item);

        Intent intent = getIntent();
        selectedITemUri = intent.getData();

        String uriImgString = intent.getStringExtra(AppConstants.IMG_URI_STRING);
        if (uriImgString != null) {
            Log.e("IMG_Editor_Activity", uriImgString);
            selectedImageUri = Uri.parse(uriImgString);
            imageView.setImageURI(selectedImageUri);
        }

        if (selectedITemUri != null) {
            //prepare for edit item
        }

       /* if (selectedITemUri != null) {
            Log.e("IMG_Editor_Activity", selectedImageUri.toString());
            // getImgFromUri(selectedImageUri);

            imageView.setImageURI(selectedImageUri);
            // imageView.setImageBitmap(Utils.getImage(imgBytes));

        }
        */

        getLoaderManager().initLoader(LOADER_INDEX, null, this);

    }

    private byte[] getImgFromUri(Uri selectedImageUri) {

        try {
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] imgBytes = Utils.getBytes(iStream);
            Log.e("TAG", " all good");
            return imgBytes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edtior, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("TAG", "%% ##");


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_save: {
                Log.e("TAG", "save press");
                saveOrUpdateItem();
                finish();
                return true;
            }
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete: {
                deleteItem();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItem() {

    }

    private void saveOrUpdateItem() {

        String name = itemName.getText().toString();
        int value = Integer.parseInt(itemPrice.getText().toString());
        byte[] imgBytes = getImgFromUri(selectedImageUri);

        Item temp = new Item();

        temp.setName(name);
        temp.setStock(20);
        temp.setValue(value);
        temp.setSales(0);
        temp.setImgBytes(imgBytes);

        ContentValues valuesForInsert = new ContentValues();
        setDataForInsert(temp, valuesForInsert);
        getContentResolver().insert(InventoryContact.ItemEntry.CONTENT_URI, valuesForInsert);

    }


    private void setDataForInsert(Item item, ContentValues values) {
        values.put(InventoryContact.ItemEntry.COLUMN_NAME, item.getName());
        values.put(InventoryContact.ItemEntry.COLUMN_SALES, item.getSales());
        values.put(InventoryContact.ItemEntry.COLUMN_VALUE, item.getValue());
        values.put(InventoryContact.ItemEntry.COLUMN_STOCK, item.getStock());
        values.put(InventoryContact.ItemEntry.COLUMN_IMG_BYTES, item.getImgBytes());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (selectedITemUri != null)
            return new CursorLoader(this, selectedITemUri, PROJECTION, SELECTION, null, null);

        return null;

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {
            int nameColumnIndex = data.getColumnIndex(InventoryContact.ItemEntry.COLUMN_NAME);
            int quantityColumnIndex = data.getColumnIndex(InventoryContact.ItemEntry.COLUMN_STOCK);
            int priceColumnIndex = data.getColumnIndex(InventoryContact.ItemEntry.COLUMN_VALUE);
            int imgBytesColumnIndex = data.getColumnIndex(InventoryContact.ItemEntry.COLUMN_IMG_BYTES);
            int saleColumnIndex = data.getColumnIndex(InventoryContact.ItemEntry.COLUMN_SALES);


            String nameItem = data.getColumnName(nameColumnIndex);
            int quantity = data.getInt(quantityColumnIndex);
            int price = data.getInt(priceColumnIndex);
            int sales = data.getInt(saleColumnIndex);
            byte[] imgBytes = data.getBlob(imgBytesColumnIndex);

            Bitmap image = Utils.getImage(imgBytes);

            // Log.e("pirce",price+" $$");

            imageView.setImageBitmap(image);
            itemName.setText(nameItem);
            itemSales.setText(Integer.toString(sales));
            itemPrice.setText(Integer.toString(price));
            itemQuantity.setText(Integer.toString(quantity));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // imageView.
        itemName.clearComposingText();
        itemPrice.clearComposingText();
        itemQuantity.clearComposingText();

    }
}

package com.example.alexandru.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import constactpack.AppConstants;
import data.InventoryContact;
import data.ItemDAO;
import data.ItemDAOImpl;
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
    protected TextView itemSales;
    protected TextView itemStock;
    protected ImageView imageView;

    private long id = -1;
    private Bitmap image;

    private Uri selectedImageUri;
    private Uri selectedItemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        itemName = (EditText) findViewById(R.id.edit_item_name);
        itemPrice = (EditText) findViewById(R.id.edit_item_price);
        itemStock = (TextView) findViewById(R.id.text_view_item_stock);
        itemSales = (TextView) findViewById(R.id.text_view_item_sales);
        imageView = (ImageView) findViewById(R.id.image_view_item);

        Intent intent = getIntent();
        selectedItemUri = intent.getData();

        String uriImgString = intent.getStringExtra(AppConstants.IMG_URI_STRING);
        if (uriImgString != null) {
            Log.e("IMG_Editor_Activity", uriImgString);
            selectedImageUri = Uri.parse(uriImgString);
            imageView.setImageURI(selectedImageUri);
            itemStock.setText(String.valueOf(AppConstants.INITIAL_STOCK));
            itemSales.setText(String.valueOf(AppConstants.INITIAL_SALES));

            int itemSales = intent.getIntExtra(AppConstants.ITEM_SALE, -1);
            Log.e("TAG", " " + itemSales);
        }

        if (selectedItemUri != null) {
            id = intent.getLongExtra(AppConstants.ID_ITEM, -1);
            Log.e("ID", id + "");

            //prepare for edit item
        }

       /* if (selectedItemUri != null) {
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


        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_save: {
                // Log.e("TAG", "save press");
                saveOrUpdateItem();
                finish();
                return true;
            }
            // Respond to a click on the "Delete all entries" menu option
           /*
            case R.id.action_delete: {
               // Log.e("TAG", "delete press");
                deleteItem();
                return true;
            }
            */

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteButtonClick(View view) {
        Log.e("TAG", "delete button pressed");

        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void deleteItem() {


        if (id > -1) {

            ItemDAO<Item> inventoryDAO = new ItemDAOImpl();
            inventoryDAO.deleteItem(getContentResolver(), selectedItemUri, id);

        }
        finish();
    }

    private void saveOrUpdateItem() {

        String name = itemName.getText().toString();
        int value = Integer.parseInt(itemPrice.getText().toString());


        Item tempItem = new Item();

        tempItem.setName(name);
        tempItem.setQuantity(AppConstants.INITIAL_STOCK);
        tempItem.setPrice(value);
        tempItem.setSales(AppConstants.INITIAL_SALES);

        if (selectedImageUri != null) {
            byte[] imgBytes = getImgFromUri(selectedImageUri);
            tempItem.setImgBytes(imgBytes);
            Log.e("BYTES: from uri", imgBytes.length + "");
        } else {
            byte[] imgBytes = Utils.getImageBytes(image);
            tempItem.setImgBytes(imgBytes);
            Log.e("BYTES from img:", imgBytes.length + "");
        }

        ContentValues values = new ContentValues();

        if (id > -1) {
            Log.e("ID", id + " &*^");
            tempItem.setId(id);

            ItemDAO<Item> inventoryDAO = new ItemDAOImpl();
            inventoryDAO.updateItem(getContentResolver(), selectedItemUri, id, tempItem);


        } else {

            ItemDAO<Item> inventoryDAO = new ItemDAOImpl();
            inventoryDAO.insertItem(getContentResolver(), InventoryContact.ItemEntry.CONTENT_URI, tempItem);
        }


    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (selectedItemUri != null)
            return new CursorLoader(this, selectedItemUri, PROJECTION, SELECTION, null, null);

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


            String nameItem = data.getString(nameColumnIndex);
            int quantity = data.getInt(quantityColumnIndex);
            int price = data.getInt(priceColumnIndex);
            int sales = data.getInt(saleColumnIndex);
            byte[] imgBytes = data.getBlob(imgBytesColumnIndex);

            image = Utils.getImage(imgBytes);

            // Log.e("pirce",price+" $$");

            imageView.setImageBitmap(image);
            itemName.setText(nameItem);
            itemSales.setText(Integer.toString(sales));
            itemPrice.setText(Integer.toString(price));
            itemStock.setText(Integer.toString(quantity));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        // imageView.
        itemName.clearComposingText();
        itemPrice.clearComposingText();
        itemStock.clearComposingText();

    }

    public void orderButtonClick(View view) {
        Log.e("TAG", "order button pressed");

        // Intent intent = new Intent(this,)
    }
}

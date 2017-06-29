package com.example.alexandru.inventoryapp;

import android.content.Intent;
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

import helperpack.Utils;

public class EditorActivity extends AppCompatActivity {


    protected EditText itemName;
    protected EditText itemPrice;
    protected EditText itemQuantity;
    protected ImageView imageView;
    protected byte[] imgBytes;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        itemName = (EditText) findViewById(R.id.edit_item_name);
        itemPrice = (EditText) findViewById(R.id.edit_item_price);
        itemQuantity = (EditText) findViewById(R.id.edit_item_quantity);
        imageView = (ImageView) findViewById(R.id.image_view_item);


        Intent intent = getIntent();
        selectedImageUri = intent.getData();
        if (selectedImageUri != null) {
            Log.e("IMG_Editor_Activity", selectedImageUri.toString());
            getImgFromUri(selectedImageUri);

            imageView.setImageURI(selectedImageUri);
            // imageView.setImageBitmap(Utils.getImage(imgBytes));

        }



    }

    private void getImgFromUri(Uri selectedImageUri) {

        try {
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            imgBytes = Utils.getBytes(iStream);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                saveOrUpdateItem();
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
        Log.e("", "");
    }

}

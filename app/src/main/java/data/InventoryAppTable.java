package data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static data.InventoryAppTable.SupplierEntry.BASE_CONTENT_URI;

/**
 * Created by Alexandru on 6/8/2017.
 */

public final class InventoryAppTable {


    //public static final String PATH_ITEMS = "inventory";


    public static abstract class ItemEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.alexandru.data.ItemProvider";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String TABLE_NAME = "items";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_SALES = "sales";
        public static final String COLUMN_IMG_BYTES = "img";

        public static final String PATH_ITEMS = "inventory";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;


    }


    public static abstract class ItemSupplierEntry {


        public static final String TABLE_NAME = "itemSupplier";

        public static final String ID_ITEM = "id_item";
        public static final String ID_SUPPLIER = "id_supplier";

        public static final String PATH_ITEMS = "items_supplier";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);
    }


    public static abstract class SupplierEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.example.alexandru.data.SupplierProvider";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String TABLE_NAME = "supplier";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";

        public static final String PATH_ITEMS = "supplier";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;
    }
}

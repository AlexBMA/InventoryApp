package data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alexandru on 6/8/2017.
 */

public final class InventoryContact {

    public static final String CONTENT_AUTHORITY = "com.example.alexandru.data.ItemProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEMS = "inventory";


    public static abstract class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "items";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_SALES = "sales";

        public static final String PATH_ITEMS = "inventory";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);
    }


    public static abstract class ItemSupplierEntry {

        public static final String TABLE_NAME = "itemSupplier";

        public static final String ID_ITEM = "id_item";
        public static final String ID_SUPPLIER = "id_supplier";
    }


    public static abstract class SupplierEntry implements BaseColumns {

        public static final String TABLE_NAME = "supplier";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";


    }
}

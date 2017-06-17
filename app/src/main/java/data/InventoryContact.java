package data;

import android.provider.BaseColumns;

/**
 * Created by Alexandru on 6/8/2017.
 */

public final class InventoryContact {


    public abstract class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "items";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_SALES = "sales";


    }

    public abstract class ItemSupplierEntry {

        public static final String TABLE_NAME = "itemSupplier";

        public static final String ID_ITEM = "id_item";
        public static final String ID_SUPPLIER = "id_supplier";
    }


    public abstract class SupplierEntry implements BaseColumns {

        public static final String TABLE_NAME = "supplier";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";


    }
}

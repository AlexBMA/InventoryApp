package data;

import android.provider.BaseColumns;

/**
 * Created by Alexandru on 6/8/2017.
 */

public final class ItemContact {


    public abstract class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "items";

        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_SALES = "sales";
        public static final String COLUMN_SUPPLIER = "supplier";

    }
}

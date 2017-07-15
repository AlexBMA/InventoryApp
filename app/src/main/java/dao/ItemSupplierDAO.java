package dao;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Alexandru on 7/11/2017.
 */

public interface ItemSupplierDAO<E> extends BasicDAO<E> {

    void deleteByItemId(ContentResolver contentResolver, Uri uri, long id);

    void deleteBySupplierId(ContentResolver contentResolver, Uri uri, long id);
}

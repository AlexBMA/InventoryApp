package dao;

import android.content.ContentResolver;
import android.net.Uri;


/**
 * Created by Alexandru on 6/18/2017.
 */

public interface BasicDAO<E> {


    E getItemById(ContentResolver contentResolver, Uri uri, long id);

    void deleteAllItems(ContentResolver contentResolver, Uri uri);

    void deleteItem(ContentResolver contentResolver, Uri uri, long id);

    Uri insertItem(ContentResolver contentResolver, Uri uri, E item);

    void updateItem(ContentResolver contentResolver, Uri uri, long id, E item);

}

package customListiner;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import dao.ItemDAO;
import daoImpl.ItemDAOImpl;
import model.Item;

/**
 * Created by Alexandru on 7/14/2017.
 */

public class CustomOderButtonListener implements View.OnClickListener {

    private Uri selectedItemUri;
    private Context context;

    public CustomOderButtonListener(Uri selectedItemUri, Context context) {
        this.selectedItemUri = selectedItemUri;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        ItemDAO<Item> itemDAO = new ItemDAOImpl();
        long id = Long.parseLong(selectedItemUri.getLastPathSegment());
        Item temp = itemDAO.getItemById(context.getContentResolver(), selectedItemUri, id);
        temp = increaseStockBy10(temp);
        itemDAO.updateItem(context.getContentResolver(), selectedItemUri, id, temp);


    }

    private Item increaseStockBy10(Item temp) {
        int stock = temp.getStock();
        stock = stock + 10;
        temp.setQuantity(stock);
        return temp;
    }

}

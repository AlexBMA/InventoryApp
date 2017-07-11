package customListiner;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import dao.ItemDAO;
import daoImpl.ItemDAOImpl;
import data.InventoryAppTable;
import model.Item;



/**
 * Created by Alexandru on 7/3/2017.
 */

public class CustomSaleButtonListener implements View.OnClickListener {

    private Item tempItem;
    private Context context;


    public CustomSaleButtonListener(Item tempItem, Context context) {

        this.tempItem = tempItem;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        int stock = tempItem.getStock();
        int sales = tempItem.getSales();
        if (stock > 0) {
            stock = stock - 1;
            tempItem.setQuantity(stock);
            sales = sales + 1;
            tempItem.setSales(sales);
            updateDatabase();
        }
    }

    private void updateDatabase() {

        long id = tempItem.getId();
        Uri editUri = Uri.withAppendedPath(InventoryAppTable.ItemEntry.CONTENT_URI, id + "");
        ItemDAO<Item> inventoryDAO = new ItemDAOImpl();
        inventoryDAO.updateItem(context.getContentResolver(), editUri, id, tempItem);
    }

}

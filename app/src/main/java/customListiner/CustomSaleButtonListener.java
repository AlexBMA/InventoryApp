package customListiner;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alexandru on 7/3/2017.
 */

public class CustomSaleButtonListener implements View.OnClickListener {

    private int sales;
    private int stock;
    private String msg;
    private TextView textView;
    private TextView textView2;

    public CustomSaleButtonListener(int stock, int sales, String msg, TextView textView, TextView textView2) {
        this.stock = stock;
        this.sales = sales;
        this.msg = msg;
        this.textView = textView;
        this.textView2 = textView2;
    }

    @Override
    public void onClick(View v) {

        Log.e("TAG Stock", stock + " ");
        Log.e("TAG Msg", msg);

        stock = stock - 1;
        sales = sales + 1;

        textView.setText(msg + " " + stock);
        textView.setText(" " + sales);
    }
}
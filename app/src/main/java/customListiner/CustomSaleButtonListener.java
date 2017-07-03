package customListiner;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alexandru on 7/3/2017.
 */

public class CustomSaleButtonListener implements View.OnClickListener {

    private int stock;
    private String msg;
    private TextView textView;

    public CustomSaleButtonListener(int stock, String msg, TextView textView) {
        this.stock = stock;
        this.msg = msg;
        this.textView = textView;
    }

    @Override
    public void onClick(View v) {

        Log.e("TAG Stock", stock + " ");
        Log.e("TAG Msg", msg);
        stock = stock - 1;
        textView.setText(msg + " " + stock);

    }
}

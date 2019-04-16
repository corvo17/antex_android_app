package uz.codic.ahmadtea.ui.merchants.adapter;

import android.app.Activity;
import android.content.Context;

public interface CallBackMerchants {

    Context getMerchantContext();
    Activity getThisActivity();
    void openMapsActivity(int pid);

    void changedPosition(int position);
}

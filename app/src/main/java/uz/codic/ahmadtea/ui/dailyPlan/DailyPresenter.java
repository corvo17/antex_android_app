package uz.codic.ahmadtea.ui.dailyPlan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Product;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.data.network.model.DailyBody;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.data.network.model.RequestWithWorkspaceId;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.Consts;

import static android.support.v4.content.ContextCompat.startActivity;

public class DailyPresenter<V extends DailyMvpView> extends BasePresenter<V> implements DailyMvpPresenter<V> {

    public DailyPresenter(Context context) {
        super(context);
    }

    @Override
    public void requestDailyMerchants(String date) {
        DailyBody body = new DailyBody();
        body.setDate(date);
        getDataManager().requestDailyMerchants(getDataManager().getToken(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Object> apiObeject) {
                        Log.d("error_message", "onSuccess: ");
                        if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count()>0){
                        //getData(dailyMerchants);
                        }else {
                            Integer error_type_id = (Integer) apiObeject.getMeta().getError().get("error_type_id");
                            if (error_type_id > 20000 && error_type_id < 21000){
                                getMvpView().goLoginActivity((String) apiObeject.getMeta().getError().get("error_label"));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ErrorClass.log((Exception) e);
                        Log.d("error_message", "onError: " + e.getMessage());
                        Log.d("error_message", "Error: " + e.toString());
                    }
                });
    }

    private void getData(DailyMerchants dailyMerchants) {
        List<WorkspaceAndMerchant> merchants = new ArrayList<>();
        if (!dailyMerchants.getPlan().isEmpty()) {
            for (DailyMerchants.Casual plan : dailyMerchants.getPlan()) {
                merchants.add(getDataManager().getWorkspaceAndMerchants(plan.getId_merchant(), plan.getId_workspace()));
            }
        }
        if (!dailyMerchants.getCasual().isEmpty()){
            for (DailyMerchants.Casual casual:dailyMerchants.getCasual()) {
                merchants.add(getDataManager().getWorkspaceAndMerchants(casual.getId_merchant(), casual.getId_workspace()));
            }
        }
        getMvpView().onMerchantsListReady(merchants);
    }
}


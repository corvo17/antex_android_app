package uz.codic.ahmadtea.ui.dailyPlan;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.data.network.model.DailyBody;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;

public class DailyPresenter<V extends DailyMvpView> extends BasePresenter<V> implements DailyMvpPresenter<V> {

    public DailyPresenter(Context context) {
        super(context);
    }

    @Override
    public void requestDailyMerchants(String date) {
        DailyBody body = new DailyBody();
        HashMap<String , HashMap<String , String >> hashMap = new HashMap<>();
        HashMap<String, String > hashMap1 = new HashMap<>();
        hashMap1.put("date", date);
        hashMap.put("params", hashMap1);
        body.setDate(date);
        getDataManager().requestDailyMerchants(getDataManager().getToken(), hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<DailyMerchants>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<DailyMerchants> apiObeject) {
                        //-
                        if (apiObeject.getMeta().getStatus() == 200 && apiObeject.getMeta().getPayload_count() > 0) {
                            getData(apiObeject.getPayload().get(0));
                        } else {

                            if (apiObeject.getMeta().getError().getError_type_id() > 20000 && apiObeject.getMeta().getError().getError_type_id() < 21000) {
                                getMvpView().goLoginActivity(apiObeject.getMeta().getError().getError_label());
                            }
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        HttpException exception = (HttpException) e;
                        ResponseBody responseBody = exception.response().errorBody();
                        try {
                            JSONObject array = new JSONObject(responseBody.string()).getJSONObject("meta");
                            ErrorClass.log(e.getMessage(), (Exception) e, array.getJSONObject("error").getString("error_id"));

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }
                        //-
                    }
                });
    }

    private void getData(DailyMerchants dailyMerchants) {
        List<WorkspaceAndMerchant> merchants = new ArrayList<>();
        if (!dailyMerchants.getPlan().isEmpty()) {
            for (DailyMerchants.Casual plan : dailyMerchants.getPlan()) {
                merchants.add(getDataManager().getWorkspaceAndMerchants(plan.getMerchant_id(), plan.getWorkspace_id(), CommonUtils.getToday()));
            }
        }
        if (!dailyMerchants.getCasual().isEmpty()) {
            for (DailyMerchants.Casual casual : dailyMerchants.getCasual()) {
                merchants.add(getDataManager().getWorkspaceAndMerchants(casual.getMerchant_id(), casual.getWorkspace_id(), CommonUtils.getToday()));
            }
        }
        getMvpView().onMerchantsListReady(merchants);
    }

    @Override
    public void getMyWorkspaces() {
        getDataManager().getMyWorkspaces(getDataManager().getId_employee())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Workspace>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Workspace> workspaces) {
                        getMvpView().onReadyMyWorkspaces(workspaces);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}


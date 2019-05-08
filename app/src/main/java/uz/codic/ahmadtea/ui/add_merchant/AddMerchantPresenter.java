package uz.codic.ahmadtea.ui.add_merchant;

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
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.Merchant;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.data.network.model.api_objects.ApiObeject;
import uz.codic.ahmadtea.data.network.model.api_objects.Meta;
import uz.codic.ahmadtea.errors.ErrorClass;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class AddMerchantPresenter<V extends AddMerchantMvpView> extends BasePresenter<V> implements AddMerchantMvpPresenter<V> {
    public AddMerchantPresenter(Context context) {
        super(context);
    }

    String id_merchant;

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
                        getMvpView().onMyWorkspaces(workspaces);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void requestAddMerchant(NewMerchant merchant) {
        id_merchant = merchant.getId();
        getDataManager().requestAddMerchant(getDataManager().getToken(), getBody(merchant))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiObeject<Merchant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ApiObeject<Merchant> apiObeject) {
                        if (apiObeject.getMeta().getStatus() == 200) {
                            Log.d("baxtiyor", "requestAddMerchant: " + apiObeject.getPayload().get(0));
                            getMvpView().resultAddMerchant();
                        }else {
                            ErrorClass.log(apiObeject.toString(), new Exception());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpException exception = (HttpException) e;
                        ResponseBody responseBody = exception.response().errorBody();
                        try {
                            JSONObject array = new JSONObject(responseBody.string()).getJSONObject("meta");

                            Log.d("baxtiyor", "onError: status " + array.getLong("status"));
                            Log.d("baxtiyor", "onError: " + responseBody.string());
                            ErrorClass.log(e.getMessage(), (Exception) e, array.getJSONObject("error").getString("error_id"));

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }
                });
    }


    private ApiObeject<Merchant> getBody(NewMerchant merchant) {
        ApiObeject<Merchant> apiObeject = new ApiObeject<>();
        List<Merchant> payload = new ArrayList<>();
        Merchant newMerchant = new Merchant();
        payload.add(newMerchant);
        payload.get(0).setId(merchant.getId());
        payload.get(0).setLabel(merchant.getName());
        payload.get(0).setAddress(merchant.getAddress());
        payload.get(0).setLatitude(merchant.getLatitude());
        payload.get(0).setLongitude(merchant.getLongitude());
        payload.get(0).setInn(merchant.getInn());
        payload.get(0).setPhone(String.valueOf(merchant.getPhone()));
        apiObeject.setPayload(payload);

        return apiObeject;
    }

    @Override
    public void seveMerchant(NewMerchant merchant) {
        getDataManager().insertNewMerchant(merchant);
    }
}

package uz.codic.ahmadtea.ui.add_merchant;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.NewMerchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.network.model.Merchant;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class AddMerchantPresenter<V extends AddMerchantMvpView> extends BasePresenter<V> implements AddMerchantMvpPresenter<V>{
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
        HashMap<String, Merchant> hashMap = getBody(merchant);
        getDataManager().requestAddMerchant(getDataManager().getToken(), hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SendResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SendResponse sendResponse) {
                        Log.d("baxtiyor", "requestAddMerchant: " + sendResponse);
                        getMvpView().resultAddMerchant(sendResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }



    private HashMap<String, Merchant> getBody(NewMerchant merchant) {
        HashMap<String, Merchant> hashMap = new HashMap<>();
        Merchant object = new Merchant();
        object.setId(merchant.getId());
        object.setName(merchant.getName());
        object.setAddress(merchant.getAddress());
        object.setLatitude(merchant.getLatitude());
        object.setLongitude(merchant.getLongitude());
        object.setInn(merchant.getInn());
        object.setPhone(String.valueOf(merchant.getPhone()));
        hashMap.put("payload", object);
        return hashMap;
    }

    @Override
    public void seveMerchant(NewMerchant merchant) {
        getDataManager().insertNewMerchant(merchant);
    }
}

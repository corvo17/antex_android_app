package uz.codic.ahmadtea.ui.visit.zakaz.product;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.ProductAndProductPrice;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.network.model.ApiOrder;
import uz.codic.ahmadtea.data.network.model.ApiOrderBasket;
import uz.codic.ahmadtea.data.network.model.ApiVisit;
import uz.codic.ahmadtea.data.network.model.Payload;
import uz.codic.ahmadtea.data.network.model.Send;
import uz.codic.ahmadtea.data.network.model.SendResponse;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

public class ProductPresenter<V extends ProductMvpView> extends BasePresenter<V> implements ProductMvpPresenter<V> {

    public ProductPresenter(Context context) {
        super(context);
    }

    @Override
    public void reqeustProductList(int priceId, String workspace_id) {
        getDataManager().getProductsWithValue(priceId, workspace_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductAndProductPrice>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ProductAndProductPrice> productAndProductPrices) {
                        getMvpView().onProductListReady(productAndProductPrices);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }



}

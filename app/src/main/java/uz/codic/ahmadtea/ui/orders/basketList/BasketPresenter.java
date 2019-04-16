package uz.codic.ahmadtea.ui.orders.basketList;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.orders.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.utils.Consts;

public class BasketPresenter<V extends BasketMvpView> extends BasePresenter<V> implements BasketMvpPresenter<V> {

    public BasketPresenter(Context context) {
        super(context);
    }

    @Override
    public void getBasketList(int priceId, String orderId) {
        getDataManager().getBasketList(priceId, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BasketProduct>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BasketProduct> orderBaskets) {
                        for (BasketProduct basketProduct: orderBaskets
                             ) {
                            Log.d(Consts.TEST_TAG, "onSuccess: " + basketProduct.getOrderBasket().toString());
                            Log.d(Consts.TEST_TAG, "onSuccess: " + basketProduct.getProductPrice().getValue());
                            Log.d(Consts.TEST_TAG, "onSuccess: " + basketProduct.getProduct().getLabel());
                        }
                        getMvpView().onBasketListReady(orderBaskets);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}

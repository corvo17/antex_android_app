package uz.codic.ahmadtea.ui.report.basketList;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;

public class BasketPresenter<V extends BasketMvpView> extends BasePresenter<V> implements BasketMvpPresenter<V> {

    public BasketPresenter(Context context) {
        super(context);
    }

    @Override
    public void getBasketList(int priceId, String orderId, Integer id_payment_type) {
        getDataManager().getBasketList(priceId, orderId, id_payment_type)
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

                        }
                        getMvpView().onBasketListReady(orderBaskets);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public Visit getVisit(String id) {
        return getDataManager().getVisitByIdOrder(id);
    }

    @Override
    public List<Comment> getComments(List<Integer> ids) {
        return getDataManager().getComments(ids);
    }
}

package uz.codic.ahmadtea.ui.orders;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.orders.adapter.OrderedList;
import uz.codic.ahmadtea.utils.Consts;

public class OrderPresenter<V extends OrderMvpView> extends BasePresenter<V> implements OrderMvpPresenter<V>  {

    public OrderPresenter(Context context) {
        super(context);
    }

    @Override
    public void getOrderedList() {
        getDataManager().getOrderedList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<OrderedList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<OrderedList> orderedLists) {
                        getMvpView().onOrderedListReady(orderedLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(Consts.TEST_TAG, "onError: " + e.getMessage());

                    }
                });
    }
}

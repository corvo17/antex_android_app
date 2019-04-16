package uz.codic.ahmadtea.ui.dashboard;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.codic.ahmadtea.data.network.model.DailyMerchants;
import uz.codic.ahmadtea.data.network.model.EmployeeLocation;
import uz.codic.ahmadtea.data.network.model.LocationResponse;
import uz.codic.ahmadtea.data.network.model.RequestWithWorkspaceId;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.Consts;

public class DashboardPresenter<V extends DashboardMvpView> extends BasePresenter<V> implements DashboardMvpPresenter<V> {

    public DashboardPresenter(Context context) {
        super(context);
    }

}

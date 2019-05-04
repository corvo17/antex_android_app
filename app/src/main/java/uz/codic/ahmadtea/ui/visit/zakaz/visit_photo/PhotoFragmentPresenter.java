package uz.codic.ahmadtea.ui.visit.zakaz.visit_photo;

import android.content.Context;

import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.ui.base.MvpView;

public class PhotoFragmentPresenter<V extends PhotoFragmentView & MvpView> extends BasePresenter<V> implements PhotoFragmentPresenterView<V> {
    public PhotoFragmentPresenter(Context context) {
        super(context);
    }
}

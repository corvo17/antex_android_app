package uz.codic.ahmadtea.ui.visit.zakaz.visitFragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.ui.base.BasePresenter;

public class VisitFragmentPresenter<v extends VisitFragmentView> extends BasePresenter<v> implements VisitFragmentPresenterView<v> {

    public VisitFragmentPresenter(Context context) {
        super(context);
    }

    List<String> allCommentStrings;

    @Override
    public void initialize() {
        getDisposable().add(getDataManager().allComments()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                    getMvpView().loadComments(data);
                }));
    }
}

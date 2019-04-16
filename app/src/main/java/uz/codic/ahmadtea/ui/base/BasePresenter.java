package uz.codic.ahmadtea.ui.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.DataManager;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    //To have the attached view Reference
    private V mMvpView;

    //To control all data logic
    private DataManager dataManager;

    //To avoid memory leaks while observing emmited values from network and database
    private CompositeDisposable disposable;

    public BasePresenter(Context context){
        dataManager = new AppDataManager(context);
        disposable = new CompositeDisposable();
    }

    //Connect the View interface with presenter interface
    @Override
    public void onAttach(V mvpView){
        mMvpView = mvpView;
    }

    //Disconnect view interface from the presenter interface
    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }


    public V getMvpView(){
        return mMvpView;
    }

    public boolean isViewAttached(){
        return mMvpView != null;
    }


}

package uz.codic.ahmadtea.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V view);

    void onDetach();

}

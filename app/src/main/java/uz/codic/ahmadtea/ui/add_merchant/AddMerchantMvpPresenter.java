package uz.codic.ahmadtea.ui.add_merchant;

        import uz.codic.ahmadtea.data.db.entities.NewMerchant;
        import uz.codic.ahmadtea.ui.base.MvpPresenter;

public interface AddMerchantMvpPresenter<V extends AddMerchantMvpView> extends MvpPresenter<V> {

    void getMyWorkspaces();

    void requestAddMerchant(NewMerchant merchant);

    void seveMerchant(NewMerchant merchant);
}

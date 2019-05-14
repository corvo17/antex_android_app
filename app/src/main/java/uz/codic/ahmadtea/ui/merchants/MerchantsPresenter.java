package uz.codic.ahmadtea.ui.merchants;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.Workspace;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BasePresenter;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

public class MerchantsPresenter<V extends MerchantsMvpView> extends BasePresenter<V> implements MerchantsMvpPresenter<V> {
    public MerchantsPresenter(Context context) {
        super(context);
    }

    @Override
    public void getWorkspaceAndMerchants() {
        if (getDataManager().isAdmin()) {
            getDataManager().getWorkspaceAndMerchants(getDataManager().getMyWorkspaceIds(getDataManager().getId_employee()), CommonUtils.getToday())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<WorkspaceAndMerchant>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<WorkspaceAndMerchant> workspaceAndMerchants) {
                            getMerchantListWorkspaces(workspaceAndMerchants);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showMessage("Error");
                        }
                    });
        } else {
            getMerchantsForTrageAgent();
        }
    }

    private void getMerchantsForTrageAgent() {

        List<String> idStrings = getDataManager().getMyWorkspaceIds(getDataManager().getId_employee());
        getDataManager().getMerchantsInWorkspace(idStrings, CommonUtils.getToday())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<WorkspaceAndMerchant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<WorkspaceAndMerchant> workspaceAndMerchants) {
                        //convertMerchantListWorkspaces(workspaceAndMerchants);
                        getMerchantListWorkspaces(workspaceAndMerchants);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public void getMerchantListWorkspaces(List<WorkspaceAndMerchant> merchant) {
        try {
            int key = 0;
            List<MerchantListWorspaces> merchants = new ArrayList<>();
            MerchantListWorspaces merchants1 = new MerchantListWorspaces();
            merchants1.setMerchant(merchant.get(0).getMerchant());
            merchants1.setWorkspace(merchant.get(0).getWorkspace());
            if (merchant.get(0).getInfoAction() != null)
                merchants1.setInfos(merchant.get(0).getInfoAction());
            merchants.add(merchants1);
            for (int i = 1; i < merchant.size(); i++) {
                if (merchant.get(key).getMerchant().getId().equals(merchant.get(i).getMerchant().getId())) {
                    merchants1.setWorkspace(merchant.get(i).getWorkspace());
                    if (merchant.get(i).getInfoAction() != null) {
                        merchants1.setInfos(merchant.get(i).getInfoAction());
                        merchants1.setIsinfos(merchant.get(i).getInfoAction().isAction());
                    }
                    merchants.add(merchants1);

                } else {
                    key = i;
                    merchants1 = new MerchantListWorspaces();
                    merchants1.setMerchant(merchant.get(i).getMerchant());
                    merchants1.setWorkspace(merchant.get(i).getWorkspace());
                    if (merchant.get(i).getInfoAction() != null) {
                        merchants1.setInfos(merchant.get(i).getInfoAction());
                        merchants1.setIsinfos(merchant.get(i).getInfoAction().isAction());
                    }
                    merchants.add(merchants1);
                }
            }
            getMvpView().getMerchants(merchants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMyWorkspaces() {
        getDataManager().getMyWorkspaces(getDataManager().getId_employee())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Workspace>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Workspace> workspaces) {
                        getMvpView().onReadyMyWorkspaces(workspaces);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void getMerchantsInWorkspace(String id_workspace) {
        ArrayList<String> list = new ArrayList<>();
        list.add(id_workspace);
        if (id_workspace == null) {
            getWorkspaceAndMerchants();
        } else {
            getDataManager().getMerchantsInWorkspace(list, CommonUtils.getToday())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<WorkspaceAndMerchant>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<WorkspaceAndMerchant> workspaceAndMerchants) {
                            convertMerchantListWorkspaces(workspaceAndMerchants);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

    private void convertMerchantListWorkspaces(List<WorkspaceAndMerchant> workspaceAndMerchants) {
        List<MerchantListWorspaces> merchantInWorkspaces = new ArrayList<>();
        for (int i = 0; i < workspaceAndMerchants.size(); i++) {
            MerchantListWorspaces list = new MerchantListWorspaces();
            list.setMerchant(workspaceAndMerchants.get(i).getMerchant());
            list.setWorkspace(workspaceAndMerchants.get(i).getWorkspace());
            if (workspaceAndMerchants.get(i).getInfoAction() != null) {
                list.setInfos(workspaceAndMerchants.get(i).getInfoAction());
                list.setIsinfos(workspaceAndMerchants.get(i).getInfoAction().isAction());
            }

            merchantInWorkspaces.add(list);
        }
        getMvpView().onReadyMerchantsInWorkspace(merchantInWorkspaces);
    }

    @Override
    public boolean isAdmin() {
        return getDataManager().isAdmin();
    }

    @Override
    public void getOldIndoActions() {
        if (!getDataManager().getDate().equals(CommonUtils.getToday())) {
            getDataManager().getInfoActionByPerdingAndSave(true, true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<InfoAction>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<InfoAction> infoActions) {
                            new MyTask().execute(infoActions);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

    private class MyTask extends AsyncTask<List<InfoAction>, Void, List<InfoAction>> {


        @Override
        protected List<InfoAction> doInBackground(List<InfoAction>... lists) {
            List<InfoAction> actions = lists[0];
            for (int i = 0; i < actions.size(); i++) {
                actions.get(i).setSend(false);
                actions.get(i).setSend_draft(false);
                actions.get(i).setDate(CommonUtils.getToday());
            }


            return actions;
        }

        @Override
        protected void onPostExecute(List<InfoAction> infoActions) {
            super.onPostExecute(infoActions);
            getDataManager().updateInfoActions(infoActions);
            getDataManager().setDate(CommonUtils.getToday());
            getMvpView().onResultInfoActionUpdate();
        }
    }
}

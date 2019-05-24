package uz.codic.ahmadtea.ui.report.basketList;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Comment;
import uz.codic.ahmadtea.data.db.entities.InfoAction;
import uz.codic.ahmadtea.data.db.entities.Order;
import uz.codic.ahmadtea.data.db.entities.OrderBasket;
import uz.codic.ahmadtea.data.db.entities.PhysicalWareHouse;
import uz.codic.ahmadtea.data.db.entities.Visit;
import uz.codic.ahmadtea.data.network.model.Merchant;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.report.basketList.adapter.BasketProduct;
import uz.codic.ahmadtea.ui.report.basketList.adapter.PagerAdapter;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;
import uz.codic.ahmadtea.ui.visit.MerchantMvpView;
import uz.codic.ahmadtea.ui.visit.MerchantPresenter;
import uz.codic.ahmadtea.ui.visit.zakaz.InformationFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.OnFragmentInteractionListener;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteApi;
import uz.codic.ahmadtea.ui.visit.zakaz.modelUi.CompleteObject;
import uz.codic.ahmadtea.ui.visit.zakaz.prices.PricesFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visitFragment.VisitFragment;
import uz.codic.ahmadtea.ui.visit.zakaz.visit_info.MerchantPageFragment;
import uz.codic.ahmadtea.utils.CommonUtils;
import uz.codic.ahmadtea.utils.Consts;

import static uz.codic.ahmadtea.utils.Consts.informationTag;
import static uz.codic.ahmadtea.utils.Consts.isSaved;
import static uz.codic.ahmadtea.utils.Consts.pricesTag;
import static uz.codic.ahmadtea.utils.Consts.statusPending;
import static uz.codic.ahmadtea.utils.Consts.statusSaveAsDraft;
import static uz.codic.ahmadtea.utils.Consts.visitTag;

public class BasketActivity extends BaseActivity implements BasketMvpView,
        OnFragmentInteractionListener,
        MerchantMvpView,
        BasketPresenter.IUpdateFromPresenter {

    private static final String TAG = "TAG";
    BasketMvpPresenter<BasketMvpView> presenter;
   // RecyclerView  recyclerView;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private Order order;
    private List<Comment> list;
    private Visit visit;
    private CompleteApi completeApi;
    private LocationManager locationManager;
    private LocationListener mLocationListener;
    private CompleteObject completeObject;
    private ImageView btn_edit;
    static IUpdateSavedVisits updateSavedVisits;
    private uz.codic.ahmadtea.data.db.entities.Merchant merchant;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        init();
        setBtns();
        btnBack = findViewById(R.id.btn_back);
        presenter = new BasketPresenter<>(this);
        BasketPresenter.setUpdater(this);
        presenter.onAttach(this);
      //  recyclerView = findViewById(R.id.ordered_list_recycler_view);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setAdapter(adapter);
        merchant = (uz.codic.ahmadtea.data.db.entities.Merchant) getIntent().getSerializableExtra("merchant");
        int priceId = getIntent().getIntExtra("priceId",0);
        String orderId = getIntent().getStringExtra("orderId");
        order = (Order)getIntent().getSerializableExtra("order");

        visit = presenter.getVisit(order.getVisitId());
        list = new ArrayList<>();
        if (visit.getId_comment() != null){
            List<Integer> ids = new ArrayList<>();
            String commentsIds = visit.getId_comment().substring(1,(visit.getId_comment().length() - 1));
            List<String> myList = new ArrayList<String>(Arrays.asList(commentsIds.split(", ")));
            for (String str : myList ) {
                ids.add(Integer.parseInt(str));
            }
            if (presenter.getComments(ids) != null){
                list.addAll(presenter.getComments(ids));
            }
        }

        presenter.getBasketList(priceId, orderId, order.getId_payment_type());

        btnBack.setOnClickListener(v -> finish());

    }
    private  void setBtns(){

        findViewById(R.id.btn_save_as_draft).setOnClickListener(v -> {
            isSaved = true;
            completeApi.getVisitObject().setStatus(statusSaveAsDraft);
            completeApi.getOrderObject().setStatus(statusSaveAsDraft);
            presenter.saveAsPending(completeApi,statusSaveAsDraft);

        });

//        if (stringClick.equals("longClick")) {
//            lnrButtons.setVisibility(View.GONE);
//            findViewById(R.id.btn_save_as_pending).setVisibility(View.GONE);
//            transactionFragments(InformationFragment.newInstance(), informationTag);
//        } else if (stringClick.equals("onClick")) {
//            transactionFragments(VisitFragment.newInstance(), visitTag);
//        }


        findViewById(R.id.bnt_send).setOnClickListener(v -> {
                isSaved = true;
                completeApi.getVisitObject().setStatus(Consts.statusSent);
                completeApi.getOrderObject().setStatus(Consts.statusSent);
                presenter.requestSend(completeApi);
                //updateSavedVisits.updateNow();

        });

        findViewById(R.id.btn_send_as_draft).setOnClickListener(v -> {
            isSaved = true;
            completeApi.getOrderObject().setStatus(Consts.statusSendAsDraft);
            completeApi.getVisitObject().setStatus(Consts.statusSendAsDraft);
                presenter.requestSendDraft(completeApi);

        });

        findViewById(R.id.btn_save_as_pending).setOnClickListener(v -> {
            isSaved = true;
            completeApi.getOrderObject().setStatus(statusPending);
            completeApi.getVisitObject().setStatus(statusPending);
            presenter.saveAsPending(completeApi,statusPending);
        });
    }

    private void init() {
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.visit_toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketActivity.this, MerchantActivity.class);
                intent.putExtra("click", "edit");
                intent.putExtra("name", merchant.getLabel());
                intent.putExtra("id_workspace",completeApi.getOrderObject().getId_workspace());
                intent.putExtra("id", merchant.getPid());
                intent.putExtra("completeApi",completeApi);
                startActivity(intent);
            }
        });



       // setSupportActionBar(toolbar);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBasketListReady(List<BasketProduct> basketProducts) {
        List<OrderBasket> orderBaskets = new ArrayList<>();
        for (BasketProduct basketBroduct : basketProducts) {
            orderBaskets.add(basketBroduct.getOrderBasket());
        }
        if (list != null){
            completeApi = new CompleteApi();
            completeApi.setOrderObject(order);
            completeApi.getOrderObject().setDate(CommonUtils.getToday());
            completeApi.setVisitObject(visit);
            completeApi.getVisitObject().setDate(CommonUtils.getToday());
            completeApi.setOrderBasketList(orderBaskets);


            pagerAdapter = new PagerAdapter(getSupportFragmentManager(),basketProducts,order,list,  visit.getNotes());
            viewPager.setAdapter(pagerAdapter);
        }
    }

    @Override
    public void transactionFragments(Fragment fragment, String tag) {

    }

    @Override
    public void onBackClick(String title) {

    }

    @Override
    public void onCompleteObjectReady(CompleteObject completeObject) {
        this.completeObject = completeObject;
      //  infoAction = presenter2.getInfoAction(completeObject.getWorkspace().getId(), completeObject.getMerchant().getId());
       // startVisit();
//        if (infoAction.isAction()){
//            transactionFragments(MerchantPageFragment.newInstance(), "Visit Info");
//            //lnrButtons.setVisibility(View.GONE);
//        }else if (stringClick.equals("longClick")) {
//            //lnrButtons.setVisibility(View.GONE);
//            findViewById(R.id.btn_save_as_pending).setVisibility(View.GONE);
//            transactionFragments(InformationFragment.newInstance(), informationTag);
//        } else if (stringClick.equals("onClick")) {
//            transactionFragments(VisitFragment.newInstance(), visitTag);
//        }else
//        if (stringClick.equals("order")) {
//            lnrButtons.setVisibility(View.GONE);
//            stateProgressBar.setVisibility(View.VISIBLE);
//            findViewById(R.id.btn_forward).setVisibility(View.VISIBLE);
//            transactionFragments(PricesFragment.newInstance(), pricesTag);
//        }
    }

    private void startVisit() {
        Visit visit = getCompleteApi().getVisitObject();
        visit.setId(UUID.randomUUID().toString());
        visit.setId_merchant(getCompleteObject().getMerchant().getId());
        visit.setId_comment(null);
        visit.setNotes(null);
        visit.setTime_start(System.currentTimeMillis());
        visit.setLatitude(0.0);
        visit.setLongitude(0.0);
        visit.setId_filial(getCompleteObject().getWorkspace().getFilial_id());
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void goBack() {

    }

    @Override
    public InfoAction getInfoAction() {
        return null;
    }

    @Override
    public CompleteApi getCompleteApi() {
        return completeApi;
    }

    @Override
    public CompleteObject getCompleteObject() {
        return completeObject;
    }

    @Override
    public void openGallery(File file) {

    }

    @Override
    public void getLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                completeApi.getVisitObject().setLatitude(location.getLatitude());
                completeApi.getVisitObject().setLongitude(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3000, mLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 3000, mLocationListener);

    }

    @Override
    public LinearLayout getLinearButtons() {
        return null;
    }

    @Override
    public List<PhysicalWareHouse> getPhysicalWareHouse() {
        return null;
    }

    public static void setUpdater(IUpdateSavedVisits updater){
        updateSavedVisits = updater;
    }

    @Override
    public void updateVisits() {
      finish();
    }

    public  interface IUpdateSavedVisits{
        void updateNow();
}
}

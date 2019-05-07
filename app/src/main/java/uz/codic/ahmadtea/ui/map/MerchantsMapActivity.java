package uz.codic.ahmadtea.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.db.entities.Merchant;
import uz.codic.ahmadtea.data.db.entities.WorkspaceAndMerchant;
import uz.codic.ahmadtea.ui.base.BaseActivity;
import uz.codic.ahmadtea.ui.map.adapter.AdapterItems;
import uz.codic.ahmadtea.ui.map.adapter.AdapterMerchantsMap;
import uz.codic.ahmadtea.ui.merchants.MerchantListWorspaces;
import uz.codic.ahmadtea.ui.merchants.MerchantsFragment;
import uz.codic.ahmadtea.ui.visit.MerchantActivity;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MerchantsMapActivity extends BaseActivity implements MapMvpView, OnMapReadyCallback {

    private GoogleMap mMap;
    private MapMvpPresenter<MapMvpView> presenter;
    private Merchant mMerchant;
    private List<MerchantListWorspaces> merchants;
    private static double PI_RAD = Math.PI / 180.0;
    private LinearLayout linearLayout;
    private TextView selectMerchent;
    private Button zakasSent;
    private List<Merchant> mMerchants;

    LocationManager locationManager;
    LocationListener mLocationListener;
    Location mLocation;
    ImageView imageViewUpDwn;
    RecyclerView recyclerView;
    AdapterMerchantsMap adapter;
    boolean isimageViewClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter = new MapPresenter<>(MerchantsMapActivity.this);
        presenter.onAttach(this);


        if (getIntent().getBooleanExtra("isPlan", false)) {
            merchants = (List<MerchantListWorspaces>) getIntent().getSerializableExtra("dailyMerchant");

        } else {
            merchants = MerchantsFragment.merchants;
        }
        getLocation();

        //   presenter.getMerchant(pid);
        //presenter.getMerchants();

        linearLayout = findViewById(R.id.id_select_merchant_layout);
        selectMerchent = findViewById(R.id.id_select_merchant);
        zakasSent = findViewById(R.id.id_select_merchant_zakas);
        imageViewUpDwn = findViewById(R.id.id_im_up_down);
        recyclerView = findViewById(R.id.id_recycler_view_merchants_map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterMerchantsMap();
        recyclerView.setAdapter(adapter);

        imageViewUpDwn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (!isimageViewClick) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageViewUpDwn.getLayoutParams();
                    params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    imageViewUpDwn.setLayoutParams(params);
                    isimageViewClick = true;
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageViewUpDwn.getLayoutParams();
                    params.removeRule(RelativeLayout.CENTER_VERTICAL);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    imageViewUpDwn.setLayoutParams(params);
                    isimageViewClick = false;
                }
            }
        });

    }


    private void readyMap() {

        if (merchants != null) {
            for (MerchantListWorspaces merchant : merchants) {
                if(merchant.getMerchant().getLatitude() != null){
                LatLng latLng = new LatLng(merchant.getMerchant().getLatitude(), merchant.getMerchant().getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(merchant.getMerchant().getLabel()));
                }
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //41.311162, 69.279623 tashkent
        LatLng latLng = new LatLng(41.311162, 69.279623);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        readyMap();
    }

    @Override
    public void onMerchantReady(Merchant merchant) {
        mMerchant = merchant;
        LatLng sydney = new LatLng(merchant.getLatitude(), merchant.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .snippet(String.valueOf(merchant.getPid()))
                .title(merchant.getLabel()));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final int pid = Integer.parseInt(marker.getSnippet());
                marker.showInfoWindow();
                linearLayout.setVisibility(View.VISIBLE);
                selectMerchent.setText(marker.getTitle());
                zakasSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //merchants.
                        presenter.getMerchantForZakas(pid);
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void sentZakas(Merchant merchant) {
        Intent intent = new Intent(this, MerchantActivity.class);
        intent.putExtra("name", merchant.getLabel());
        intent.putExtra("id", merchant.getPid());
        intent.putExtra("id_workspace", presenter.id_workspace(merchant.getId()));
        startActivity(intent);
        finish();
    }

    @Override
    public void onMerchantsListReady(List<WorkspaceAndMerchant> list) {
//        merchants = list;
//        LatLng thisMerchant = new LatLng(mMerchant.getLatitude(), mMerchant.getLongitude());
//        for (int i = 0; i < list.size(); i++) {
//            LatLng newMerchant = new LatLng(list.get(i).getMerchant().getLatitude(), list.get(i).getMerchant().getLongitude());
//            if (greatCircleInMeters(thisMerchant, newMerchant)<= 1000){
//                addNewMarker(newMerchant, list.get(i).getMerchant().getLabel(), list.get(i).getMerchant().getI_id());
//            }
//        }

    }

    private void addNewMarker(LatLng newLatLng, String title, int id) {
//        mMap.addMarker(new MarkerOptions()
//                .position(newLatLng)
//                .alpha(0.7f)
//                .snippet(String.valueOf(id))
//                .title(title));
    }

    @SuppressLint("NewApi")
    private void createList() {
        List<AdapterItems> adapterItems = new ArrayList<>();

        LatLng myLat = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        LatLng metchantLatLng;
        for (int i = 0; i < merchants.size(); i++) {
            Merchant item = merchants.get(i).getMerchant();
            AdapterItems temp = new AdapterItems();
            if (item.getLatitude()!= null) {
                metchantLatLng = new LatLng(item.getLatitude(), item.getLongitude());
                Double value = greatCircleInMeters(myLat, metchantLatLng);
                Double truncatedDouble = BigDecimal.valueOf(value)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
                temp.setValue(truncatedDouble);
            }
                temp.setName(item.getLabel());
                adapterItems.add(temp);
        }
        adapterItems.sort(Comparator.comparing(AdapterItems::getValue));

        adapter.setAdapterItems(adapterItems);
    }

    public double greatCircleInMeters(LatLng latLng1, LatLng latLng2) {
        return greatCircleInKilometers(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude);
    }

    public double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }


    public void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mLocation = location;
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    locationManager.removeUpdates(mLocationListener);
                    createList();
                    imageViewUpDwn.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);


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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationListener != null) {
            locationManager.removeUpdates(mLocationListener);
        }

    }
}

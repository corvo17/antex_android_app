package uz.codic.ahmadtea.service.old;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.codic.ahmadtea.data.network.ApiClient;
import uz.codic.ahmadtea.data.network.ApiEndPoint;
import uz.codic.ahmadtea.data.network.ApiService;
import uz.codic.ahmadtea.data.network.model.EmployeeLocation;
import uz.codic.ahmadtea.data.network.model.EmployeeLocations;
import uz.codic.ahmadtea.data.network.model.LocationResponse;


public class ToastService extends Service {

    LocationRequest mLocationRequest;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyLog", "Create");
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d("MyLog", "gpsprovider  " + gpsEnabled);
        Log.d("MyLog", "network  " + networkEnabled);

//        apiService = ApiClient.getApiClient(getApplicationContext()).create(ApiService.class);
//        Log.d("MyLog", apiService.toString());

        apiService = new Retrofit
                .Builder()
                .baseUrl(ApiEndPoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("Users", Context.MODE_PRIVATE);

        Log.d("MyLog", sharedPreferences.getString("token", "223232233"));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    mLastLocation = locationList.get(locationList.size() - 1);
                }

                Log.d("MyLog", mLastLocation.toString());

                EmployeeLocation location = new EmployeeLocation();
                location.setLat(mLastLocation.getLatitude());
                location.setLng(mLastLocation.getLongitude());
                location.setReceived_time(System.currentTimeMillis());

                List<EmployeeLocation> employeeLocations = new ArrayList<>();
                employeeLocations.add(location);

                apiService.sendLocation(sharedPreferences.getString("token", ""), employeeLocations).enqueue(new Callback<LocationResponse>() {
                    @Override
                    public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {


                        Log.d("MyLog", "OnResponse" + response.body().getMessage());


                        stopSelf();
                    }

                    @Override
                    public void onFailure(Call<LocationResponse> call, Throwable t) {
                        Log.d("MyLog", "OnFailure" + t.getMessage());
                        stopSelf();
                    }
                });

                mFusedLocationClient.removeLocationUpdates(mLocationCallback);


//                try {
//                    apiService.sendLocation(sharedPreferences.getString("token", ""), location).execute();
//                } catch (IOException e) {
//                    Log.d("MyLog", e.toString());
//                    e.printStackTrace();
//                }


            }
        };


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyLog", "destroy service");
//        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }

}

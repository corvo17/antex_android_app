package uz.codic.ahmadtea.data.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.codic.ahmadtea.data.prefs.PrefHelper;

public class ApiClient {

    private static final long REQUEST_TIMEOUT = 60;
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static OkHttpClient okHttpClient;
    private static OkHttpClient okHttpClient2;
    PrefHelper prefHelper;
    Context mContext;
    String base_url;


    public ApiClient(Context context, String base_url) {
        mContext = context;
        this.base_url = base_url;
    }

    public  Retrofit getApiClient(){


        if(okHttpClient == null){
            initOkHttp(mContext);
        }

        if(retrofit == null){
            retrofit = new Retrofit
                            .Builder()
                            .baseUrl(base_url)
                            .client(okHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        }
        return retrofit;

    }

    public  Retrofit getApiClient2(Context context){

        if(okHttpClient2 == null){
            initOkHttp(context);
        }

        if(retrofit2 == null){
            retrofit2 = new Retrofit
                    .Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;

    }


    public  void initOkHttp(Context context){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");

//                // Adding Authorization token (API Key)
//                // Requests will be denied without API key
//                if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
//                    requestBuilder.addHeader("Authorization", PrefUtils.getApiKey(context));
//                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.addInterceptor(new ChuckInterceptor(context));

        okHttpClient = httpClient.build();
    }


}

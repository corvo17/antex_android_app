package uz.codic.ahmadtea.errors;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.nio.channels.Channel;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uz.codic.ahmadtea.MvpApplication;
import uz.codic.ahmadtea.R;
import uz.codic.ahmadtea.data.AppDataManager;
import uz.codic.ahmadtea.data.network.model.ErrorBody;
import uz.codic.ahmadtea.data.network.model.ErrorObject;
import uz.codic.ahmadtea.errors.error_activity.ErrorActivity;
import uz.codic.ahmadtea.utils.Consts;

public class ErrorClass{

    private static AppDataManager appDataManager;

    private static Context context;

    public static void init(Context context){
        ErrorClass.context = context;
        ErrorClass.appDataManager = new AppDataManager(context);

    }

    @SuppressLint("NewApi")
    public static void log(String mdg, Exception exception) {
        //log into db
        //send
        exception.printStackTrace();
        String id = UUID.randomUUID().toString();
        Log.d("baxtiyor", "mesage: " + mdg);
        Log.d("baxtiyor", "id: " + id);
        Log.d("baxtiyor", "API_version: " + Build.VERSION.SDK_INT);
        Log.d("baxtiyor", "OS_version: " + Build.VERSION.RELEASE);
        Log.d("baxtiyor", "OS_version: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        Log.d("baxtiyor", "active_internet_connection: " + isOnline());
        Log.d("baxtiyor", "timestamp: " + System.currentTimeMillis());
        Log.d("baxtiyor", "error: " + exception.getMessage());
        Log.d("baxtiyor", "error_type: " + exception.getClass().getCanonicalName());
        Log.d("baxtiyor", "error_toSting: " + exception.toString());
        for (StackTraceElement stackTraceElement :exception.getStackTrace()) {
            Log.d("baxtiyor", "error_printStackTrace: " + stackTraceElement.toString());
        }
        openNotifi(id);
        //sendError(id, exception);
    }

    public static void log(Exception exception) {
        //log into db
        //send
        exception.printStackTrace();
        String id = UUID.randomUUID().toString();
        Log.d("baxtiyor", "id: " + id);
        Log.d("baxtiyor", "API_version: " + Build.VERSION.SDK_INT);
        Log.d("baxtiyor", "OS_version: " + Build.VERSION.RELEASE);
        Log.d("baxtiyor", "OS_version: " + Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        Log.d("baxtiyor", "active_internet_connection: " + isOnline());
        Log.d("baxtiyor", "timestamp: " + System.currentTimeMillis());
        Log.d("baxtiyor", "error: " + exception.getMessage());
        Log.d("baxtiyor", "error_type: " + exception.getClass().getCanonicalName());
        Log.d("baxtiyor", "error_toSting: " + exception.toString());
        for (StackTraceElement stackTraceElement :exception.getStackTrace()) {
            Log.d("baxtiyor", "error_printStackTrace: " + stackTraceElement.toString());
        }

        openNotifi(id);
        //sendError(id, exception);

    }

    @SuppressLint("NewApi")
    public static void openNotifi(){
        String id = UUID.randomUUID().toString();

        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);

        final Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("message", "mm");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.start_icon)
                .setChannelId(CHANNEL_ID)
                .setContentText("Произошла ошибка!\n" +
                        "код: общая ошибка\n" +
                        "№: " +id + "\n" +
                        "\n" +
                        "просим синхронизировать приложение\n" +
                        "и отправить скриншот этой ошибки\n" +
                        "в техподдержку,\n" +
                        "телеграм: +998908228585")
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(1, builder.build());
    }

    @SuppressLint("NewApi")
    public static void openNotifi(String id){
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);

        final Intent intent = new Intent(context, ErrorActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("message", "mm");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.start_icon)
                .setChannelId(CHANNEL_ID)
                .setContentText("Произошла ошибка!\n" +
                        "код: общая ошибка\n" +
                        "№: " +id + "\n" +
                        "\n" +
                        "просим синхронизировать приложение\n" +
                        "и отправить скриншот этой ошибки\n" +
                        "в техподдержку,\n" +
                        "телеграм: +998908228585")
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        manager.notify(1, builder.build());
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void sendError(String id, Exception e){
        ErrorObject errorObject = new ErrorObject();
        ErrorBody body = new ErrorBody();
        body.setAPI_version(String.valueOf(Build.VERSION.SDK_INT));
        body.setOS_version(String.valueOf(Build.VERSION.RELEASE));
        body.setDevice_model(Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL);
        body.setTimestamp(System.currentTimeMillis());
        body.setError_log(e.getMessage());
        body.setActive_internet_connection(isOnline());
        errorObject.setBody(body);
        errorObject.setId(id);
        errorObject.setApp_client_type(Consts.APP_CLIENT_TYPE);
        appDataManager.sendError(appDataManager.getToken(), errorObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }



}

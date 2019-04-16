package uz.codic.ahmadtea.service.old;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;


import static android.content.Context.ALARM_SERVICE;

public class ToastBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int NOTIFICATION_ID = 12345;

        Log.d("MyLog", "Onreceive");

        Calendar now = Calendar.getInstance();
        Calendar after = Calendar.getInstance();
        after.set(Calendar.HOUR, 19);
        after.set(Calendar.MINUTE, 0);
        after.set(Calendar.SECOND, 0);


        if (now.getTimeInMillis() < after.getTimeInMillis()) {
            Log.d("MyLog",after.getTime().toString());
            Log.d("MyLog",now.getTime().toString());
            Log.d("MyLog", "TTT");
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            Calendar cal_alarm = Calendar.getInstance();
//            cal_alarm.set(Calendar.HOUR, 12);
//            cal_alarm.set(Calendar.MINUTE, 33);
//            cal_alarm.set(Calendar.SECOND, 0);

            Intent intent1 = new Intent(context, ToastBroadCastReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= 23) {
                Log.d("MyLog", "23");
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis() + 60 * 1000, pendingIntent1);
            } else if (Build.VERSION.SDK_INT >= 19) {
                Log.d("MyLog", "19");
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis() + 60 * 1000, pendingIntent1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, 60 * 1000, pendingIntent1);
            }

            if (!isServiceRunning(context)) {
                Log.d("MyLog", "check service ");
                Intent serviceIntent = new Intent(context, ToastService.class);
                context.startService(serviceIntent);
            }


//            NotificationCompat.Builder builder =
//                    new NotificationCompat.Builder(context)
//                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle("My Notification Title")
//                            .setContentText("Something interesting happened")
//                            .setOnlyAlertOnce(false) // so when data is updated don't make sound and alert in android 8.0+
//                            .setOngoing(true);
//
//
//            nManager.notify(NOTIFICATION_ID, builder.build());
//            Log.d("MyLog", "Notification is always running");

        } else {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
            Log.d("MyLog",after.getTime().toString());
            Log.d("MyLog",now.getTime().toString());

//            nManager.cancel(NOTIFICATION_ID);
            Log.d("MyLog", "Notification remove");
        }


    }

    private boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ToastService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
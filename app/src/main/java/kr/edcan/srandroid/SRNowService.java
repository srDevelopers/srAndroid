package kr.edcan.srandroid;

/**
 * Created by JunseokOh on 2016. 11. 9..
 */

import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Junseok on 2016. 1. 10..
 */
public class SRNowService extends Service {

    final static int INTENT_KEY = 1208;
    Intent startMain;
    public static Service service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = this;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.profile)
                .setContentTitle("SRNow")
                .setContentText("SRNow를 실행하시려면 클릭하세요.");
        startMain = new Intent(this, SrNowActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(startMain);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        startForeground(INTENT_KEY, builder.build());
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
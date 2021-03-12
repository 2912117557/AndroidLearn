package com.example.myapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;

public class BackgroundService extends Service {
    private String CHANNEL_ID2 = "channel_2";
    private int notificationId = 1;
    private NotificationCompat.Builder notification;
    private MyCallback callback;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private static class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.i("service","run");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public interface MyCallback{
        void sendMessage(String data);
    }

    public void setCallback(MyCallback callback){
        this.callback = callback;
    }

    @Override
    public void onCreate() {
        Log.i("service","create");
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name2";
            String description = "channel_description2";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID2, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification =
                new NotificationCompat.Builder(this, CHANNEL_ID2)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setSmallIcon(R.drawable.ic_1)
                        .setContentIntent(pendingIntent);
    }

    int i=1;
    Handler handler = new Handler();
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("service","start");
        int type = intent.getIntExtra("type",1);

        switch (type){
            case 2:
                Log.i("service","startForeNew");
                startForeground(notificationId,notification.build());
                break;
            case 3:
                Log.i("service","startFore");
                startForeground(notificationId,notification.build());
                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BackgroundService.this,String.valueOf(i++),Toast.LENGTH_SHORT).show();
                                }
                            });
                            try{
                                Thread.sleep(3000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            default:
                callback.sendMessage("fromService");
        }
        Message msg = serviceHandler.obtainMessage();
        serviceHandler.sendMessage(msg);
        return START_REDELIVER_INTENT;
    }

    private IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service","bind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("service","unBind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i("service","reBind");
    }

    @Override
    public void onDestroy() {
        Log.i("service","destory");
        serviceLooper.quit();
    }

    public void stopFore(){
        Log.i("service","stopFore");
        stopForeground(true);
    }

    public void updateNot(String text){
        notification.setContentText(text);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification.build());
    }
}

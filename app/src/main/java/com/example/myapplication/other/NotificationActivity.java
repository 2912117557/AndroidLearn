package com.example.myapplication.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.regAndLogin.RegisterActivity;

public class NotificationActivity extends AppCompatActivity {

    private String CHANNEL_ID = "channel_1";
    private String CHANNEL_ID2 = "channel_2";
    private int notificationId = 1;
    private static final String KEY_TEXT_REPLY = "key_text_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("notify","create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name2";
            String description = "channel_description2";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID2, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        IntentFilter filter = new IntentFilter("com.example.myapplication.BroadcastReceiver1");
        registerReceiver(receiver, filter);

    }

    PendingIntent pendingIntent, pendingIntent2,pendingIntent3;
    NotificationCompat.Action action3;
    public void onButton1Click(View view){

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Log.i("notify","full");

        Intent intent = new Intent(this, RegisterActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent2 = new Intent();
        intent2.setAction("com.example.myapplication.BroadcastReceiver1");
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);

        Intent intent3 = new Intent(this, NotificationActivity.class);
        pendingIntent3 = PendingIntent.getActivity(this, 0, intent3, 0);

        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("ReplyHint")
                .build();
        Bitmap bitmap =  BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        action3 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
                        "Reply", pendingIntent3)
                        .addRemoteInput(remoteInput)
                        .build();
        Spanned str = Html.fromHtml("<b>Title</b>");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID2)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(str)
//                .setLargeIcon(bitmap)
//                .setFullScreenIntent(pendingIntent, true)
                .setContentText("do you receive my question?\n")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("do you receive my question?\nMuch longer text that cannot fit one line" +
//                                " Much longer text that cannot fit one line"))
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Much longer text that cannot fit one line Much longer text that cannot fit one line")
                        .addLine("Much longer text that cannot fit one line Much longer text that cannot fit one line")
                        .addLine("Much longer text that cannot fit one line Much longer text that cannot fit one line")
                )
//                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher,"NeedLogin",pendingIntent2)
                .addAction(action3);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }


    public void onButton2Click(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent2 = new Intent();
        intent2.setAction("com.example.myapplication.BroadcastReceiver1");
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);

        Intent intent3 = new Intent(this, NotificationActivity.class);
        pendingIntent3 = PendingIntent.getActivity(this, 0, intent3, 0);

        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("ReplyHint")
                .build();

        action3 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
                        "Reply", pendingIntent3)
                        .addRemoteInput(remoteInput)
                        .build();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title2")
                .setContentText("Content Text")
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1/* #1: pause button */)
                        )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(R.mipmap.ic_launcher,"NeedLogin",pendingIntent2)
                .addAction(action3)
                .addAction(action3);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    NotificationCompat.Builder notification;
    Person person1;
    Person person2;
    NotificationCompat.MessagingStyle messagingStyle;
    public void onButton3Click(View view){
        Intent intent2 = new Intent();
        intent2.setAction("com.example.myapplication.BroadcastReceiver1");
        pendingIntent2 = PendingIntent.getBroadcast(this, 1, intent2, 0);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("ReplyHint")
                .build();
        action3 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
                "Reply", pendingIntent2)
                .addRemoteInput(remoteInput)
                .build();
        person1 = new Person.Builder()
                .setIcon(IconCompat.createWithResource(getApplicationContext(),R.mipmap.ic_launcher_round))
                .setName("Me")
                .build();
        person2 = new Person.Builder()
                .setName("Cowoker")
                .build();
        messagingStyle = new NotificationCompat.MessagingStyle(person1)
//                .setConversationTitle("Team lunch")
                .addMessage("Hi", 1, (Person) null)
                .addMessage("What's up?", 2, person2)
                .addMessage("Not much", 3, (Person) null)
                .addMessage("Not much", 4, (Person) null)
                .addMessage("How about lunch?", 5, person2);
        notification = new NotificationCompat.Builder(this, CHANNEL_ID2)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(messagingStyle)
                .addAction(action3);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, notification.build());

    }

    final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("notify", "receive");
            String ans = "nothing";
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                ans = (String) remoteInput.getCharSequence(KEY_TEXT_REPLY);
            }
            messagingStyle.addMessage(ans, 6, (Person) null);
            notification.setStyle(messagingStyle);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(2, notification.build());
        }
    };


    public void onButton4Click(View view){
//        messagingStyle.addMessage("Hi2", 5, (Person) null)
//                .addMessage("What's up2?", 6, person2);
//        notification.setStyle(messagingStyle);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(2, notification.build());

        //use constant ID for notification used as group summary
        int SUMMARY_ID = 0;
        String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

        Notification newMessageNotification1 =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title1")
                        .setContentText("You will not believe...")
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .build();

        Notification newMessageNotification2 =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title2")
                        .setContentText("Please join us to celebrate the...")
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .build();

        Notification summaryNotification =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Summary")
                        //set content text to support devices running API level < 24
                        .setContentText("Two new messages")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //build summary info into InboxStyle template
//                        .setStyle(new NotificationCompat.InboxStyle()
//                                .addLine("Alex Faarborg  Check this out")
//                                .addLine("Jeff Chang    Launch Party")
////                                .setBigContentTitle("2 new messages")
//                                .setSummaryText("janedoe@example.com"))
                        //specify which group this notification belongs to
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, newMessageNotification1);
        notificationManager.notify(2, newMessageNotification2);
        notificationManager.notify(SUMMARY_ID, summaryNotification);

    }



    @Override
    protected void onResume() {
        super.onResume();
        String ans="nothing";
        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
        if (remoteInput != null) {
            ans = (String)remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        ((TextView)findViewById(R.id.notificationTextView1)).setText(ans);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("notify","new intent");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}


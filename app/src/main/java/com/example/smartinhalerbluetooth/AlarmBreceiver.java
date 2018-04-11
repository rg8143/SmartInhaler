package com.example.smartinhalerbluetooth;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Rahul on 11-03-2018.
 */

public class AlarmBreceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {


//
//        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
//        mediaPlayer.setScreenOnWhilePlaying(true);
//        mediaPlayer.start();
//
//        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF) && mediaPlayer.isPlaying())
//            mediaPlayer.stop();

        Log.d("alarm","alarm set ringing");



         Calendar c= Calendar.getInstance();
        SimpleDateFormat toshow = new SimpleDateFormat("hh:mm aa");

        Intent resultIntent = new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Time To Take Your Inhaler:)")
                .setContentText("It's "+ toshow.format(c.getTime())+" NOW")
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());




    }

}

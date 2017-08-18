package com.example.bilal.arksolutions_jomwork.Helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;


public class AlarmReceiverPrayers extends BroadcastReceiver {

    public static final String STOP_SOUND = "stop_sound";

    int[] arrPrayers = {0, 2, 3, 4, 5, 1};

    int entryId;
    Boolean chkFajar;
    Context context;

    private int indexSoundOption = 0;
    public static boolean isWorkStarted=false;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
      isWorkStarted=true;
        Toast.makeText(context,"received",Toast.LENGTH_SHORT).show();


       // sendNotification();
        // showNamazNotification();
    }


    private void sendNotification() {
/*
        int notificationID = entryId;

        // Intent resultIntent = new Intent(context, MainActivity.class);
        // resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // resultIntent.putExtra("notificationID", notificationID);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		*//*
         * PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, GcmResigterActivity.class), 0);
		 * 
		 * NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context) .setSmallIcon(R.drawable.ic_launcher) .setContentTitle("GCM Notification") .setStyle(new NotificationCompat.BigTextStyle() .bigText(msg)) .setContentText(msg);
		 * 
		 * mBuilder.setContentIntent(contentIntent); mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		 *//*

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

      *//*  String[] arrPrayers = {context.getString(R.string.fajar_txt), context.getString(R.string.zuahr_txt), context.getString(R.string.asar_txt), context.getString(R.string.maghrib_txt), context.getString(R.string.isha_txt), context.getString(R.string.sunrise_txt)};
        String message = arrPrayers[entryId - 1] + " " + context.getString(R.string.salat_timings) + " " + context.getString(R.string.time);*//*

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(getNotificationIcon()).setLargeIcon(bm).setAutoCancel(true).setContentTitle(context.getResources().getString(R.string.app_name)).setContentText(message);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainIndexActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.putExtra("notificationID", notificationID);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // context ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainIndexActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        Uri uri = null;

        if (indexSoundOption == 0) {//Settings Default Device Notification Sound
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else if (indexSoundOption != 1) {// If not Silent Mode
            sendBroadcastAlarm();
            String uriAudio = "";
//            if (chkFajar) {
//                uriAudio = "azan_4";
//            } else {
//                uriAudio = "azan_" + (indexSoundOption - 1);
//            }

            String[] adhanSounds = {"adhan_fajr_madina","adhan_madina", "most_popular_adhan" , "azan_by_nasir_a_qatami", "azan_mansoural_zahrani", "mishary_rashid_al_afasy","adhan_from_egypt"};

            uriAudio = adhanSounds[indexSoundOption - 2];

            uri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + uriAudio);
        } else {// If Silent Mode
            uri = null;
        }

        if (uri != null) {
            mBuilder.setSound(uri);
        }
        mNotificationManager.notify(notificationID, mBuilder.build());*/
    }
  /*  private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_launcher : R.mipmap.ic_launcher;
    }


    private void sendBroadcastAlarm() {

        Intent intentBroadCast = new Intent(STOP_SOUND);
        context.sendBroadcast(intentBroadCast);
    }*/
}
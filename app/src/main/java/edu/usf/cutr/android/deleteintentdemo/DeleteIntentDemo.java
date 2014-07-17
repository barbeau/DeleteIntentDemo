package edu.usf.cutr.android.deleteintentdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DeleteIntentDemo extends ActionBarActivity {

    public static final String TAG = "DeleteIntentDemo";

    private static NotificationManager mNM;

    //startId=43 action=com.joulespersecond.seattlebusbot.action.NOTIFY uri=content://com.joulespersecond.oba/trip_alerts/2
    public static final Uri mTripUri = Uri.parse("content://com.joulespersecond.oba/trip_alerts/1");
    public static final String mRouteId = "Route 5";
    public static final String ACTION_CANCEL =
            "com.joulespersecond.seattlebusbot.action.SCHEDULE";
    public static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_intent_demo);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Context mContext;
        Button btnBroadcast;
        Button btnService;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_delete_intent_demo, container, false);

            mContext = getActivity();
            btnBroadcast = (Button) rootView.findViewById(R.id.btnShowNotificationBroadcast);
            btnService = (Button) rootView.findViewById(R.id.btnShowNotificationService);

            btnBroadcast.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    id++;

                    // Deliver DeleteIntent to BroadcastReceiver
//                    Notification notification = createNotification(mContext, AlarmReceiver.class);
//                    showNotification(mContext, notification, AlarmReceiver.class);
                    final Intent intent = new Intent(ACTION_CANCEL);
                    getActivity().sendBroadcast(intent);
                }
            });

            btnService.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    id++;

                    // Deliver DeleteIntent to Service
//                    Notification notification = createNotification(mContext, AlarmService.class);
//                    showNotification(mContext, notification, AlarmService.class);
                }
            });

            return rootView;
        }
    }

    private static Notification createNotification(Context context, Class<?> cls) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setDeleteIntent(createDeleteIntent(context, cls))
                .setAutoCancel(true)
                .build();
    }


    private static PendingIntent createDeleteIntent(Context context, Class<?> cls) {
        Intent deleteIntent = new Intent(context, cls);
        deleteIntent.setAction(DeleteIntentDemo.ACTION_CANCEL);
        deleteIntent.setData(mTripUri);

        if (cls.getSuperclass().equals(BroadcastReceiver.class)) {
            // BroadcastReceiver
            return PendingIntent.getBroadcast(context, 0,
                    deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            // Service
            return PendingIntent.getService(context, 0,
                    deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private static void showNotification(Context context, Notification notification, Class<?> cls) {
        /*
         When setLatestInfo() is called, the deleteIntent will be erased
         (within notification.setLatestEventInfo()), and we won't see any Intents in either the
         AlarmReceiver or AlarmService when the notification is dismissed.

         Workarounds:
            a) If you comment out setLatestInfo(), deleteIntent is delivered.  However,
               you can't update a pre-existing Notification object.
            b) Uncomment the block of code that re-registers the deleteIntent after calling
               setLatestInfo().
          */

        // Workaround a) - Comment out the below line
        setLatestInfo(context, notification);

        // Workaround b) - Uncomment the below lines
//        Intent d = new Intent(context, cls);
//        d.setAction(DeleteIntentDemo.ACTION_CANCEL);
//        d.setData(mTripUri);
//        notification.deleteIntent = PendingIntent.getBroadcast(context, 0,
//                d, PendingIntent.FLAG_UPDATE_CURRENT);

        mNM.notify(id, notification);
    }

    @SuppressWarnings("deprecation")
    private static void setLatestInfo(Context context, Notification notification) {
        final String title = context.getString(R.string.app_name);

        // BUG - Android 4.4.3/4.4.4!  The below call erases any previously registered deleteIntent
        // for this notification.  This works fine on Android 4.1.2 and below.
        notification.setLatestEventInfo(context,
                title,
                mRouteId + " departed",
                null);
    }
}

package edu.usf.cutr.android.deleteintentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DeleteIntentDemo extends Activity {

    public static final String TAG = "DeleteIntentDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_intent_demo);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(), "")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Button btn;

        public static final String ACTION_CANCEL =
                "com.joulespersecond.seattlebusbot.action.CANCEL";
        private int counter = 0;

        private NotificationManager mNM;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_delete_intent_demo, container, false);

            btn = (Button) rootView.findViewById(R.id.btnShowNotification);

            mNM = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    counter++;
                    //startId=43 action=com.joulespersecond.seattlebusbot.action.NOTIFY uri=content://com.joulespersecond.oba/trip_alerts/2
                    final Uri tripUri = Uri.parse("content://com.joulespersecond.oba/trip_alerts/1");
                    final String routeId = "Route 5";

                    // Set our state to notified
                    Notification notification = createNotification(getActivity(), tripUri);

                    setLatestInfo(getActivity(), notification, routeId, tripUri);
                    mNM.notify(counter, notification);
                }
            });

            return rootView;
        }
    }

    private static Notification createNotification(Context context, Uri alertUri) {
        Log.d(TAG, "Creating notification for alert: " + alertUri);
        Intent deleteIntent = new Intent(context, AlarmReceiver.class);
        deleteIntent.setAction(PlaceholderFragment.ACTION_CANCEL);
        deleteIntent.setData(alertUri);

        return new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setDeleteIntent(PendingIntent.getBroadcast(context, 0,
                        deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                        //.setLights(0xFF00FF00, 1000, 1000)
                        //.setVibrate(VIBRATE_PATTERN)
                .build();
    }

    @SuppressWarnings("deprecation")
    private static void setLatestInfo(Context context, Notification notification,
            String routeId, Uri alertUri) {
        final String title = context.getString(R.string.app_name);

        notification.setLatestEventInfo(context,
                title,
                routeId + " departed",
                null);

        // If you uncomment the below, the DeleteIntent will be received
//        Intent deleteIntent = new Intent(context, AlarmReceiver.class);
//        deleteIntent.setAction(PlaceholderFragment.ACTION_CANCEL);
//        deleteIntent.setData(alertUri);
//        notification.deleteIntent = PendingIntent.getBroadcast(context, 0,
//                deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

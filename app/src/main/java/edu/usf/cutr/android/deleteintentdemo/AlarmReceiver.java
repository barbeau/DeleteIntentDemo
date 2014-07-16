package edu.usf.cutr.android.deleteintentdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receiver got deleteIntent! Action - " + intent.getAction());
        Toast.makeText(context, "Receiver got deleteIntent", Toast.LENGTH_LONG).show();
    }
}

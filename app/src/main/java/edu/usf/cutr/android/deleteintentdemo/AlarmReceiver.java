package edu.usf.cutr.android.deleteintentdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "In onReceive with intent action " + intent.getAction());
        Toast.makeText(context, "AlarmReceiver got CANCEL Intent", Toast.LENGTH_LONG).show();
    }
}

package edu.usf.cutr.android.deleteintentdemo;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service {

    public static final String TAG = "AlarmService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String action = intent.getAction();
        final Uri uri = intent.getData();
        Log.d(TAG, "Service got deleteIntent! Command: startId=" + startId +
                " action=" + action +
                " uri=" + uri);
        Toast.makeText(this, "Service got deleteIntent", Toast.LENGTH_LONG).show();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

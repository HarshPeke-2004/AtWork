package com.example.atwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiver";
    private static final String ACTION_PROCESS_GEOFENCE_EVENT = "com.example.atwork.ACTION_PROCESS_GEOFENCE_EVENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent action: " + intent.getAction());
        if (!ACTION_PROCESS_GEOFENCE_EVENT.equals(intent.getAction())) {
            Log.e(TAG, "Unexpected intent action: " + intent.getAction());
            return;
        }

        Toast.makeText(context, "Geofence triggered", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "GeofenceBroadcastReceiver triggered");

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent == null) {
            Log.e(TAG, "GeofencingEvent is null");
            return;
        }

        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent error: " + geofencingEvent.getErrorCode());
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        if (geofenceList == null || geofenceList.isEmpty()) {
            Log.e(TAG, "No triggering geofences found");
            return;
        }

        // Initialize DatabaseHelper to store entry/exit times
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        for (Geofence geofence : geofenceList) {
            String geofenceId = geofence.getRequestId();
            Log.d(TAG, "Triggered Geofence ID: " + geofenceId);

            int transitionType = geofencingEvent.getGeofenceTransition();
            String transition;

            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    transition = "ENTER";
                    Toast.makeText(context, "Marked IN", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Entering geofence");
                    notificationHelper.sendHighPriorityNotification("Marked IN", "Your attendance has been marked", MapsActivity.class);
                    break;

                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    transition = "EXIT";
                    Toast.makeText(context, "Marked OUT", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Exiting geofence");
                    notificationHelper.sendHighPriorityNotification("Marked OUT", "You have left the vicinity", MapsActivity.class);
                    break;

                default:
                    Log.e(TAG, "Unknown geofence transition type: " + transitionType);
                    return;
            }

            // Insert the transition type and geofence ID into the database
            boolean insertSuccess = databaseHelper.insertGeofenceLog(geofenceId, transition);
            if (insertSuccess) {
                Log.d(TAG, "Geofence log inserted successfully.");
            } else {
                Log.e(TAG, "Failed to insert geofence log.");
            }
        }
    }
}

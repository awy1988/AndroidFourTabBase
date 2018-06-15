package com.docomotv.service.fcm;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DocomotvFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = DocomotvFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        Toast.makeText(this, "messageReceived!!!", Toast.LENGTH_SHORT).show();
    }
}

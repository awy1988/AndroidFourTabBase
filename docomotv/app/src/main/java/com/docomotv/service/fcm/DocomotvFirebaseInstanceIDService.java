package com.docomotv.service.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class DocomotvFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private final String TAG = DocomotvFirebaseInstanceIDService.class.getSimpleName();

    // TODO 在google firebase平台上申请账号，绑定App，网址：https://firebase.google.com/

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {

    }
}

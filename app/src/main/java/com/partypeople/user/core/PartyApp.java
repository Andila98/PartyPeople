package com.partypeople.user.core;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.partypeople.user.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class PartyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public boolean usersignedIn() {
        return FirebaseAuth.getInstance().getUid() != null;
    }
}

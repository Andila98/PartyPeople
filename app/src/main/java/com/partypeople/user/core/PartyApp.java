package com.partypeople.user.core;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PartyApp extends Application {
    public boolean usersignedIn(){
        return FirebaseAuth.getInstance().getUid()!= null;

    }

}

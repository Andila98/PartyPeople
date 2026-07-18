package com.partypeople.user.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Provides Firebase service singletons for injection instead of
 * calling getInstance() throughout the codebase.
 */
@Module
@InstallIn(SingletonComponent.class)
public final class FirebaseModule {

    private FirebaseModule() {
    }

    @Provides
    @Singleton
    public static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public static FirebaseFirestore provideFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    public static FirebaseStorage provideStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    public static FirebaseDatabase provideDatabase() {
        return FirebaseDatabase.getInstance();
    }
}

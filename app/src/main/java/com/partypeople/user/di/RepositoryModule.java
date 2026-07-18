package com.partypeople.user.di;

import com.partypeople.user.data.repository.EventRepository;
import com.partypeople.user.data.repository.EventRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract EventRepository bindEventRepository(EventRepositoryImpl impl);
}

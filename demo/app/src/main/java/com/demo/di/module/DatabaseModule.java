package com.demo.di.module;

import android.content.Context;
import androidx.room.Room;
import com.demo.data.database.AppDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DatabaseModule {

    private Context mContext;

    public DatabaseModule(Context context) {
        this.mContext = context;
    }

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase() {
        AppDatabase db = Room.databaseBuilder(mContext,
            AppDatabase.class, "database-name").build();
        return db;
    }
}

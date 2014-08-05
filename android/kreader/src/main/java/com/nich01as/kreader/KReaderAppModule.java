package com.nich01as.kreader;

import android.app.Application;
import android.view.LayoutInflater;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.nich01as.kreader.activity.ButterActivity;
import com.nich01as.kreader.activity.ButterListActivity;
import com.nich01as.kreader.services.ServiceModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nicholaszhao on 8/2/14.
 */
@Module(
        injects = {
                ButterListActivity.ButterListAdapter.class,
                ButterActivity.class
        },
        complete = true, library = true,
        includes = {ServiceModule.class})
public class KReaderAppModule {

    private Application application;

    public KReaderAppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflactor() {
        return LayoutInflater.from(application);
    }
}

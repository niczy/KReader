package com.nich01as.kreader;

import android.app.Application;
import android.app.Service;

import com.nich01as.kreader.services.ServiceModule;

import dagger.ObjectGraph;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class KReaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerInjector.init(this);
    }
}

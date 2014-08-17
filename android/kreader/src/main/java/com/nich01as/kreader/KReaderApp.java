package com.nich01as.kreader;

import android.app.Application;
import android.app.Service;

import com.nich01as.kreader.services.ServiceModule;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import dagger.ObjectGraph;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class KReaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerInjector.init(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            .build();
        ImageLoader.getInstance().init(config);
    }
}

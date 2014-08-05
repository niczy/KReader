package com.nich01as.kreader;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class DaggerInjector {

    private static ObjectGraph objectGraph;

    public static void init(Application application) {
        objectGraph = ObjectGraph.create(new KReaderAppModule(application));
    }

    public static void inject(Object o) {
        objectGraph.inject(o);
    }
}

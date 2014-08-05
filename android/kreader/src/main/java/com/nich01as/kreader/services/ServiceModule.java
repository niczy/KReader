package com.nich01as.kreader.services;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nich01as.kreader.activity.ButterListActivity;
import com.nich01as.kreader.activity.MainActivity;
import com.nich01as.kreader.activity.NewUserActivity;
import com.nich01as.kreader.activity.PostButterActivity;
import com.nich01as.kreader.activity.TagsActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nicholaszhao on 8/2/14.
 */
@Module(
    injects = {
            MainActivity.class,
            NewUserActivity.class,
            TagsActivity.class,
            PostButterActivity.class,
            ButterListActivity.class
    },
    library = true)
public class ServiceModule {

    @Provides
    @Singleton
    Kreaderservice provideEndpointService() {
        return new Kreaderservice.Builder(
                AndroidHttp.newCompatibleTransport(),
                AndroidJsonFactory.getDefaultInstance(),
                null)
            .setApplicationName("KReader")
            .build();
    }
}

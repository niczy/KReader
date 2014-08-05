package com.nich01as.kreader.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTagCollection;
import com.google.common.collect.Lists;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/3/14.
 */
public class PostButterActivity extends Activity {

    @Inject
    Kreaderservice kreaderservice;

    private TextView mButter;
    private TextView mTag;

    public PostButterActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_post_butter);
        mButter = (TextView) findViewById(R.id.butter);
        mTag = (TextView) findViewById(R.id.tag);
    }

    public void onOkClicked(View view) {
        String butterContent = mButter.getText().toString();
        String tagName = mTag.getText().toString();
        final ApiApiModelButter butter = new ApiApiModelButter();
        butter.setContent(butterContent);
        ApiApiModelTagCollection tagCollection = new ApiApiModelTagCollection();
        ApiApiModelTag tag = new ApiApiModelTag();
        tag.setName(tagName);
        tagCollection.setItems(Lists.newArrayList(tag));
        butter.setTags(tagCollection);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    kreaderservice.postButter(butter).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}

package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTagCollection;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class TagsActivity extends Activity {

    @Inject
    Kreaderservice kreaderservice;

    TextView mTags;
    TextView mNewTagName;

    public TagsActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tags);
        mTags = (TextView) findViewById(R.id.tags);
        mNewTagName = (TextView) findViewById(R.id.tag);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final ApiApiModelTagCollection tagCollection =
                        kreaderservice.listFollowingTags("nicholas").execute();
                    Log.d("tags", tagCollection.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tagCollection.getItems() != null) {
                                StringBuilder sb = new StringBuilder();
                                for (ApiApiModelTag tag : tagCollection.getItems()) {
                                    sb.append(tag.getName() + " ");
                                }
                                mTags.setText(sb.toString());
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void onFollowTagClicked(View view) {
        final String tagName = mNewTagName.getText().toString();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ApiApiModelTag tag = new ApiApiModelTag();
                tag.setName(tagName);
                try {
                    kreaderservice.followTag("nicholas", tag).execute();
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
        startActivity(new Intent(this, TagsActivity.class));
    }

}

package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/3/14.
 */
public class ButterActivity extends Activity {


    public static final String BUTTER_CONTENT_KEY = "butter_content";
    public static final String TAGS_KEY = "tags";

    @Inject
    LayoutInflater mLayoutInflator;

    private String mButterContent;
    private List<String> mTags;

    public ButterActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Intent intent = getIntent();
        mButterContent = intent.getStringExtra(BUTTER_CONTENT_KEY);
        mTags = intent.getStringArrayListExtra(TAGS_KEY);
        setContentView(R.layout.activity_butter);

        TextView butterContent = (TextView) findViewById(R.id.butter_content);

        butterContent.setText(mButterContent);
        ViewGroup tagContainer = (ViewGroup) findViewById(R.id.tag_container);
        for (String tag : mTags) {
            TextView textView = (TextView) mLayoutInflator.inflate(R.layout.tag_item, null);
            textView.setText(tag);
            tagContainer.addView(textView);
        }
    }
}

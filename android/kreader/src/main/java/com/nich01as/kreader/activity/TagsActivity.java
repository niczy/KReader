package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTagCollection;
import com.google.common.base.Joiner;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;
import com.nich01as.kreader.services.TagService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class TagsActivity extends Activity implements AdapterView.OnItemClickListener {

    @Inject
    Kreaderservice kreaderservice;
    @Inject
    TagService mTagService;

    TextView mTags;
    TextView mNewTagName;
    ListView mTagList;

    ArrayAdapter<String> mTagAdapter;

    public TagsActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tags);
        mTags = (TextView) findViewById(R.id.tags);
        mNewTagName = (TextView) findViewById(R.id.tag);
        mTagList = (ListView) findViewById(R.id.tag_list);
        mTagList.setOnItemClickListener(this);
        updateList();
    }

    public void onFollowTagClicked(View view) {
        final String tagName = mNewTagName.getText().toString();
        mTagService.addTag(tagName);
        updateList();
        mNewTagName.setText("");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        mTagService.remove(mTagAdapter.getItem(pos));
        updateList();
    }

    private void updateList() {
        mTagAdapter = new ArrayAdapter<String>(this, R.layout.tag_item, R.id.tag, mTagService.listTags());
        mTagList.setAdapter(mTagAdapter);
    }
}

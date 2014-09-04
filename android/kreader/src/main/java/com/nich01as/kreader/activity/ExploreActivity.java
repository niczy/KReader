package com.nich01as.kreader.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelUser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;
import com.nich01as.kreader.services.TagService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class ExploreActivity extends ListActivity {

    @Inject
    Kreaderservice kreaderservice;
    @Inject
    TagService mTagService;

    List<String> tags = Lists.newArrayList("Sports", "Startup", "China", "Tesla", "Humor", "History", "Yoga", "Sex", "Pets", "Design");

    public ExploreActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.tag_item, R.id.tag, tags));
    }

    @Override
    public void onListItemClick(ListView list, View view, int pos, long id) {
        view.setBackgroundColor(getResources().getColor(R.color.blue));
        mTagService.addTag(tags.get(pos));
    }



}

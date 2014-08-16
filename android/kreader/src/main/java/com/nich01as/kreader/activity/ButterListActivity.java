package com.nich01as.kreader.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButterCollection;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;
import com.nich01as.kreader.services.SearchService;
import com.nich01as.kreader.services.TagService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/3/14.
 */
public class ButterListActivity extends ListActivity {

    private List<ApiApiModelButter> mButters;

    @Inject
    Kreaderservice kreaderservice;
    @Inject
    SearchService searchService;
    @Inject
    TagService mTagService;

    public ButterListActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    try {
                        List<String> tags = mTagService.listTags();
                        List<JSONObject> list = searchService.search(tags);
                        final ButterListAdapter adapter =
                                new ButterListAdapter(ButterListActivity.this, list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setListAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ButterActivity.class);
        ApiApiModelButter butter = mButters.get(position);
        intent.putExtra(ButterActivity.BUTTER_CONTENT_KEY, butter.getContent());
        ArrayList<String> tags = Lists.newArrayList();
        for (ApiApiModelTag tag : butter.getTags().getItems()) {
            tags.add(tag.getName());
        }
        intent.putStringArrayListExtra(ButterActivity.TAGS_KEY, tags);
        startActivity(intent);
    }


    public static class ButterListAdapter extends BaseAdapter {
        private Context mContext;
        private List<JSONObject> mButters;
        @Inject
        LayoutInflater mLayoutInflator;

        public ButterListAdapter(Context context, List<JSONObject> butters) {
            this.mContext = context;
            this.mButters = butters;
            DaggerInjector.inject(this);
        }

        @Override
        public int getCount() {
            return mButters.size();
        }

        @Override
        public Object getItem(int position) {
            return mButters.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mButters.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View butterView = mLayoutInflator.inflate(R.layout.butter_item, parent, false);
            TextView butterContent = (TextView) butterView.findViewById(R.id.butter_content);
            JSONObject butter = mButters.get(position);

            try {
                String content = butter.getString("content");
                butterContent.setText(Html.fromHtml(content));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            ViewGroup tagContainer = (ViewGroup) butterView.findViewById(R.id.tag_container);
//            for (int i = 0; i < butter.getTags().getItems().size(); i++) {
//                mLayoutInflator.inflate(R.layout.tag_item, tagContainer);
//            }
//            int i = 0;
//
//            for (ApiApiModelTag tag : butter.getTags().getItems()) {
//                TextView textView = (TextView) tagContainer.getChildAt(i++);
//
//                    textView.setText(tag.getName());
//
//            }
            return butterView;
        }
    }
}

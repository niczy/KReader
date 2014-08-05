package com.nich01as.kreader.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
                    ApiApiModelButterCollection butterCollection =
                        kreaderservice.getButtersByUserName("nicholas").execute();
                    mButters = butterCollection.getItems();
                    final ButterListAdapter listAdapter =
                            new ButterListAdapter(ButterListActivity.this, mButters);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setListAdapter(listAdapter);
                        }
                    });

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
        private List<ApiApiModelButter> mButters;
        @Inject
        LayoutInflater mLayoutInflator;

        public ButterListAdapter(Context context, List<ApiApiModelButter> butters) {
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
            ApiApiModelButter butter = mButters.get(position);
            butterContent.setText(butter.getContent().substring(0, Math.min(240, butter.getContent().length())));
            ViewGroup tagContainer = (ViewGroup) butterView.findViewById(R.id.tag_container);
            for (int i = 0; i < butter.getTags().getItems().size(); i++) {
                mLayoutInflator.inflate(R.layout.tag_item, tagContainer);
            }
            int i = 0;

            for (ApiApiModelTag tag : butter.getTags().getItems()) {
                TextView textView = (TextView) tagContainer.getChildAt(i++);

                    textView.setText(tag.getName());

            }
            return butterView;
        }
    }
}

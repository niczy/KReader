package com.nich01as.kreader.activity;

import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButterCollection;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
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

    public static final String KEYWORDS_KEY = "keywords";

    private List<ApiApiModelButter> mButters;

    @Inject
    Kreaderservice kreaderservice;
    @Inject
    SearchService searchService;
    @Inject
    TagService mTagService;
    @Inject
    LayoutInflater mLayoutInflator;

    List<String> mKeywords;

    ButterListAdapter mAdapter;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;


    public ButterListActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_butter_list);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final View drawer = findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.tag_list);
        mDrawerList.setAdapter(new TagListAdapter(this, mTagService.listTags(), mLayoutInflator));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                updateListViewKeywords(Lists.newArrayList(mTagService.listTags().get(pos)));
                mDrawerLayout.closeDrawer(drawer);
            }
        });

        final List<String> keywords = getIntent().getStringArrayListExtra(KEYWORDS_KEY);
        updateListViewKeywords(keywords);

    }

    private void updateListViewKeywords(final List<String> keywords) {
        mKeywords = keywords;
        setTitle(Joiner.on(",").join(keywords));
        new AsyncTask<Void, Void, List<JSONObject>>() {
            @Override
            protected List<JSONObject> doInBackground(Void... params) {
                try {
                    try {
                        return searchService.search(keywords);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<JSONObject> butters) {
                mAdapter = new ButterListAdapter(ButterListActivity.this, butters);
                setListAdapter(mAdapter);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mKeywords.size() == 1 && !mTagService.listTags().contains(mKeywords.get(0))) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mTagService.addTag(mKeywords.get(0));
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ButterActivity.class);
        JSONObject butter = (JSONObject) mAdapter.getItem(position);
        try {
            intent.putExtra(ButterActivity.URL_KEY, butter.getString("unescapedUrl"));
            intent.putExtra(ButterActivity.TITLE_KEY, butter.getString("title"));
            intent.putExtra(ButterActivity.PUBLISHER_KEY, butter.getString("publisher"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v.findViewById(R.id.butter_content), "article");
        startActivity(intent, options.toBundle());
    }

    public static class TagListAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private List<String> mTagList;


        public TagListAdapter(Context context, List<String> tagList, LayoutInflater layoutInflater) {
            mContext = context;
            mLayoutInflater = layoutInflater;
            mTagList = tagList;
        }

        @Override
        public int getCount() {
            return mTagList.size();
        }

        @Override
        public Object getItem(int i) {
            return mTagList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View tagContainer = mLayoutInflater.inflate(R.layout.tag_item, viewGroup, false);
            TextView textView = (TextView) tagContainer.findViewById(R.id.tag);
            textView.setText(mTagList.get(i));
            return tagContainer;
        }
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
            TextView titleContent = (TextView) butterView.findViewById(R.id.title);
            TextView dateContent = (TextView) butterView.findViewById(R.id.date);
            JSONObject butter = mButters.get(position);

            try {
                String content = butter.getString("content");
                butterContent.setText(Html.fromHtml(content));
                titleContent.setText(Html.fromHtml(butter.getString("title")));
                dateContent.setText(Html.fromHtml(butter.getString("date")));
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

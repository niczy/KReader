package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;

import com.google.common.base.Preconditions;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;
import com.nich01as.kreader.services.TagService;

import javax.inject.Inject;


public class MainActivity extends Activity {

    @Inject
    Kreaderservice kreaderservice;
    @Inject
    TagService mTagService;

    public MainActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Preconditions.checkNotNull(kreaderservice);
    }

    public void onNewUserClicked(View view) {
        startActivity(new Intent(this, ExploreActivity.class));
    }

    public void onFollowingTagsClicked(View view) {
        startActivity(new Intent(this, TagsActivity.class));
    }

    public void onExporeClicked(View view) {
        startActivity(new Intent(this, ExploreActivity.class));
    }

    public void onButtersClicked(View view) {
        Intent intent = new Intent(this, ButterListActivity.class);
        intent.putStringArrayListExtra(ButterListActivity.KEYWORDS_KEY, mTagService.listTags());
        startActivity(intent);
    }
}

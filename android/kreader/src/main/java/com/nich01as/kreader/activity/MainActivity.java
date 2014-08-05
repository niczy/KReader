package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;

import com.google.common.base.Preconditions;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;

import javax.inject.Inject;


public class MainActivity extends Activity {

    @Inject
    Kreaderservice kreaderservice;

    public MainActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Preconditions.checkNotNull(kreaderservice);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNewUserClicked(View view) {
        startActivity(new Intent(this, NewUserActivity.class));
    }

    public void onFollowingTagsClicked(View view) {
        startActivity(new Intent(this, TagsActivity.class));
    }

    public void onNewButterClicked(View view) {
        startActivity(new Intent(this, PostButterActivity.class));
    }

    public void onButtersClicked(View view) {
        startActivity(new Intent(this, ButterListActivity.class));
    }
}

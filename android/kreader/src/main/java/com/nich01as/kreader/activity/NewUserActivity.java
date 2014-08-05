package com.nich01as.kreader.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.nich01as_com.kreaderservice.Kreaderservice;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelUser;
import com.google.common.base.Preconditions;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by nicholaszhao on 8/2/14.
 */
public class NewUserActivity extends Activity {

    @Inject
    Kreaderservice kreaderservice;

    public NewUserActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_new_user);
        Preconditions.checkNotNull(kreaderservice);
    }

    public void onOkClicked(View view) {
        String userName = ((TextView) findViewById(R.id.user_name)).getText().toString();
        final ApiApiModelUser user = new ApiApiModelUser();
        user.setName(userName);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    kreaderservice.addUser(user).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    NewUserActivity.this, "Account created", Toast.LENGTH_SHORT).show();
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

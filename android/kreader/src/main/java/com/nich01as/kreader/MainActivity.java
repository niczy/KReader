package com.nich01as.kreader;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appspot.nich01as_com.article.Article;
import com.appspot.nich01as_com.article.model.KreaderArticle;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.apache.GoogleApacheHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.apache.ApacheHttpTransport;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class MainActivity extends Activity {

    Article articleEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleEndpoint = new Article.Builder(
                AndroidHttp.newCompatibleTransport(),
              AndroidJsonFactory.getDefaultInstance(),
             null
        ).setApplicationName("KReader")
            .build();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final KreaderArticle article = articleEndpoint.article().execute();
                    Log.d("main", "get content " + article.getContent());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.hello)).setText(article.getContent());
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
}

package com.nich01as.kreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelButter;
import com.appspot.nich01as_com.kreaderservice.model.ApiApiModelTag;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.nich01as.kreader.DaggerInjector;
import com.nich01as.kreader.R;
import com.nich01as.kreader.data.Butter;
import com.nich01as.kreader.services.ButterService;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by nicholaszhao on 8/3/14.
 */
public class ButterActivity extends Activity {


    public static final String BUTTER_CONTENT_KEY = "butter_content";
    public static final String URL_KEY = "url";
    public static final String TITLE_KEY = "title";
    public static final String PUBLISHER_KEY = "publisher";
    public static final String TAGS_KEY = "tags";

    @Inject
    LayoutInflater mLayoutInflator;
    @Inject
    ButterService mButterService;

    private String mButterContent;
    private String mUrl;
    private String mTitle;
    private String mPublisher;
    private List<String> mTags;

    private ImageView mIcoView;
    private TextView mKeywordView;

    public ButterActivity() {
        DaggerInjector.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(URL_KEY);
        mTitle = intent.getStringExtra(TITLE_KEY);
        mPublisher = intent.getStringExtra(PUBLISHER_KEY);

        setContentView(R.layout.activity_butter);

        final TextView butterContent = (TextView) findViewById(R.id.butter_content);
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(Html.fromHtml(mTitle));
        ((TextView) findViewById(R.id.publisher)).setText(mPublisher);
        mIcoView = (ImageView) findViewById(R.id.ico);

        URI uri = URI.create(mUrl);

        ImageLoader.getInstance().displayImage(uri.getScheme() + "://" + uri.getHost() + "/favicon.ico", mIcoView);

        new AsyncTask<String, Void, Butter>() {
            @Override
            protected Butter doInBackground(String... urls) {
                try {
                    String url = urls[0];
                    return mButterService.loadButter(url);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Butter butter) {
                butterContent.setText(Joiner.on("\n\n").join(butter.getParagraphs()));
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.tag_container);
                for (int i = 0; i < butter.getTags().size() && i < 4; i++) {
                    TextView textView = (TextView) mLayoutInflator.inflate(R.layout.content_tag, viewGroup, false);
                    final String tag = butter.getTags().get(i).getText();
                    textView.setText("#" + tag);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ButterActivity.this, ButterListActivity.class);
                            intent.putStringArrayListExtra(ButterListActivity.KEYWORDS_KEY, Lists.newArrayList(tag));
                            startActivity(intent);
                        }
                    });
                    viewGroup.addView(textView);
                }
            }
        }.execute(mUrl);

    }
}

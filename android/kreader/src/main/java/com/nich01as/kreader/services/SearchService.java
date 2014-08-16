package com.nich01as.kreader.services;


import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Lists;
import com.google.api.client.util.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nicholaszhao on 8/16/14.
 */
@Singleton
public class SearchService {

    private static final HttpClient httpClient = AndroidHttpClient.newInstance("kreader");
    private static final String ENDPOINT = "http://www.nich01as.com/apps/kreader/_/articles?q=%s";

    @Inject
    public SearchService() {}

    public List<JSONObject> search(List<String> queries) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        for (String q : queries) {
            sb.append(URLEncoder.encode(q, "utf-8")).append(",");
        }
        URI uri = URI.create(String.format(ENDPOINT, sb.toString().substring(0, sb.length()-1)));

        HttpGet get = new HttpGet(uri);
        HttpResponse response = httpClient.execute(get);
        String content = EntityUtils.toString(response.getEntity());
        Log.d("Search", get.getURI().toString());
        Log.d("Search", content);
        JSONArray arr = new JSONArray(content);
        List<JSONObject> list = Lists.newArrayList();
        for (int i = 0; i < arr.length(); i++) {
            list.add(arr.getJSONObject(i));
        }
        return list;
    }

    public List<JSONObject> search(String... queries) throws IOException, JSONException {
        return search(Arrays.asList(queries));
    }
}

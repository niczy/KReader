package com.nich01as.kreader.services;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;


/**
 * Created by nicholaszhao on 8/16/14.
 */
public class TagService {

    private static final String NAME = "tags";
    private static final String TAG_KEY = "tags_key";

    SharedPreferences preference;

    @Inject
    public TagService(Application application) {
        preference = application.getSharedPreferences(NAME, 0);
    }

    public List<String> listTags() {
        String tags = preference.getString(TAG_KEY, "");
        if (Strings.isNullOrEmpty(tags)) {
            return com.google.api.client.util.Lists.newArrayList();
        }
        return Arrays.asList(preference.getString(TAG_KEY, "").split(","));
    }

    public void addTag(String tag) {
        List<String> tags = listTags();
        if (!tags.contains(tag)) {
            List<String> newTags = Lists.newArrayList();
            newTags.addAll(tags);
            newTags.add(tag);
            Collections.reverse(newTags);
            preference.edit().putString(TAG_KEY, Joiner.on(",").join(newTags)).commit();
        }
    }

    public void remove(String tag) {
        List<String> tags = listTags();
        if (tags.contains(tag)) {
            List<String> newTags = Lists.newArrayList();
            newTags.addAll(tags);
            newTags.remove(tag);
            preference.edit().putString(TAG_KEY, Joiner.on(",").join(newTags)).commit();
        }
    }
}

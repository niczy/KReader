package com.nich01as.kreader.data;

import com.google.api.client.util.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by nicholaszhao on 8/16/14.
 */
public class Butter {

    public String title;
    private String mContent;
    private List<String> mParagraphs;
    private List<Tag> mTags = Lists.newArrayList();

    public void setContent(String content) {
        mContent = content;
        mParagraphs = Arrays.asList(content.split("\n"));
    }

    public void addTag(Tag tag) {
        if (!mTags.contains(tag)) {
            mTags.add(tag);
        }
    }

    public String getContent() {
        return mContent;
    }

    public List<String> getParagraphs() {
        return mParagraphs;
    }

    public List<Tag> getTags() {
        return mTags;
    }

    public void sortTags() {
        Collections.sort(mTags);
    }
}

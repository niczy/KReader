package com.nich01as.kreader.data;

/**
 * Created by nicholaszhao on 8/16/14.
 */
public class Tag implements Comparable {

    private String text;
    private double relevane;

    public Tag(String text, double relevance) {
        this.text = text;
        this.relevane = relevance;
    }

    public String getText() {
        return text;
    }

    public double getRelevance() {
        return relevane;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Tag) {
            return text.equals(((Tag) other).getText());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        Tag other = (Tag) o;
        if (relevane == other.relevane) {
            return 0;
        }
        return relevane > other.relevane ? 1 : -1;
    }
}

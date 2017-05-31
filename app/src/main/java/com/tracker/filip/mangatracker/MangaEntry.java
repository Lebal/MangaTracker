package com.tracker.filip.mangatracker;

/**
 * Created by Filip on 2017-05-31.
 */

public class MangaEntry {

    private String name;
    private int chapter;
    private int picture;

    public MangaEntry(String name, int chapter, int picture) {
        this.name = name;
        this.chapter = chapter;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}

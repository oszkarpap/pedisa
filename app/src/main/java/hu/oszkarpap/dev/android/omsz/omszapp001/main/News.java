package hu.oszkarpap.dev.android.omsz.omszapp001.main;

import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GL;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This class set the SOP item
 */


public class News implements Comparable<News> {

    private String key;
    private String name;
    private String date;
    private String text;

    public News() {
    }

    public News(String key, String name, String date, String text) {
        this.key = key;
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(News o) {
        if (date.compareTo(o.getDate()) < 0) {
            return 1;
        } else if (date.compareTo(o.getDate()) > 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
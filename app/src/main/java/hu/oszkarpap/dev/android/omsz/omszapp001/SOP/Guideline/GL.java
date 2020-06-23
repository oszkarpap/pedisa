package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

import android.content.Intent;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This class set the GL item
 */


public class GL implements Comparable<GL> {
    private Long id;
    private String key;
    private String name;
    private String desc;
    private String asc;
    private String attr;
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public GL(String name) {
    }

    public GL() {
    }

    public GL(String desc, String key, String name) {
        this.key = key;
        this.name = name;
        this.desc = desc;
    }

    public GL(Long id, String key, String name, String desc, String attr) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.attr = attr;
    }

    public GL(String key, String name, String desc, String asc, String attr, String count) {
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
        this.attr = attr;
        this.count = count;
    }

    public GL(Long id, String key, String name, String desc, String asc, String attr, String count) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
        this.attr = attr;
        this.count = count;
    }

    public GL(Long id, String key, String name, String desc, String asc, String attr) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
        this.attr = attr;
    }

    public GL(String key, String name, String desc, String asc, String attr) {
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
        this.attr = attr;
    }

    public GL(String key, String name, String desc, String asc) {
        this.key = key;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
    }

    public GL(Long id, String name, String desc, String asc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.asc = asc;
    }

    public GL(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public GL(String name, String desc) {
        this.name = name;
        this.desc = desc;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public int compareTo(GL gl) {
        if ( Integer.parseInt(count) > Integer.parseInt(gl.getCount())) {
            return 1;
        }
        else if ( Integer.parseInt(count) < Integer.parseInt(gl.getCount())) {
            return -1;
        }
        else {
            return 0;
        }
    }
}

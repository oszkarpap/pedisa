package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GL;

public class GuideLineListItem implements Comparable<GuideLineListItem>{

    private Long id;
    private String key;
    private String item;
    private String parent;
    private String attributum;
    private String secondKey;
    private int count;

    public String getSecondKey() {
        return secondKey;
    }

    public void setSecondKey(String secondKey) {
        this.secondKey = secondKey;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public GuideLineListItem(){}

    public GuideLineListItem(String key, int count, String item, String parent, String attributum, String secondKey) {
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
        this.key = key;
        this.count = count;
        this.secondKey = secondKey;
    }

    public GuideLineListItem(Long id, String key, int count, String item, String parent, String attributum, String secondKey) {
        this.id = id;
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
        this.key = key;
        this.count = count;
        this.secondKey = secondKey;
    }

    public String getAttributum() {
        return attributum;
    }

    public void setAttributum(String attributum) {
        this.attributum = attributum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }



    @Override
    public int compareTo(GuideLineListItem o) {
        if (count > (int) o.getCount()) {
            return 1;
        }
        else if (count < (int) o.getCount()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}

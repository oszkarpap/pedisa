package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

import hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.GL;

public class GuideLineListItem implements Comparable<GuideLineListItem>{


    private String key;
    private String item;
    private String parent;
    private String attributum;
    private String secondKey;
    private String count;

    public String getSecondKey() {
        return secondKey;
    }

    public void setSecondKey(String secondKey) {
        this.secondKey = secondKey;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public GuideLineListItem(){}


    public GuideLineListItem(String key, String count, String item, String parent, String attributum, String secondKey) {

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
        if (Integer.parseInt(count) > Integer.parseInt( o.getCount())) {
            return 1;
        }
        else if (Integer.parseInt(count) < Integer.parseInt( o.getCount()))  {
            return -1;
        }
        else {
            return 0;
        }
    }
}

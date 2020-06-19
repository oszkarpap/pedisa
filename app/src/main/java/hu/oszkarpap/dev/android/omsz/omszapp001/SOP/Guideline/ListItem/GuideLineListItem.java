package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

public class GuideLineListItem {

    private Long id;
    private String key;
    private String item;
    private String parent;
    private String attributum;
    private int count;

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

    public GuideLineListItem(String key, int count, String item, String parent, String attributum) {
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
        this.key = key;
        this.count = count;
    }

    public GuideLineListItem(Long id, String key, int count, String item, String parent, String attributum) {
        this.id = id;
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
        this.key = key;
        this.count = count;
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
}

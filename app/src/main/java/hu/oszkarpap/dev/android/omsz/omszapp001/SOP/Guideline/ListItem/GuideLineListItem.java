package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline.ListItem;

public class GuideLineListItem {

    private Long id;
    private String item;
    private String parent;
    private String attributum;

    public GuideLineListItem(String item, String parent, String attributum) {
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
    }

    public GuideLineListItem(Long id, String item, String parent, String attributum) {
        this.id = id;
        this.item = item;
        this.parent = parent;
        this.attributum = attributum;
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

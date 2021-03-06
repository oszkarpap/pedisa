package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This class set the SOP item
 */


public class SOP {
    private Long id;
    private String key;
    private String name;
    private String desc;
    private String icon;

    public SOP(String name) {
    }

    public SOP() {
    }

    public SOP(Long id, String name, String desc, String icon) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
    }

    public SOP(String name, String desc, String icon) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;

    }
    public SOP(String name, String desc) {
        this.name = name;
        this.desc = desc;

    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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


}
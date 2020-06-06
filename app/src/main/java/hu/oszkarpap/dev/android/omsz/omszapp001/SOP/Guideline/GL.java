package hu.oszkarpap.dev.android.omsz.omszapp001.SOP.Guideline;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This class set the GL item
 */


public class GL {
    private Long id;
    private String key;
    private String name;
    private String desc;
    private String asc;

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




}
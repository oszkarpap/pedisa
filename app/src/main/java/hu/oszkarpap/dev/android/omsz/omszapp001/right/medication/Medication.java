package hu.oszkarpap.dev.android.omsz.omszapp001.right.medication;



public class Medication {
    private Long id;
    private String key;
    private String name;
    private String agent;
    private String pack;
    private String ind;
    private String cont;
    private String adult;
    private String child;
    private String child01;
    private String child02;
    private String unit;
    private String childDMax;
    private String childDMax02;
    private String childDDesc;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getChildDMax02() {
        return childDMax02;
    }

    public void setChildDMax02(String childDMax02) {
        this.childDMax02 = childDMax02;
    }

    public String getChild01() {
        return child01;
    }

    public void setChild01(String child01) {
        this.child01 = child01;
    }

    public String getChild02() {
        return child02;
    }

    public void setChild02(String child02) {
        this.child02 = child02;
    }

    public String getChildDMax() {
        return childDMax;
    }

    public void setChildDMax(String childDMax) {
        this.childDMax = childDMax;
    }

    public String getChildDDesc() {
        return childDDesc;
    }

    public void setChildDDesc(String childDDesc) {
        this.childDDesc = childDDesc;
    }



    public Medication(String name, String agent, String pack, String ind, String cont, String adult, String child, String child01,
                      String child02,String unit, String childDMax,String childDMax02, String childDDesc) {
        this.name = name;
        this.agent = agent;
        this.pack = pack;
        this.ind = ind;
        this.cont = cont;
        this.adult = adult;
        this.child = child;
        this.child01 = child01;
        this.child02 = child02;
        this.unit = unit;
        this.childDMax = childDMax;
        this.childDMax02 = childDMax02;
        this.childDDesc = childDDesc;
    }

    public Medication() {
    }

    public Medication(Long id, String name, String agent, String pack, String ind, String cont, String adult, String child) {
        this.id = id;
        this.name = name;
        this.agent = agent;
        this.pack = pack;
        this.ind = ind;
        this.cont = cont;
        this.adult = adult;
        this.child = child;
    }

    public Medication(String name, String agent, String pack, String ind, String cont, String adult, String child) {
        this.name = name;
        this.agent = agent;
        this.pack = pack;
        this.ind = ind;
        this.cont = cont;
        this.adult = adult;
        this.child = child;
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

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }



}
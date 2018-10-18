package hu.oszkarpap.dev.android.omsz.omszapp001.guide.medication;

public class Medication {
    private String id;
    private String key;
    private String name;
    private String agent;
    private String pack;
    private String ind;
    private String cont;
    private String adult;
    private String child;

    public Medication(String id, String name, String agent, String pack, String ind, String cont, String adult, String child) {
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

    public Medication() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

package hu.oszkarpap.dev.android.omsz.omszapp001.main;

public class PhoneNumber {
    private String id;
    private String key;
    private String name;
    private String number;

    public PhoneNumber(String id, String key, String name, String number) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.number = number;
    }

    public PhoneNumber(String key, String name, String number) {
        this.key = key;
        this.name = name;
        this.number = number;
    }



    public PhoneNumber() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

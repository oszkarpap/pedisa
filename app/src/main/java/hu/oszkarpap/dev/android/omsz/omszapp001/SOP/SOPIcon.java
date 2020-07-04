package hu.oszkarpap.dev.android.omsz.omszapp001.SOP;

import android.widget.ImageView;

/**
 * @author Oszkar Pap
 * @version 2.0
 * This class set the SOP item
 */


public class SOPIcon {

    private String name;
    private int icon;

    public SOPIcon(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
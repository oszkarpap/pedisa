package hu.oszkarpap.dev.android.omsz.omszapp001.main;


/**
 * @author Oszkar Pap
 * @version 1.0
 * This class prepare the menu model for navigation drawer menu - omsz protocol menu - left menu item
 */

public class MenuModel {

    public String menuName;
    public int modelId;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int modelId) {
        this.menuName = menuName;
        this.modelId = modelId;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
    }
}


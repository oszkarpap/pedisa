package hu.oszkarpap.dev.android.omsz.omszapp001.main;




public class MenuModel {

    public String menuName;
    public int modelId;
    public boolean hasChildren, isGroup;


    /*created by
     * Oszkar Pap
     * */

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int modelId) {
        this.menuName = menuName;
        this.modelId = modelId;
        this.hasChildren = hasChildren;
        this.isGroup = isGroup;
    }
}


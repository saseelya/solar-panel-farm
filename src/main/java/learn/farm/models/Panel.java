package learn.farm.models;

public class Panel {

    private PanelSection section;
    private int row;
    private int column;
    private int yearInstalled;
    private PanelMaterial material;
    private boolean tracking;

    public Panel() {

    }

    public Panel(PanelSection section, int row, int column, int yearInstalled, PanelMaterial material, boolean tracking) {
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.tracking = tracking;
    }

    public PanelSection getSection() {
        return section;
    }

    public void setSection(PanelSection section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public PanelMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PanelMaterial material) {
        this.material = material;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }
}

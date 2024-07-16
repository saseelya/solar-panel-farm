package learn.farm.models;

public enum PanelSection {
    MAIN("Main", 3, 4),
    UPPER_HILL("Upper Hill", 2, 4),
    LOWER_HILL("Lower Hill", 2, 4);

    private final String name;
    private final int row;
    private final int column;

    PanelSection(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

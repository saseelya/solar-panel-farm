package learn.farm.ui;

public enum MenuOption {
    EXIT("Exit"),
    DISPLAY_PANELS("Display Panels"),
    CREATE_PANEL("Create Panel"),
    UPDATE_PANEL("Update Panel"),
    DELETE_PANEL("Delete Panel");


    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private final String title;
}

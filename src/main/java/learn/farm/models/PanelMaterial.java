package learn.farm.models;

public enum PanelMaterial {
    MULTICRYSTALLINE_SILICON("Multicrystalline Silicon"),
    MONOCRYSTALLINE_SILICON("Monocrystalline Silicon"),
    AMORPHOUS_SILICON("Amorphous Silicon"),
    CADMIUM_TELLURIDE("Cadmium Telluride"),
    COPPER_INDIUM_GALLIUM_SELENIDE("Copper Indium Gallium Selenide");

    private final String name;

    PanelMaterial(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

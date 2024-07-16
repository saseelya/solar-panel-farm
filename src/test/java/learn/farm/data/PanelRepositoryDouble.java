package learn.farm.data;

import learn.farm.models.Panel;
import learn.farm.models.PanelMaterial;
import learn.farm.models.PanelSection;

import java.util.ArrayList;
import java.util.List;

public class PanelRepositoryDouble implements PanelRepository {

    private ArrayList<Panel> panels = new ArrayList<>();

    public PanelRepositoryDouble() {
        panels.add(new Panel(PanelSection.MAIN, 2,2,1998,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true));
        panels.add(new Panel(PanelSection.UPPER_HILL, 1,0,2001,PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE,true));
        panels.add(new Panel(PanelSection.UPPER_HILL, 0,0,2014,PanelMaterial.MULTICRYSTALLINE_SILICON,false));
        panels.add(new Panel(PanelSection.LOWER_HILL, 0,0,2019,PanelMaterial.AMORPHOUS_SILICON,false));
    }

    @Override
    public List<Panel> findAll() throws DataAccessException {
        return new ArrayList<>(panels);
    }

    @Override
    public Panel findBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException {
        for (Panel p : panels) {
            if (p.getSection() == section && p.getRow() == row && p.getColumn() == column) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException {
        ArrayList<Panel> result = new ArrayList<>();
        for (Panel p: panels) {
            if (p.getMaterial() == material) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public Panel add(Panel panel) throws DataAccessException {
        panels.add(panel);
        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataAccessException {
        return true;
    }

    @Override
    public boolean deleteBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException {
        return true;
    }
}

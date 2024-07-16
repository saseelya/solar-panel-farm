package learn.farm.data;

import learn.farm.models.Panel;
import learn.farm.models.PanelMaterial;
import learn.farm.models.PanelSection;

import java.util.List;

public interface PanelRepository {
    public List<Panel> findAll() throws DataAccessException;

    public Panel findBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException;

    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException;

    public Panel add(Panel panel) throws DataAccessException;

    public boolean update(Panel panel) throws DataAccessException;

    public boolean deleteBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException;

}

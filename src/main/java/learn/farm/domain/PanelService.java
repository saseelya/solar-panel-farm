package learn.farm.domain;

import learn.farm.data.DataAccessException;
import learn.farm.data.PanelRepository;
import learn.farm.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanelService {

    private final PanelRepository repository;

    public PanelService(PanelRepository repository) {
        this.repository = repository;
    }

    public List<Panel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException {
        return repository.findByMaterial(material);
    }

    public PanelResult add(Panel panel) throws DataAccessException {
        PanelResult result = validateInputs(panel);
        if (!result.isSuccess()) {
            return result;
        }

        if(repository.findBySectionRowColumn(panel.getSection(), panel.getRow(), panel.getColumn()) != null) {
            result.addErrorMessage("Panel already exists in Section " + panel.getSection().getName() +
                    ", Row " + panel.getRow() + ", and Column " + panel.getColumn() + ".");
        }

        Panel p = repository.add(panel);
        result.setPayload(p);
        return result;
    }

    public PanelResult update (Panel panel) throws DataAccessException {
        PanelResult result = validateInputs(panel);
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.update(panel);
        if (!success) {
            result.addErrorMessage("No Panel found at Section " + panel.getSection().getName() +
                    ", Row " + panel.getRow() + ", and Column " + panel.getColumn() + ".");
            return result;
        }

        return result;
    }

    public PanelResult deleteBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException {
        PanelResult result = new PanelResult();
        boolean success = repository.deleteBySectionRowColumn(section, row, column);
        if (!success) {
            result.addErrorMessage("No Panel found at Section " + section.getName() +
                    ", Row " + row + ", and Column " + column + ".");
            return result;
        }

        return result;
    }

    private PanelResult validateInputs(Panel panel) {
        PanelResult result = new PanelResult();
        if (panel == null) {
            result.addErrorMessage("Panel can't be null.");
            return result;
        }

        if (panel.getMaterial() == null) {
            result.addErrorMessage("Panel Material can't be null.");
            return result;
        }

        if (panel.getYearInstalled() > 2020 || panel.getYearInstalled() < 1839) {
            result.addErrorMessage("Date installed must be between 2020 and 1839.");
            return result;
        }

        PanelSection section = panel.getSection();
        if (section == null) {
            result.addErrorMessage("Panel Section can't be null.");
            return result;
        }
        if (panel.getRow() < 0 || panel.getRow() >= section.getRow()) {
            result.addErrorMessage("Panel Row can't be less than 0 or greater than " + section.getRow() + ".");
            return result;
        }
        if (panel.getColumn() < 0 || panel.getColumn() >= section.getColumn()) {
            result.addErrorMessage("Panel Column can't be less than 0 or greater than " + section.getColumn() + ".");
            return result;
        }
        return result;

    }
}

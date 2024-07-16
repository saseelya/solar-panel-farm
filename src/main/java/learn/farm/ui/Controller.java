package learn.farm.ui;

import learn.farm.data.DataAccessException;
import learn.farm.models.*;
import learn.farm.domain.*;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    private final PanelService service;
    private final View view;

    public Controller(PanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            runMenu();
        } catch (DataAccessException ex) {
            view.printHeader("Fatal Error: " + ex);
        }
    }

    public void runMenu() throws DataAccessException {
        MenuOption option;
        do {
            option = view.displayMenuAndSelect(service.findAll());

            switch (option) {
                case EXIT:
                    System.out.println("Goodbye.");
                    break;
                case DISPLAY_PANELS:
                    displayPanels();
                    break;
                case CREATE_PANEL:
                    createPanel();
                    break;
                case UPDATE_PANEL:
                    updatePanel();
                    break;
                case DELETE_PANEL:
                    deletePanel();
                    break;
            }
        }while(option != MenuOption.EXIT);
    }

    public void displayPanels() throws DataAccessException {
        view.printHeader(MenuOption.DISPLAY_PANELS.getTitle() + "(⬛⬛ = Occupied by a Panel, ⬜⬜ = Vacant)");
        view.displayAll(service.findAll());
        Panel panel = view.findPanel(service.findAll());
        if (panel != null) {
            view.displayPanel(panel);
        }
    }

    public void createPanel() throws DataAccessException {
        view.printHeader(MenuOption.CREATE_PANEL.getTitle());
        Panel panel = view.makePanel();
        PanelResult result = service.add(panel);
        view.displayResult(result);
    }

    public void updatePanel() throws DataAccessException {
        view.printHeader(MenuOption.UPDATE_PANEL.getTitle());
        Panel panel = view.update(service.findAll());
        if (panel == null) {
            return;
        }
        PanelResult result = service.update(panel);
        view.displayResult(result);
    }

    public void deletePanel() throws DataAccessException {
        view.printHeader(MenuOption.DELETE_PANEL.getTitle());
        Panel panel = view.findPanel(service.findAll());
        if (panel == null) {
            return;
        }
        PanelResult result = service.deleteBySectionRowColumn(panel.getSection(), panel.getRow(), panel.getColumn());
        view.displayResult(result);
    }
}

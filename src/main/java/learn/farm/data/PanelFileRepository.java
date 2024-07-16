package learn.farm.data;

import learn.farm.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PanelFileRepository implements PanelRepository {

    private final String filePath;
    private final String delimiter = ",";

    public PanelFileRepository(@Value("${dataFilePath:./data/default-file.txt}")String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Panel> findAll() throws DataAccessException {
        ArrayList<Panel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(delimiter, -1);

                if (fields.length == 6) {
                    Panel panel = new Panel(
                            PanelSection.valueOf(fields[0]), //Section
                            Integer.parseInt(fields[1]), //Row
                            Integer.parseInt(fields[2]), //Column
                            Integer.parseInt(fields[3]), //Year
                            PanelMaterial.valueOf(fields[4]), //Material Type
                            Boolean.parseBoolean(fields[5])); //isTracking
                    result.add(panel);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return result;
    }

    @Override
    public Panel findBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException {
        for (Panel panel : findAll()) {
            if (panel.getSection().getName().equals(section.getName()) && panel.getRow() == row && panel.getColumn() == column) {
                return panel;
            }
        }
        return null;
    }

    @Override
    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException {
        ArrayList<Panel> result = new ArrayList<>();
        for (Panel panel : findAll()) {
            if (panel.getMaterial().getName().equals(material.getName())) {
                result.add(panel);
            }
        }
        if (result.size() != 0) {
            return result;
        }
        return null;
    }

    @Override
    public Panel add(Panel panel) throws DataAccessException {
        List<Panel> all = findAll();
        all.add(panel);
        writeAll(all);
        return panel;
    }

    @Override
    public boolean update(Panel panel) throws DataAccessException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getSection() == panel.getSection() && all.get(i).getRow() == panel.getRow() && all.get(i).getColumn() == panel.getColumn()) {
                all.set(i, panel);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteBySectionRowColumn(PanelSection section, int row, int column) throws DataAccessException {
        List<Panel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getSection() == section && all.get(i).getRow() == row && all.get(i).getColumn() == column) {
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Panel> panels) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("section,row,column,yearInstalled,material,tracking");
            for (Panel p : panels) {
                writer.println(serialize(p));
            }
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    private String serialize(Panel panel) {
        return String.format("%s,%s,%s,%s,%s,%s",
                panel.getSection(),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial(),
                panel.isTracking());
    }
}

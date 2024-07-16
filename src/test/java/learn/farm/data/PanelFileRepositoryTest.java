package learn.farm.data;

import learn.farm.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PanelFileRepositoryTest {

    private static final String SEED_PATH = "./data/panels-seed.csv";
    private static final String TEST_PATH = "./data/panels-test.csv";

    private PanelFileRepository repository = new PanelFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindFourPanels() throws DataAccessException {
        List<Panel> actual = repository.findAll();
        assertNotNull(actual);

        assertEquals(5,actual.size());
    }

    @Test
    void shouldFindExistingPanel() throws DataAccessException {
        Panel panel = repository.findBySectionRowColumn(PanelSection.valueOf("MAIN"),1,2);
        assertNotNull(panel);
        assertEquals(2015, panel.getYearInstalled());
    }

    @Test
    void shouldNotFindNonExistantPanel() throws DataAccessException {
        Panel panel = repository.findBySectionRowColumn(PanelSection.valueOf("UPPER_HILL"), 0, 0);
        assertNull(panel);
    }

    @Test
    void shouldFindType() throws DataAccessException {
        List<Panel> amorph = repository.findByMaterial(PanelMaterial.valueOf("AMORPHOUS_SILICON"));
        assertNotNull(amorph);
        assertEquals(2, amorph.size());

        List<Panel> cad = repository.findByMaterial(PanelMaterial.valueOf("CADMIUM_TELLURIDE"));
        assertNotNull(cad);
        assertEquals(1, cad.size());

        List<Panel> mono = repository.findByMaterial(PanelMaterial.valueOf("MONOCRYSTALLINE_SILICON"));
        assertNotNull(mono);
        assertEquals(1, mono.size());

        List<Panel> multi = repository.findByMaterial(PanelMaterial.valueOf("MULTICRYSTALLINE_SILICON"));
        assertNotNull(multi);
        assertEquals(1, multi.size());

    }

    @Test
    void shouldNotFindType() throws DataAccessException {
        List<Panel> copper = repository.findByMaterial(PanelMaterial.valueOf("COPPER_INDIUM_GALLIUM_SELENIDE"));
        assertNull(copper);
    }

    @Test
    void shouldAddPanel() throws DataAccessException {
        Panel panel = new Panel(
                PanelSection.UPPER_HILL,
                1,
                1,
                2012,
                PanelMaterial.MONOCRYSTALLINE_SILICON,
                true);
        Panel actual = repository.add(panel);
        assertNotNull(actual);
        assertEquals(PanelMaterial.MONOCRYSTALLINE_SILICON, actual.getMaterial());
    }

    @Test
    void shouldUpdateExisting() throws DataAccessException {
        Panel panel = new Panel(
                PanelSection.LOWER_HILL,
                1,
                3,
                2012,
                PanelMaterial.MULTICRYSTALLINE_SILICON,
                false);
        boolean success = repository.update(panel);
        assertTrue(success);

        Panel actual = repository.findBySectionRowColumn(PanelSection.LOWER_HILL, 1, 3);
        assertEquals(2012, actual.getYearInstalled());
    }

    @Test
    void shouldNotUpdateNonExistant() throws DataAccessException {
        Panel panel = new Panel(
                PanelSection.LOWER_HILL,
                1,
                1,
                2012,
                PanelMaterial.MULTICRYSTALLINE_SILICON,
                true);
        boolean success = repository.update(panel);
        assertFalse(success);
    }

    @Test
    void shouldDeleteExisting() throws DataAccessException {
        boolean actual = repository.deleteBySectionRowColumn(PanelSection.LOWER_HILL, 0, 0);
        assertTrue(actual);

        Panel panel = repository.findBySectionRowColumn(PanelSection.LOWER_HILL,0,0);
        assertNull(panel);
    }

    @Test
    void shouldNotDeleteNonExistant() throws DataAccessException {
        boolean actual = repository.deleteBySectionRowColumn(PanelSection.MAIN,0,0);
        assertFalse(actual);

        Panel panel = repository.findBySectionRowColumn(PanelSection.MAIN,0,0);
        assertNull(panel);
    }
}

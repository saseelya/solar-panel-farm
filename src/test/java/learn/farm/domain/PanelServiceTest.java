package learn.farm.domain;

import learn.farm.data.DataAccessException;
import learn.farm.data.PanelRepositoryDouble;
import learn.farm.models.Panel;
import learn.farm.models.PanelMaterial;
import learn.farm.models.PanelSection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PanelServiceTest {

    PanelService service = new PanelService(new PanelRepositoryDouble());

    @Test
    void shouldFindByMaterial() throws DataAccessException {
        List<Panel> copper = service.findByMaterial(PanelMaterial.COPPER_INDIUM_GALLIUM_SELENIDE);
        assertNotNull(copper);
        assertEquals(2, copper.size());
    }

    @Test
    void shouldNotFindByMaterialWhenNonExistant() throws DataAccessException {
        List<Panel> mono = service.findByMaterial(PanelMaterial.MONOCRYSTALLINE_SILICON);
        assertEquals(0, mono.size());
    }

    @Test
    void shouldAdd() throws DataAccessException {
        PanelResult result = service.add(new Panel(PanelSection.MAIN, 0, 2, 2018, PanelMaterial.MULTICRYSTALLINE_SILICON, true));
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullPanel() throws DataAccessException {
        PanelResult result = service.add(null);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddPanelOutsideOfSpace() throws DataAccessException {
        PanelResult result = service.add(new Panel(PanelSection.MAIN, 3, 4, 2018, PanelMaterial.MULTICRYSTALLINE_SILICON, true));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddPanelWhenAlreadyOccupied() throws DataAccessException {
        PanelResult result = service.add(new Panel(PanelSection.MAIN, 2, 2, 2018, PanelMaterial.MULTICRYSTALLINE_SILICON, true));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        PanelResult result = service.update(new Panel(PanelSection.MAIN, 2, 2, 2018, PanelMaterial.MULTICRYSTALLINE_SILICON, false));
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldDelete() throws DataAccessException {
        PanelResult result = service.deleteBySectionRowColumn(PanelSection.LOWER_HILL, 0, 0);
        assertTrue(result.isSuccess());
    }
}

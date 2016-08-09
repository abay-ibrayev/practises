package kz.gbk.eprocurement.purchase.input.readers;

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings;
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadStatus;
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr;
import kz.gbk.eprocurement.purchase.model.ProcurementPlan;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ProcurementPlanExcelReaderTest {

    private ProcurementPlanExcelReader reader;

    @Before
    public void setUp() throws Exception {
        reader = new ProcurementPlanExcelReader();
    }

    @Test
    public void shouldReadProcurementPlan() throws Exception {
        ProcurementPlanLoadSettings settings = new ProcurementPlanLoadSettings();

        settings.setSheetNum(0);
        settings.setFirstDataLineNum(17);
        settings.setLastDataLineNum(814);

        Map<ProcurementItemAttr, String> attributeToColumnNameMapping = new HashMap<>();
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_ITEM_NO, "A");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_GSW_UNIQUE_NUMBER, "C");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_GSW_NAME, "D");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_TIME_PERIOD, "K");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_MEASUREMENT_UNIT, "Q");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_ITEM_AMOUNT, "R");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_MARKETING_UNIT_PRICE, "S");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_TOTAL_COST, "T");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_TOTAL_COST_VAT, "U");
        attributeToColumnNameMapping.put(ProcurementItemAttr.ATTR_COMMENTS, "X");

        settings.setColumnMapping(attributeToColumnNameMapping);

        ClassPathResource resource = new ClassPathResource("kz/gbk/eprocurement/purchase/input/readers/sample_plan.xls");

        ProcurementPlanLoadStatus loadStatus = reader.readProcurementPlan(resource.getInputStream(), settings);

        ProcurementPlan plan = loadStatus.getProcurementPlan();
        assertNotNull(plan);

        assertEquals(794, plan.getItems().size());
    }
}
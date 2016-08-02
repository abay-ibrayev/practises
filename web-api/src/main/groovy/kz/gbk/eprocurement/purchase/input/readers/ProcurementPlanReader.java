package kz.gbk.eprocurement.purchase.input.readers;

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings;
import kz.gbk.eprocurement.purchase.model.ProcurementPlan;

import java.io.File;
import java.io.InputStream;

public interface ProcurementPlanReader {
    ProcurementPlan readProcurementPlan(InputStream inputStream, ProcurementPlanLoadSettings settings);
}

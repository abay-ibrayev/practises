package kz.gbk.eprocurement.purchase.input

import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr

class ProcurementPlanLoadSettings {

    int sheetNum = 1

    int firstDataLineNum

    int lastDataLineNum

    Map<ProcurementItemAttr, String> attributeToColumnNameMapping;

    List<String> COLUMN_NAMES = [('A'..'Z').toList()].flatten()

    public ProcurementPlanLoadSettings() {
        (0..COLUMN_NAMES.size()).forEach {
            COLUMN_NAMES << COLUMN_NAMES[0] + COLUMN_NAMES[it]
        }
    }

    int getColumnNumberByName(String columnName) {
        return COLUMN_NAMES.indexOf(columnName)
    }
}

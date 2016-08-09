package kz.gbk.eprocurement.purchase.input

import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import org.apache.commons.lang3.StringUtils

class ProcurementPlanLoadSettings {

    Long purchasingPartyId

    int sheetNum = 0

    int firstDataLineNum

    int lastDataLineNum

    Map<ProcurementItemAttr, String> columnMapping

    static final List<String> COLUMN_NAMES
    static {
        COLUMN_NAMES = [('A'..'Z').toList()].flatten()
        (0..COLUMN_NAMES.size()).forEach {
            COLUMN_NAMES << COLUMN_NAMES[0] + COLUMN_NAMES[it]
        }
    }

    int getColumnNumberByName(String columnName) {
        return COLUMN_NAMES.indexOf(StringUtils.upperCase(columnName))
    }
}

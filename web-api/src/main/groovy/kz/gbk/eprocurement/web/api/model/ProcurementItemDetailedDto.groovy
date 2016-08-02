package kz.gbk.eprocurement.web.api.model

import kz.gbk.eprocurement.purchase.model.ProcurementItem

import java.time.ZoneId

class ProcurementItemDetailedDto extends ProcurementItemDto {
    Integer procurementYear

    String gswUniqueCode

    String measurementUnit

    String totalCostVAT

    static ProcurementItemDetailedDto fromProcurementItem(ProcurementItem procurementItem, Locale locale) {
        ProcurementItemDetailedDto result = new ProcurementItemDetailedDto()
        result.copyPropertiesFrom(procurementItem, locale)
        result.gswUniqueCode = procurementItem.gswUniqueNumber
        result.measurementUnit = procurementItem.measurementUnit
        result.totalCostVAT = procurementItem.totalCostVAT

        if (procurementItem.plan?.startDate instanceof java.sql.Date) {
            result.procurementYear = procurementItem.plan?.startDate?.toLocalDate().getYear()
        }

        return result
    }
}

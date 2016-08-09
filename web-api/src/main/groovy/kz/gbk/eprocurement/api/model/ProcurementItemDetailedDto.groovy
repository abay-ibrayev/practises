package kz.gbk.eprocurement.api.model

import kz.gbk.eprocurement.purchase.model.ProcurementItem

class ProcurementItemDetailedDto extends ProcurementItemDto {
    Integer procurementYear

    String gswUniqueCode

    String measurementUnit

    String totalCostVAT

    String paymentConditionsText;

    String procurementMode;

    String placeKatoCode;

    String placeAddressText;

    String deliveryDestination;

    String deliveryConditions;

    String deliveryTimeText;

    String localContentForecast;

    String comments

    static ProcurementItemDetailedDto fromProcurementItem(ProcurementItem procurementItem, Locale locale) {
        ProcurementItemDetailedDto result = new ProcurementItemDetailedDto()
        result.copyPropertiesFrom(procurementItem, locale)
        result.gswUniqueCode = procurementItem.gswUniqueNumber
        result.measurementUnit = procurementItem.measurementUnit
        result.totalCostVAT = procurementItem.totalCostVAT?.amount.toString()
        result.procurementMode = procurementItem.procurementMode
        result.localContentForecast = procurementItem.localContentForecast
        result.placeKatoCode = procurementItem.placeKatoCode
        result.placeAddressText = procurementItem.placeAddressText
        result.deliveryConditions = procurementItem.deliveryConditions
        result.deliveryDestination = procurementItem.deliveryDestination
        result.deliveryTimeText = procurementItem.deliveryTimeText
        result.paymentConditionsText = procurementItem.paymentConditionsText
        result.comments = procurementItem.comments

        if (procurementItem.plan?.startDate instanceof java.sql.Date) {
            result.procurementYear = procurementItem.plan?.startDate?.toLocalDate().getYear()
        }

        return result
    }
}

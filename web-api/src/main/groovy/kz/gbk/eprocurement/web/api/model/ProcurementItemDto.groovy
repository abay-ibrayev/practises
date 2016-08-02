package kz.gbk.eprocurement.web.api.model

import kz.gbk.eprocurement.purchase.model.ProcurementItem

import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle

class ProcurementItemDto {
    String itemType

    Long id

    String itemNo

    String gswName

    String gswShortDescription;

    String gswAdditionalDescription;

    String period

    String marketingUnitPrice

    Integer itemAmount

    String totalCost

    static ProcurementItemDto fromProcurementItem(ProcurementItem item, Locale locale) {
        ProcurementItemDto result = new ProcurementItemDto()
        result.copyPropertiesFrom(item, locale)

        return result
    }

    protected void copyPropertiesFrom(ProcurementItem item, Locale locale) {
        id = item.id
        itemNo = item.itemNo
        gswName = item.gswName
        gswShortDescription = 'здесь будет краткая характеристика'
        gswAdditionalDescription = 'здесь будет дополнительная характеристика'
        period = extractPeriod(item.startDate, item.finishDate, locale)
        marketingUnitPrice = item.marketingUnitPrice?.toString()
        itemAmount = item.itemAmount
        totalCost = item.totalCost?.toString()
    }

    private static String extractPeriod(java.sql.Date start, java.sql.Date end, Locale locale) {
        if (start == null || end == null) {
            return ''
        }

        LocalDate startingDate = start.toLocalDate()
        LocalDate endingDate = end.toLocalDate()

        Month startingMonth = startingDate.getMonth()
        Month endingMonth = endingDate.getMonth()

        StringBuilder builder = new StringBuilder()
        if (startingMonth.equals(endingMonth)) {
            builder.append(startingMonth.getDisplayName(TextStyle.FULL_STANDALONE, locale))
            builder.append(' ')
            builder.append(startingDate.getYear())
        } else {
            builder.append(startingMonth.getDisplayName(TextStyle.FULL_STANDALONE, locale))
            builder.append(' - ')
            builder.append(endingMonth.getDisplayName(TextStyle.FULL_STANDALONE, locale))
            builder.append(' ')
            builder.append(startingDate.getYear())
        }

        return builder.toString()
    }
}

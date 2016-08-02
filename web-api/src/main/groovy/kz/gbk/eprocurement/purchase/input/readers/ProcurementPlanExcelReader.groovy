package kz.gbk.eprocurement.purchase.input.readers

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.model.ProcurementItem
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import kz.gbk.eprocurement.purchase.model.ProcurementPlan
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.springframework.stereotype.Component

import java.math.RoundingMode
import java.text.NumberFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class ProcurementPlanExcelReader implements ProcurementPlanReader {

    static final Pattern pattern = Pattern.compile('(\\p{L}+)')

    def monthNamesMappings = [:]

    Locale locale = new Locale("ru", "KZ")

    int yearOfPlan = 2016

    ProcurementPlanExcelReader() {
        this.monthNamesMappings = Month.values().collectEntries {
            [(it.getDisplayName(TextStyle.FULL_STANDALONE, locale).toLowerCase()): it]
        }
    }

    @Override
    ProcurementPlan readProcurementPlan(InputStream inputStream, ProcurementPlanLoadSettings settings) {
        Workbook wb = null

        ProcurementPlan procurementPlan = new ProcurementPlan()
        try {
            wb = WorkbookFactory.create(inputStream)

            Sheet sheet = wb.getSheetAt(settings.sheetNum)

            List<ProcurementItem> items = readProcurementItemsFromSheet(sheet, procurementPlan, settings,
                    wb.creationHelper.createFormulaEvaluator())
            procurementPlan.addItems(items)
        } finally {
            if (wb != null) {
                wb.close()
            }
        }

        return procurementPlan
    }

    List<ProcurementItem> readProcurementItemsFromSheet(Sheet sheet, ProcurementPlan procurementPlan,
                                                        ProcurementPlanLoadSettings settings,
                                                        FormulaEvaluator formulaEvaluator) {
        List<ProcurementItem> result = []

        DataFormatter formatter = new DataFormatter(locale)

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale)

        for (int i = settings.firstDataLineNum - 1; i < settings.lastDataLineNum; i++) {
            Row row = sheet.getRow(i)

            ProcurementItem procurementItem = new ProcurementItem()
            settings.attributeToColumnNameMapping.each { ProcurementItemAttr attr, String columnName ->
                int columnNum = settings.getColumnNumberByName(columnName)
                if (columnName >= 0) {
                    Cell cell = row.getCell(columnNum)
                    String cellText = StringUtils.trimToNull(formatter.formatCellValue(cell))
                    switch (attr) {
                        case ProcurementItemAttr.ATTR_ITEM_NO:
                            procurementItem.itemNo = cellText
                            break
                        case ProcurementItemAttr.ATTR_GSW_UNIQUE_NUMBER:
                            procurementItem.gswUniqueNumber = cellText
                            break
                        case ProcurementItemAttr.ATTR_GSW_NAME:
                            procurementItem.gswName = cellText
                            break
                        case ProcurementItemAttr.ATTR_GSW_SHORT_DESCRIPTION:
                            procurementItem.gswShortDescription = cellText
                            break
                        case ProcurementItemAttr.ATTR_GSW_ADDITIONAL_DESCRIPTION:
                            procurementItem.gswAdditionalDescription = cellText
                            break
                        case ProcurementItemAttr.ATTR_TIME_PERIOD:
                            if (cellText) {
                                Matcher matcher = pattern.matcher(cellText)
                                def words = []
                                while (matcher.find()) {
                                    words << matcher.group(1)
                                }
                                extractProcurementPeriod(procurementItem, words)
                            }
                            break
                        case ProcurementItemAttr.ATTR_MEASUREMENT_UNIT:
                            procurementItem.measurementUnit = cellText
                            break
                        case ProcurementItemAttr.ATTR_ITEM_AMOUNT:
                            procurementItem.itemAmount = cellText ? numberFormat.parse(cellText) : 0
                            break
                        case ProcurementItemAttr.ATTR_MARKETING_UNIT_PRICE:
                            procurementItem.marketingUnitPrice = readMoneyVal(cell, numberFormat, formulaEvaluator)
                            break
                        case ProcurementItemAttr.ATTR_TOTAL_COST:
                            procurementItem.totalCost = readMoneyVal(cell, numberFormat, formulaEvaluator)
                            break
                        case ProcurementItemAttr.ATTR_TOTAL_COST_VAT:
                            procurementItem.totalCostVAT = readMoneyVal(cell, numberFormat, formulaEvaluator)
                            break
                        case ProcurementItemAttr.ATTR_COMMENTS:
                            procurementItem.comments = cellText
                            break
                    }
                }
            }

            if (procurementItem.gswUniqueNumber && procurementItem.gswName) {
                if (StringUtils.endsWith(procurementItem.itemNo, 'Т')) {
                    procurementItem.gswType = ProcurementItem.GSWType.GOODS
                } else if (StringUtils.endsWith(procurementItem.itemNo, 'У')) {
                    procurementItem.gswType = ProcurementItem.GSWType.SERVICES
                } else if (StringUtils.endsWith(procurementItem.itemNo, 'Р')) {
                    procurementItem.gswType = ProcurementItem.GSWType.WORKS
                }

                result.add(procurementItem)
            }
        }

        return result
    }

    private Money readMoneyVal(Cell cell, NumberFormat numberFormat, FormulaEvaluator formulaEvaluator) {
        Double amount = null

        switch (cell.cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                amount = cell.numericCellValue
                break
            case Cell.CELL_TYPE_STRING:
                String cellText = cell.stringCellValue
                amount = cellText ? numberFormat.parse(cellText) : 0d
                break
            case Cell.CELL_TYPE_FORMULA:
                int resultType = formulaEvaluator.evaluateFormulaCell(cell)
                if (resultType == Cell.CELL_TYPE_NUMERIC) {
                    amount = cell.numericCellValue
                }
                break
            default:
                break
        }

        return amount ? Money.of(CurrencyUnit.of(locale), amount, RoundingMode.HALF_UP) : null
    }

    void extractProcurementPeriod(ProcurementItem procurementItem, def words) {
        def months = words.findAll {
            monthNamesMappings.containsKey(it.toLowerCase())
        }.collect {
            monthNamesMappings.get(it.toLowerCase())
        }

        months.sort()

        if (!months.isEmpty()) {
            Month sMonth = months[0]
            Month eMonth = months[-1]

            LocalDate startDate = LocalDate.of(yearOfPlan, sMonth, 1)
            LocalDate endDate = LocalDate.of(yearOfPlan, eMonth, 1)
            endDate = endDate.withDayOfMonth(endDate.lengthOfMonth())

            procurementItem.startDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            procurementItem.finishDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        }
    }

    public static void main(String[] args) {
        String period1 = 'январь, Февраль май апрель 2016 г.'
        Pattern pattern = Pattern.compile('(\\p{L}+)')
        Matcher matcher = pattern.matcher(period1)

        while (matcher.find()) {
            println matcher.group(1)
        }
    }
}

package kz.gbk.eprocurement.purchase.input.readers

import kz.gbk.eprocurement.purchase.input.CellLoadStatus
import kz.gbk.eprocurement.purchase.input.LoadErrorKind
import kz.gbk.eprocurement.purchase.input.LoadStatusKind
import kz.gbk.eprocurement.purchase.input.ProcurementItemLoadStatus
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadStatus
import kz.gbk.eprocurement.purchase.model.ProcurementItem
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import kz.gbk.eprocurement.purchase.model.ProcurementPlan
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.apache.poi.ss.usermodel.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    static final Logger logger = LoggerFactory.getLogger(ProcurementPlanExcelReader.class)

    static final Pattern pattern = Pattern.compile('(\\p{L}+)')

    def monthNamesMappings = [:]

    Locale locale = new Locale("ru", "KZ")

    int yearOfPlan = 2016

    DataFormatter formatter

    NumberFormat numberFormat

    FormulaEvaluator formulaEvaluator

    ProcurementPlanExcelReader() {
        this.monthNamesMappings = Month.values().collectEntries {
            [(it.getDisplayName(TextStyle.FULL_STANDALONE, locale).toLowerCase()): it]
        }
    }

    @Override
    ProcurementPlanLoadStatus readProcurementPlan(InputStream inputStream, ProcurementPlanLoadSettings settings) {
        formatter = new DataFormatter(locale)

        numberFormat = NumberFormat.getNumberInstance(locale)

        Workbook wb = null

        ProcurementPlanLoadStatus result = new ProcurementPlanLoadStatus(procurementPlan: new ProcurementPlan())

        try {
            wb = WorkbookFactory.create(inputStream)

            formulaEvaluator = wb.creationHelper.createFormulaEvaluator()

            Sheet sheet = wb.getSheetAt(settings.sheetNum)

            List<ProcurementItem> items = readProcurementItemsFromSheet(sheet, result, settings)

            result.procurementPlan.addItems(items)
        } finally {
            if (wb != null) {
                wb.close()
            }
        }

        return result
    }

    List<ProcurementItem> readProcurementItemsFromSheet(Sheet sheet,
                                                        ProcurementPlanLoadStatus procurementPlanLoadStatus,
                                                        ProcurementPlanLoadSettings settings) {
        List<ProcurementItem> result = []

        for (int i = settings.firstDataLineNum - 1; i < settings.lastDataLineNum; i++) {
            Row row = sheet.getRow(i)
            if (row == null) {
                procurementPlanLoadStatus.itemsLoadStatusList.add(
                        ProcurementItemLoadStatus.failedStatus(i + 1, LoadErrorKind.ROW_WRONG_INDEX))
                continue
            }

            ProcurementItemLoadStatus itemLoadStatus = new ProcurementItemLoadStatus(rowNumber: i + 1,
                    loadStatus: LoadStatusKind.SUCCESS)

            ProcurementItem procurementItem = new ProcurementItem()
            for (ProcurementItemAttr attr : settings.columnMapping.keySet()) {
                String columnName = settings.columnMapping[attr]

                String columnRef = StringUtils.trimToEmpty(columnName)

                int columnNum = NumberUtils.isNumber(columnRef) ? NumberUtils.toInt(columnRef) - 1
                        : settings.getColumnNumberByName(columnRef)
                if (columnNum < 0) {
                    itemLoadStatus.failedStatusCell(attr, LoadErrorKind.COLUMN_WRONG_INDEX)
                    continue
                }
                Cell cell = row.getCell(columnNum)

                if (cell != null) {
                    CellLoadStatus loadStatus = handleSingleCell(itemLoadStatus.rowNumber, cell, attr, procurementItem)
                    if (loadStatus.statusKind == LoadStatusKind.FAILED) {
                        itemLoadStatus.cellLoadStatusMap[attr] = loadStatus
                    }
                } else {
                    itemLoadStatus.failedStatusCell(attr, LoadErrorKind.COLUMN_WRONG_INDEX)
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

                if (!itemLoadStatus.cellLoadStatusMap.isEmpty()) {
                    itemLoadStatus.loadStatus = LoadStatusKind.PARTIAL
                }

                procurementPlanLoadStatus.itemsLoadStatusList.add(itemLoadStatus)
            }
        }

        return result
    }

    private CellLoadStatus handleSingleCell(int rowNum, Cell cell,
                                            ProcurementItemAttr attr, ProcurementItem procurementItem) {
        CellLoadStatus cellLoadStatus = new CellLoadStatus(statusKind: LoadStatusKind.SUCCESS)

        try {
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
                case ProcurementItemAttr.ATTR_PLACE_ADDRESS:
                    procurementItem.placeAddressText = cellText
                    break
                case ProcurementItemAttr.ATTR_PLACE_KATO_CODE:
                    procurementItem.placeKatoCode = cellText
                    break
                case ProcurementItemAttr.ATTR_PAYMENT_CONDITIONS_TEXT:
                    procurementItem.paymentConditionsText = cellText
                    break
                case ProcurementItemAttr.ATTR_DELIVERY_CONDITIONS:
                    procurementItem.deliveryConditions = cellText
                    break
                case ProcurementItemAttr.ATTR_DELIVERY_DESTINATION:
                    procurementItem.deliveryDestination = cellText
                    break
                case ProcurementItemAttr.ATTR_DELIVERY_TIME_TEXT:
                    procurementItem.deliveryTimeText = cellText
                    break
                case ProcurementItemAttr.ATTR_PROCUREMENT_MODE:
                    procurementItem.procurementMode = cellText
                    break
                case ProcurementItemAttr.ATTR_LOCAL_CONTENT_FORECAST:
                    procurementItem.localContentForecast = cellText
                    break
                default:
                    break;
            }
        } catch (Exception ex) {
            cellLoadStatus.statusKind = LoadStatusKind.FAILED
            cellLoadStatus.errorMessage = ex.message

            if (logger.isDebugEnabled()) {
                logger.debug("An exception occurred while parsing the cell $attr on the row $rowNum", ex)
            }
        }

        return cellLoadStatus
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

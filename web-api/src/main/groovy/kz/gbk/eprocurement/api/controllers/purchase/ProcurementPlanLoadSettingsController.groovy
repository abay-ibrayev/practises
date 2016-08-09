package kz.gbk.eprocurement.api.controllers.purchase

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.Sql
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

import javax.sql.DataSource

@CrossOrigin
@RestController
@RequestMapping("/api")
class ProcurementPlanLoadSettingsController {

    static final String TABLE_NAME = 'procurement_plan_load_settings'

    static final List COLUMNS = ['purchasing_party_id', 'sheet_num', 'first_data_line_num', 'last_data_line_num',
                                 'column_mapping_json']

    static
    final String SQL_QUERY_BY_PURCHASING_PARTY_ID = "SELECT ${COLUMNS.join(',')} FROM $TABLE_NAME WHERE purchasing_party_id = :purchasingPartyId"

    static final String SQL_INSERT_SETTINGS = "INSERT INTO $TABLE_NAME (${COLUMNS.join(',')}) VALUES (?,?,?,?,?)"

    static
    final String SQL_UPDATE_SETTINGS = "UPDATE $TABLE_NAME SET sheet_num = ?, first_data_line_num = ?, last_data_line_num = ?, column_mapping_json = ? WHERE purchasing_party_id = ?"

    static final String SQL_COUNT_SETTINGS = "SELECT COUNT(1) AS numRec FROM $TABLE_NAME WHERE purchasing_party_id = ?"

    @Autowired
    DataSource dataSource

    @RequestMapping(value = "/procurement/plan/settings/{purchasingPartyId}", method = RequestMethod.GET)
    def purchaserLoadSettings(@PathVariable Long purchasingPartyId) {
        Sql sqlInstance = new Sql(dataSource)

        List rows = sqlInstance.rows(['purchasingPartyId': purchasingPartyId], SQL_QUERY_BY_PURCHASING_PARTY_ID)
        if (rows.isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        def columnMappingData = fromJsonString(rows[0]['column_mapping_json'])

        Map<ProcurementItemAttr, String> columnMapping = columnMappingData.collectEntries { key, val ->
            [(ProcurementItemAttr.valueOf(key)): val]
        }

        return new ProcurementPlanLoadSettings(
                purchasingPartyId: rows[0]['purchasing_party_id'],
                sheetNum: rows[0]['sheet_num'],
                firstDataLineNum: rows[0]['first_data_line_num'],
                lastDataLineNum: rows[0]['last_data_line_num'],
                columnMapping: columnMapping)
    }

    @RequestMapping(value = "/procurement/plan/settings/{purchasingPartyId}", method = RequestMethod.POST)
    def storeSettingSettings(@PathVariable Long purchasingPartyId, @RequestBody ProcurementPlanLoadSettings settings) {
        assert purchasingPartyId != null

        Sql sqlInstance = new Sql(dataSource)

        def result = sqlInstance.firstRow(SQL_COUNT_SETTINGS, [purchasingPartyId])
        if (result.numRec == 0) {
            String columnMappingAsJson = toJsonString(settings.columnMapping)

            sqlInstance.execute(SQL_INSERT_SETTINGS, [purchasingPartyId,
                                                      settings.sheetNum,
                                                      settings.firstDataLineNum,
                                                      settings.lastDataLineNum,
                                                      columnMappingAsJson])
            return new ResponseEntity<>(HttpStatus.CREATED)
        } else if (result.numRec == 1) {
            String columnMappingAsJson = toJsonString(settings.columnMapping)

            sqlInstance.execute(SQL_UPDATE_SETTINGS, [settings.sheetNum,
                                                      settings.firstDataLineNum,
                                                      settings.lastDataLineNum,
                                                      columnMappingAsJson,
                                                      purchasingPartyId])

            return new ResponseEntity<>(HttpStatus.ACCEPTED)
        } else {
            throw new IllegalStateException("Procurement plan settings are not unique for purchasingPartyId = $purchasingPartyId")
        }

    }

    private String toJsonString(Map<ProcurementItemAttr, String> columnMapping) {
        assert columnMapping != null

        ObjectMapper mapper = new ObjectMapper()
        return mapper.writeValueAsString(columnMapping)
    }

    private Map<ProcurementItemAttr, String> fromJsonString(String columnMappingAsJson) {
        assert columnMappingAsJson != null

        ObjectMapper mapper = new ObjectMapper()
        return mapper.readValue(columnMappingAsJson, Map.class)
    }
}

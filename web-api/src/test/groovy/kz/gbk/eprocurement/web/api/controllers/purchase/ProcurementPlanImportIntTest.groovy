package kz.gbk.eprocurement.web.api.controllers.purchase

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import kz.gbk.eprocurement.web.api.WebAPIMain
import kz.gbk.eprocurement.web.api.commands.ImportProcurementPlanCmd
import kz.gbk.eprocurement.web.api.controllers.files.FileMetaData
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebAPIMain.class)
@WebIntegrationTest
@ActiveProfiles('production')
//@TestExecutionListeners(DbUnitTestExecutionListener.class)
//@DatabaseSetup("/kz/gbk/eprocurement/purchase/repository/purchasingPartyDs.xml")
class ProcurementPlanImportIntTest {

    @Test
    void shouldImportProcurementPlan() {
        FileMetaData fileMetaData = uploadSamplePlan()
        ImportProcurementPlanCmd cmd = createImportCommand(6L, fileMetaData)

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity<ImportProcurementPlanCmd> requestEntity = new HttpEntity<>(cmd, headers)

        RestTemplate template = new RestTemplate()
        template.postForObject('http://localhost:9000/api/6/procurement/plans',
                requestEntity, ImportProcurementPlanCmd.class)
    }

    private FileMetaData uploadSamplePlan() {
        LinkedMultiValueMap model = new LinkedMultiValueMap()
        model.add('uploadFile', new FileSystemResource("C:\\data\\планы закупок\\планы закупок ктж\\вокзалсервис.xls"))

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)

        HttpEntity requestEntity = new HttpEntity(model, headers)

        RestTemplate template = new RestTemplate()
        ResponseEntity<FileMetaData> result = template.exchange(
                'http://localhost:9000/api/files/upload', HttpMethod.POST, requestEntity, FileMetaData.class)

        return result.body
    }

    private ImportProcurementPlanCmd createImportCommand(Long purchasingPartyId, FileMetaData fileMetaData) {
        ProcurementPlanLoadSettings settings = new ProcurementPlanLoadSettings()
        settings.sheetNum = 0
        settings.firstDataLineNum = 17
        settings.lastDataLineNum = 814
        settings.attributeToColumnNameMapping = [
                (ProcurementItemAttr.ATTR_ITEM_NO): 'A',
                (ProcurementItemAttr.ATTR_GSW_UNIQUE_NUMBER): 'C',
                (ProcurementItemAttr.ATTR_GSW_NAME): 'D',
                (ProcurementItemAttr.ATTR_TIME_PERIOD): 'K',
                (ProcurementItemAttr.ATTR_MEASUREMENT_UNIT): 'Q',
                (ProcurementItemAttr.ATTR_ITEM_AMOUNT): 'R',
                (ProcurementItemAttr.ATTR_MARKETING_UNIT_PRICE): 'S',
                (ProcurementItemAttr.ATTR_TOTAL_COST): 'T',
                (ProcurementItemAttr.ATTR_TOTAL_COST_VAT): 'U',
                (ProcurementItemAttr.ATTR_COMMENTS): 'X'
        ]

        ImportProcurementPlanCmd cmd = new ImportProcurementPlanCmd(purchasingPartyId: purchasingPartyId, settings: settings)
        cmd.filePathRefs << fileMetaData.path

        return cmd
    }
}

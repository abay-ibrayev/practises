package kz.gbk.eprocurement.api.controllers.purchase

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import kz.gbk.eprocurement.api.commands.ImportProcurementPlanCmd
import kz.gbk.eprocurement.api.controllers.files.FileMetaData
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.util.LinkedMultiValueMap

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles('integration')
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class])
@DatabaseSetup("/kz/gbk/eprocurement/purchase/repository/purchasingPartyDs.xml")
class ProcurementPlanImportIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldImportProcurementPlan() {
        FileMetaData fileMetaData = uploadSamplePlan()
        Long partyId = 999999999L
        ImportProcurementPlanCmd cmd = createImportCommand(partyId, fileMetaData)

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity<ImportProcurementPlanCmd> requestEntity = new HttpEntity<>(cmd, headers)

        restTemplate.postForObject("/api/$partyId/procurement/plans", requestEntity, ImportProcurementPlanCmd.class)
    }

    private FileMetaData uploadSamplePlan() {
        LinkedMultiValueMap model = new LinkedMultiValueMap()
        model.add('file', new ClassPathResource("kz/gbk/eprocurement/purchase/input/readers/sample_plan.xls"))

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)

        HttpEntity requestEntity = new HttpEntity(model, headers)

        ResponseEntity<FileMetaData> result = restTemplate.postForEntity('/api/files/upload', requestEntity,
                FileMetaData.class)

        return result.body
    }

    private ImportProcurementPlanCmd createImportCommand(Long purchasingPartyId, FileMetaData fileMetaData) {
        ProcurementPlanLoadSettings settings = new ProcurementPlanLoadSettings()
        settings.sheetNum = 0
        settings.firstDataLineNum = 17
        settings.lastDataLineNum = 814
        settings.columnMapping = [
                (ProcurementItemAttr.ATTR_ITEM_NO)             : 'A',
                (ProcurementItemAttr.ATTR_GSW_UNIQUE_NUMBER)   : 'C',
                (ProcurementItemAttr.ATTR_GSW_NAME)            : 'D',
                (ProcurementItemAttr.ATTR_TIME_PERIOD)         : 'K',
                (ProcurementItemAttr.ATTR_MEASUREMENT_UNIT)    : 'Q',
                (ProcurementItemAttr.ATTR_ITEM_AMOUNT)         : 'R',
                (ProcurementItemAttr.ATTR_MARKETING_UNIT_PRICE): 'S',
                (ProcurementItemAttr.ATTR_TOTAL_COST)          : 'T',
                (ProcurementItemAttr.ATTR_TOTAL_COST_VAT)      : 'U',
                (ProcurementItemAttr.ATTR_COMMENTS)            : 'X'
        ]

        ImportProcurementPlanCmd cmd = new ImportProcurementPlanCmd(purchasingPartyId: purchasingPartyId, settings: settings)
        cmd.filePathRefs << fileMetaData.path

        return cmd
    }
}

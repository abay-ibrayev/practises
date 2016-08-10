package kz.gbk.eprocurement.api.controllers.purchase

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles('integration')
@TestExecutionListeners([DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class])
@DatabaseSetup("/kz/gbk/eprocurement/purchase/repository/purchasingPartyDs.xml")
class ProcurementPlanLoadSettingsIntTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndRetrieveSettings() {
        Long partyId = 999999999L

        ProcurementPlanLoadSettings settings = new ProcurementPlanLoadSettings(sheetNum: 2, firstDataLineNum: 1,
                lastDataLineNum: 100)
        settings.columnMapping = [
                (ProcurementItemAttr.ATTR_ITEM_NO) : 'A',
                (ProcurementItemAttr.ATTR_GSW_NAME): 'C'
        ]

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        HttpEntity<ProcurementPlanLoadSettings> requestEntity = new HttpEntity<>(settings, headers)

        String url = "/backend/api/procurement/plan/settings/$partyId"

        ResponseEntity responseEntity1 = restTemplate.postForEntity(url, requestEntity, String.class)

        assertEquals(HttpStatus.CREATED, responseEntity1.statusCode)

        ResponseEntity<ProcurementPlanLoadSettings> responseEntity2 = restTemplate.getForEntity(url, ProcurementPlanLoadSettings.class)

        assertNotNull(responseEntity2.body)
    }
}

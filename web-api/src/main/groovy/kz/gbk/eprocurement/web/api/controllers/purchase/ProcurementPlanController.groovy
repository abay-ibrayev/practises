package kz.gbk.eprocurement.web.api.controllers.purchase

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.input.readers.ProcurementPlanReader
import kz.gbk.eprocurement.purchase.model.ProcurementItem
import kz.gbk.eprocurement.purchase.model.ProcurementPlan
import kz.gbk.eprocurement.purchase.model.PurchasingParty
import kz.gbk.eprocurement.purchase.repository.ProcurementItemRepository
import kz.gbk.eprocurement.purchase.repository.ProcurementPlanRepository
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository
import kz.gbk.eprocurement.web.api.commands.ImportProcurementPlanCmd
import kz.gbk.eprocurement.web.api.model.ProcurementItemDetailedDto
import kz.gbk.eprocurement.web.api.model.ProcurementPlanDto
import kz.gbk.eprocurement.web.api.model.PurchasingPartyDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import java.text.SimpleDateFormat

@CrossOrigin
@Controller
class ProcurementPlanController {

    static final Locale DEFAULT_LOCALE = new Locale("ru", "KZ")

    @Autowired
    Environment env

    @Autowired
    ProcurementPlanReader reader

    @Autowired
    PurchasingPartyRepository purchasingPartyRepository

    @Autowired
    ProcurementPlanRepository procurementPlanRepository

    @Autowired
    ProcurementItemRepository procurementItemRepository

    @RequestMapping(value = '/api/{purchasingPartyId}/procurement/plans', method = RequestMethod.POST)
    ResponseEntity<String> importProcurementPlan(@PathVariable Long purchasingPartyId,
                                                 @RequestBody ImportProcurementPlanCmd command) {
        PurchasingParty purchasingParty = purchasingPartyRepository.findOne(purchasingPartyId)
        if (purchasingParty == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }
        ProcurementPlan plan = readProcurementPlan(command.filePathRefs, command.settings)
        plan.owner = purchasingParty

        procurementPlanRepository.save(plan)

        return new ResponseEntity<>(HttpStatus.CREATED)
    }

    @RequestMapping(value = '/api/{purchasingPartyId}/procurement/plan', method = RequestMethod.GET)
    def @ResponseBody getCurrentProcurementPlan(@PathVariable Long purchasingPartyId) {
        PurchasingParty purchasingParty = purchasingPartyRepository.findOne(purchasingPartyId)
        if (purchasingParty == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy")

        List<ProcurementPlan> planList = procurementPlanRepository.findByOwnerAndStartDateAndFinishDate(purchasingParty,
                format.parse("01-01-2016"), format.parse("31-12-2016"))
        if (planList.isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        ProcurementPlanDto procurementPlanDto = ProcurementPlanDto.fromProcurementPlan(planList[0], DEFAULT_LOCALE)
        procurementPlanDto.purchasingParty = PurchasingPartyDto.fromPurchasingParty(purchasingParty)

        return procurementPlanDto
    }

    @RequestMapping(value = '/api/procurement/plan/items/{itemId}', method = RequestMethod.GET)
    def @ResponseBody getProcurementItem(@PathVariable Long itemId) {
        ProcurementItem item = procurementItemRepository.findOne(itemId)
        if (item == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        return ProcurementItemDetailedDto.fromProcurementItem(item, DEFAULT_LOCALE)
    }


    private ProcurementPlan readProcurementPlan(List<String> filePathRefs, ProcurementPlanLoadSettings settings) {
        String rootDir = env.getProperty('app.paths.uploadedFiles')

        ProcurementPlan plan
        FileInputStream stream = new FileInputStream(new File(rootDir, filePathRefs[0]))
        stream.withCloseable {
            plan = reader.readProcurementPlan(stream, settings)
        }

        return plan
    }

}

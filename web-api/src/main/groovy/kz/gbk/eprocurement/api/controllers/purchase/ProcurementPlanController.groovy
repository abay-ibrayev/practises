package kz.gbk.eprocurement.api.controllers.purchase

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings
import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadStatus
import kz.gbk.eprocurement.purchase.input.readers.ProcurementPlanReader
import kz.gbk.eprocurement.purchase.model.ProcurementItem
import kz.gbk.eprocurement.purchase.model.ProcurementPlan
import kz.gbk.eprocurement.purchase.model.PurchasingPartyNotFoundException
import kz.gbk.eprocurement.purchase.repository.ProcurementItemRepository
import kz.gbk.eprocurement.purchase.services.ProcurementPlanService
import kz.gbk.eprocurement.api.commands.ImportProcurementPlanCmd
import kz.gbk.eprocurement.api.model.ProcurementItemDetailedDto
import kz.gbk.eprocurement.api.model.ProcurementPlanDto
import kz.gbk.eprocurement.api.model.PurchasingPartyDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api")
class ProcurementPlanController {

    static final Locale DEFAULT_LOCALE = new Locale("ru", "KZ")

    @Autowired
    Environment env

    @Autowired
    ProcurementPlanReader reader

    @Autowired
    ProcurementPlanService procurementPlanService

    @Autowired
    ProcurementItemRepository procurementItemRepository

    @RequestMapping(value = '/{purchasingPartyId}/procurement/plans', method = RequestMethod.POST)
    ResponseEntity<ProcurementPlanLoadStatus> importProcurementPlan(@PathVariable Long purchasingPartyId,
                                                                    @RequestBody ImportProcurementPlanCmd command) {
        ProcurementPlanLoadStatus planLoadStatus = readProcurementPlan(command.filePathRefs, command.settings)

        procurementPlanService.importPlan(purchasingPartyId, planLoadStatus.procurementPlan)

        return new ResponseEntity<>(planLoadStatus.lightCopy(), HttpStatus.CREATED)
    }

    @RequestMapping(value = '/{purchasingPartyId}/procurement/plan', method = RequestMethod.GET)
    def getCurrentProcurementPlan(@PathVariable Long purchasingPartyId) {
        ProcurementPlan plan = procurementPlanService.getCurrentPlanOwnedBy(purchasingPartyId)
        if (plan == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        ProcurementPlanDto procurementPlanDto = ProcurementPlanDto.fromProcurementPlan(plan, DEFAULT_LOCALE)
        procurementPlanDto.purchasingParty = PurchasingPartyDto.fromPurchasingParty(plan.owner)

        return procurementPlanDto
    }

    @RequestMapping(value = '/procurement/plan/items/{itemId}', method = RequestMethod.GET)
    def getProcurementItem(@PathVariable Long itemId) {
        ProcurementItem item = procurementItemRepository.findOne(itemId)
        if (item == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        return ProcurementItemDetailedDto.fromProcurementItem(item, DEFAULT_LOCALE)
    }

    private ProcurementPlanLoadStatus readProcurementPlan(List<String> filePathRefs, ProcurementPlanLoadSettings settings) {
        Assert.notEmpty(filePathRefs)

        String rootDir = env.getProperty('app.paths.uploadedFiles')

        ProcurementPlanLoadStatus planLoadStatus
        FileInputStream stream = new FileInputStream(new File(rootDir, filePathRefs[0]))
        stream.withCloseable {
            planLoadStatus = reader.readProcurementPlan(stream, settings)
        }

        return planLoadStatus
    }

    @ExceptionHandler(PurchasingPartyNotFoundException.class)
    @ResponseBody
    ResponseEntity<?> handlePurchasingPartyNotFoundException(PurchasingPartyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    }
}

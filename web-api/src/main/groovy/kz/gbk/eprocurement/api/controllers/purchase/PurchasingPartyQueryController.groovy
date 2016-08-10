package kz.gbk.eprocurement.api.controllers.purchase

import kz.gbk.eprocurement.api.commands.AddChildrenPartiesCmd
import kz.gbk.eprocurement.api.model.PurchasingPartyDto
import kz.gbk.eprocurement.purchase.model.PurchasingParty
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository
import kz.gbk.eprocurement.purchase.services.PurchasingPartyCommandService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api")
class PurchasingPartyQueryController {

    @Autowired
    PurchasingPartyRepository repository

    @Autowired
    PurchasingPartyCommandService service

    @RequestMapping(value = "/purchase/parties", method = RequestMethod.GET)
    def queryParentParties() {
        List<PurchasingParty> purchasingParties = repository.findByParentIsNull()

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }

    @RequestMapping(value = "/purchase/parties/{parentPartyId}", method = RequestMethod.GET)
    def queryChildParties(@PathVariable Long parentPartyId) {
        List<PurchasingParty> purchasingParties = repository.findByParentId(parentPartyId)

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }

    @RequestMapping(value = "/purchase/parties/{parentPartyId}", method = RequestMethod.POST)
    def addChildParties(@PathVariable Long parentPartyId, @RequestBody AddChildrenPartiesCmd cmd) {

        List<PurchasingParty> childParties = cmd.childPartyNames.collect() {
            new PurchasingParty(shortName: it, fullName: it)
        }

        service.addChildrenParties(parentPartyId, childParties)

        return new ResponseEntity<String>(HttpStatus.ACCEPTED)
    }
}

package kz.gbk.eprocurement.api.controllers.purchase

import kz.gbk.eprocurement.purchase.model.PurchasingParty
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository
import kz.gbk.eprocurement.api.model.PurchasingPartyDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api")
class PurchasingPartyQueryController {

    @Autowired
    PurchasingPartyRepository repository

    @RequestMapping(value = "/purchase/parties")
    def queryParentParties() {
        List<PurchasingParty> purchasingParties = repository.findByParentIsNull()

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }

    @RequestMapping(value = "/purchase/parties/{parentPartyId}")
    def queryChildParties(@PathVariable Long parentPartyId) {
        List<PurchasingParty> purchasingParties = repository.findByParentId(parentPartyId)

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }
}

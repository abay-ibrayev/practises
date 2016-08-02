package kz.gbk.eprocurement.web.api.controllers.purchase

import kz.gbk.eprocurement.purchase.model.PurchasingParty
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository
import kz.gbk.eprocurement.web.api.model.PurchasingPartyDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@CrossOrigin
@Controller
class PurchasingPartyQueryController {

    @Autowired
    PurchasingPartyRepository repository

    @RequestMapping(value = "/api/purchase/parties")
    def @ResponseBody queryParentParties() {
        List<PurchasingParty> purchasingParties = repository.findByParentIsNull()

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }

    @RequestMapping(value = "/api/purchase/parties/{parentPartyId}")
    def @ResponseBody queryChildParties(@PathVariable Long parentPartyId) {
        List<PurchasingParty> purchasingParties = repository.findByParentId(parentPartyId)

        purchasingParties.collect() { PurchasingParty party ->
            PurchasingPartyDto.fromPurchasingParty(party)
        }
    }
}

package kz.gbk.eprocurement.api.model

import kz.gbk.eprocurement.purchase.model.PurchasingParty

class PurchasingPartyDto {
    Long id
    String shortName
    String fullName
    String phoneNumber
    String webSiteAddress
    Long parentPartyId
    int childCount

    static PurchasingPartyDto fromPurchasingParty(PurchasingParty purchasingParty) {
        return new PurchasingPartyDto(
                id: purchasingParty.id,
                shortName: purchasingParty.shortName,
                fullName: purchasingParty.fullName,
                phoneNumber: purchasingParty.phoneNumber?.displayName,
                webSiteAddress: purchasingParty.webSiteAddress,
                parentPartyId: purchasingParty.parent?.id,
                childCount: purchasingParty.childCount
        )
    }
}

package kz.gbk.eprocurement.api.model

import kz.gbk.eprocurement.tenders.model.Tender
import kz.gbk.eprocurement.tenders.model.TenderLot

/**
 * Created by abai on 09.08.2016.
 */
class TenderDto {
    Long id

    Long tenderId

    String companyName

    String tenderName

    String tenderMethod

    Date tenderStart

    Date tenderEnd

    String tenderStatus

    def tenderLots = []

    static TenderDto fromTender(Tender tender){
        TenderDto dto = new TenderDto(id: tender.id, tenderId: tender.tenderId, companyName: tender.companyName, tenderName: tender.tenderName, tenderMethod: tender.tenderMethod,tenderStart: tender.tenderStart,tenderEnd: tender.tenderEnd,tenderStatus: tender.tenderStatus)
        dto.tenderLots = tender.tenderLots.collect { TenderLot lot ->
            [
                    id: lot.id,lotNumber: lot.lotNumber,lotName: lot.lotName,lotDesc: lot.lotDesc, lotQuantity: lot.lotQuantity,lotPrice: lot.lotPrice, lotSum: lot.lotSum, lotPlace: lot.lotPlace, lotTimeframe: lot.lotTimeframe, lotCondition: lot.lotCondition
            ]
        }

        return dto
    }

    static List<TenderDto> fromAllTenders(List<Tender> tenders) {
        tenders.collect() { Tender tender ->
            return new TenderDto(id: tender.id, tenderId: tender.tenderId, companyName: tender.companyName, tenderName: tender.tenderName, tenderMethod: tender.tenderMethod,tenderStart: tender.tenderStart,tenderEnd: tender.tenderEnd,tenderStatus: tender.tenderStatus)
        }
    }



}

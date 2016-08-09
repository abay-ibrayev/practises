package kz.gbk.eprocurement.api.model

import kz.gbk.eprocurement.purchase.model.ProcurementPlan

class ProcurementPlanDto {
    Long id

    Date startDate
    Date finishDate

    List<ProcurementItemDto> items = []

    PurchasingPartyDto purchasingParty

    static ProcurementPlanDto fromProcurementPlan(ProcurementPlan plan, Locale locale) {
        ProcurementPlanDto dto = new ProcurementPlanDto(id: plan.id, startDate: plan.startDate,
                finishDate: plan.finishDate)
        dto.items = plan.items.collect() {
            ProcurementItemDto.fromProcurementItem(it, locale)
        }

        return dto
    }
}

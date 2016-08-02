package kz.gbk.eprocurement.web.api.commands

import kz.gbk.eprocurement.purchase.input.ProcurementPlanLoadSettings

class ImportProcurementPlanCmd {
    Long purchasingPartyId

    List<String> filePathRefs = []

    ProcurementPlanLoadSettings settings
}

package kz.gbk.eprocurement.purchase.input

import kz.gbk.eprocurement.purchase.model.ProcurementPlan

class ProcurementPlanLoadStatus {
    ProcurementPlan procurementPlan

    List<ProcurementItemLoadStatus> itemsLoadStatusList = []

    ProcurementPlanLoadStatus lightCopy() {
        return new ProcurementPlanLoadStatus(itemsLoadStatusList: new ArrayList<>(itemsLoadStatusList))
    }
}

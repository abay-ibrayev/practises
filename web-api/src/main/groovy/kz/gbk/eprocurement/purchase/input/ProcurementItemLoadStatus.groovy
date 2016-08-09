package kz.gbk.eprocurement.purchase.input

import kz.gbk.eprocurement.purchase.model.ProcurementItemAttr

class ProcurementItemLoadStatus {

    int rowNumber

    LoadStatusKind loadStatus

    LoadErrorKind loadErrorKind

    Map<ProcurementItemAttr, CellLoadStatus> cellLoadStatusMap = new HashMap<>()

    static ProcurementItemLoadStatus failedStatus(int rowNumber, LoadErrorKind loadErrorKind) {
        return new ProcurementItemLoadStatus(rowNumber: rowNumber, loadErrorKind: loadErrorKind,
                loadStatus: LoadStatusKind.FAILED)
    }

    CellLoadStatus failedStatusCell(ProcurementItemAttr itemAttr, LoadErrorKind errorKind) {
        CellLoadStatus cellLoadStatus = new CellLoadStatus(statusKind: LoadStatusKind.FAILED, errorKind: errorKind)
        cellLoadStatusMap[itemAttr] = cellLoadStatus

        return cellLoadStatus
    }
}

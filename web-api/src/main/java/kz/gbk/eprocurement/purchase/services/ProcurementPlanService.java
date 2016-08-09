package kz.gbk.eprocurement.purchase.services;

import kz.gbk.eprocurement.purchase.model.ProcurementPlan;
import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import kz.gbk.eprocurement.purchase.model.PurchasingPartyNotFoundException;
import kz.gbk.eprocurement.purchase.repository.ProcurementPlanRepository;
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProcurementPlanService {

    @Autowired
    PurchasingPartyRepository purchasingPartyRepository;

    @Autowired
    ProcurementPlanRepository procurementPlanRepository;

    @Transactional(readOnly = true)
    public ProcurementPlan getCurrentPlanOwnedBy(Long purchasingPartyId) {
        PurchasingParty purchasingParty = purchasingPartyRepository.findOne(purchasingPartyId);
        if (purchasingParty == null) {
            throw new PurchasingPartyNotFoundException("Purchasing party with id = " + purchasingParty + " not found");
        }

        ProcurementPlan plan = procurementPlanRepository.findOneByOwnerAndActiveTrue(purchasingParty);
        if (plan != null) {
            plan.assignTo(purchasingParty);
        }

        return plan;
    }

    public void importPlan(Long purchasingPartyId, ProcurementPlan newPlan) {
        PurchasingParty purchasingParty = purchasingPartyRepository.findOne(purchasingPartyId);
        if (purchasingParty == null) {
            throw new PurchasingPartyNotFoundException("Purchasing party with id = " + purchasingParty + " not found");
        }

        ProcurementPlan currentPlan = procurementPlanRepository.findOneByOwnerAndActiveTrue(purchasingParty);
        if (currentPlan != null) {
            currentPlan.deactivate();
        }

        newPlan.activate(purchasingParty);

        procurementPlanRepository.save(newPlan);
    }
}

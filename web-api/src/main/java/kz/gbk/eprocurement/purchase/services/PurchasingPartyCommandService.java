package kz.gbk.eprocurement.purchase.services;

import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import kz.gbk.eprocurement.purchase.model.PurchasingPartyNotFoundException;
import kz.gbk.eprocurement.purchase.repository.PurchasingPartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchasingPartyCommandService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PurchasingPartyRepository repository;

    @Autowired
    public PurchasingPartyCommandService(PurchasingPartyRepository repository) {
        this.repository = repository;
    }

    public void addChildrenParties(Long parentPartyId, List<PurchasingParty> childrenToAdd) {
        PurchasingParty parent = repository.findOne(parentPartyId);
        if (parent == null) {
            throw new PurchasingPartyNotFoundException("Purchasing party with id = " + parentPartyId + " not found");
        }

        List<PurchasingParty> currentChildren = repository.findByParentId(parent.getId());
        for (PurchasingParty added : childrenToAdd) {
            Optional<PurchasingParty> matched = currentChildren.stream()
                    .filter(p -> p.getShortName().equals(added.getShortName()))
                    .findFirst();

            if (matched.isPresent()) {
                logger.info("Purchasing party with name = " + added.getShortName() + " already exists within parent party");
                continue;
            }

            PurchasingParty toSave = new PurchasingParty(parent);
            toSave.setShortName(added.getShortName());
            toSave.setFullName(added.getFullName());
            toSave.setPhoneNumber(added.getPhoneNumber());

            repository.save(toSave);
        }

    }
}

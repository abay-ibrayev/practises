package kz.gbk.eprocurement.purchase.repository;

import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingPartyRepository extends JpaRepository<PurchasingParty, Long> {
    List<PurchasingParty> findByParentIsNull();

    List<PurchasingParty> findByParentId(Long parentPartyId);
}

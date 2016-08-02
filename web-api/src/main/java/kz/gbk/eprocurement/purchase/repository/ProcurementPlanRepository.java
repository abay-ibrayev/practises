package kz.gbk.eprocurement.purchase.repository;

import kz.gbk.eprocurement.purchase.model.ProcurementPlan;
import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProcurementPlanRepository extends JpaRepository<ProcurementPlan, Long> {

    @Query("select p from ProcurementPlan p inner join fetch p.items where p.id = ?1")
    ProcurementPlan findOne(Long id);

    List<ProcurementPlan> findByOwnerAndStartDateAndFinishDate(PurchasingParty party, Date startDate, Date endDate);
}

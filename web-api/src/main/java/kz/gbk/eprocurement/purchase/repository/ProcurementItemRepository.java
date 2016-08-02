package kz.gbk.eprocurement.purchase.repository;

import kz.gbk.eprocurement.purchase.model.ProcurementItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcurementItemRepository extends JpaRepository<ProcurementItem, Long> {
    @Query("select item from ProcurementItem item inner join fetch item.plan where item.id = ?1")
    ProcurementItem findOne(Long id);
}

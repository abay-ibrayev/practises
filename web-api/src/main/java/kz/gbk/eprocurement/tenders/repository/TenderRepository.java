package kz.gbk.eprocurement.tenders.repository;

import kz.gbk.eprocurement.tenders.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by abai on 06.08.2016.
 */
@Repository
public interface TenderRepository extends JpaRepository<Tender,Long> {

    @Query("select t from Tender t left join fetch t.tenderLots where t.id = ?1")
    Tender findOne(Long id);

    @Query("select t from Tender t left join fetch t.tenderLots where t.tenderId = ?1")
    Tender findByTenderId(Long tenderId);

    @Query("select max(t.tenderId) from Tender t")
    Long getLastOneId();

    @Query("select t.id from Tender t")
    List<Long> getAllTenderIds();

}

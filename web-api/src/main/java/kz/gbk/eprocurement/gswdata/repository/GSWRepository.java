package kz.gbk.eprocurement.gswdata.repository;

import kz.gbk.eprocurement.gswdata.model.GSWData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by abai on 11.08.2016.
 */
@Repository
public interface GSWRepository extends JpaRepository<GSWData,Long> {

}

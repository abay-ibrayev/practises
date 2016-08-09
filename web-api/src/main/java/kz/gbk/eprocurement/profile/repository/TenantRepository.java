package kz.gbk.eprocurement.profile.repository;

import kz.gbk.eprocurement.profile.model.Tenant;
import kz.gbk.eprocurement.profile.model.id.TenantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, TenantId> {

    List<Tenant> findByName(String name);

    List<Tenant> findByNameIgnoreCaseContaining(String fragment);
}

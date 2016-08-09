package kz.gbk.eprocurement.profile.repository;

import kz.gbk.eprocurement.profile.model.User;
import kz.gbk.eprocurement.profile.model.id.TenantId;
import kz.gbk.eprocurement.profile.model.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    User findOneByUsername(String username);

    List<User> findByTenantId(TenantId tenantId);
}

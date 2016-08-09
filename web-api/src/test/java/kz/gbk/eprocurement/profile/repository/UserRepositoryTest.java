package kz.gbk.eprocurement.profile.repository;

import kz.gbk.eprocurement.profile.model.User;
import kz.gbk.eprocurement.profile.model.UserRole;
import kz.gbk.eprocurement.profile.model.id.TenantId;
import kz.gbk.eprocurement.profile.model.id.UserId;
import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContextConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        repository.deleteAllInBatch();
    }

    @Test
    public void shouldFindOneByUsername() throws Exception {
        User user = new User(TenantId.newId());
        user.setId(UserId.newId());
        user.setUsername("mfowler");
        user.setRole(UserRole.TENANT_ADMIN);

        repository.saveAndFlush(user);

        User storedUser = repository.findOneByUsername(user.getUsername());

        assertEquals(user.getId(), storedUser.getId());
        assertEquals(user.getUsername(), storedUser.getUsername());
        assertEquals(user.getRole(), storedUser.getRole());
        assertEquals(user.getTenantId(), storedUser.getTenantId());

        assertTrue(user.isActive());
    }

    @Test
    public void shouldFindMultipleByTenantId() throws Exception {
        TenantId tenantId = TenantId.newId();

        User martin = new User(tenantId);
        martin.setId(UserId.newId());
        martin.setUsername("mfowler");
        martin.setRole(UserRole.TENANT_ADMIN);

        User pramod = new User(tenantId);
        pramod.setId(UserId.newId());
        pramod.setUsername("psadalage");
        pramod.setRole(UserRole.TENANT_USER);

        User kent = new User(TenantId.newId());
        kent.setId(UserId.newId());
        kent.setUsername("kbeck");
        kent.setRole(UserRole.TENANT_USER);

        repository.save(Arrays.asList(martin, pramod, kent));

        List<User> users = repository.findByTenantId(tenantId);

        assertThat(users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList()), contains("mfowler", "psadalage"));
    }

}
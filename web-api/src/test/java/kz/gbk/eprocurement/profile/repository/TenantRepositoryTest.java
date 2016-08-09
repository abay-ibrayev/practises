package kz.gbk.eprocurement.profile.repository;

import kz.gbk.eprocurement.profile.model.Tenant;
import kz.gbk.eprocurement.profile.model.id.TenantId;
import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContextConfig.class})
public class TenantRepositoryTest {

    @Autowired
    private TenantRepository repository;

    @Before
    public void setUp() {
        repository.deleteAllInBatch();
    }

    @Test
    public void shouldFindByNameFragment() throws Exception {
        Tenant google = new Tenant(TenantId.newId(), "Google Inc.");
        Tenant thoughtWorks = new Tenant(TenantId.newId(), "ThoughtWorks");
        Tenant microSoft = new Tenant(TenantId.newId(), "Microsoft");
        Tenant dreamworks = new Tenant(TenantId.newId(), "Dreamworks Studio");

        repository.save(Arrays.asList(google, thoughtWorks, microSoft, dreamworks));
        repository.flush();

        assertTrue(repository.findByNameIgnoreCaseContaining("unknown").isEmpty());

        assertEquals(1, repository.findByNameIgnoreCaseContaining(google.getName()).size());
        assertEquals(1, repository.findByNameIgnoreCaseContaining(google.getName().toLowerCase()).size());
        assertEquals(1, repository.findByNameIgnoreCaseContaining("micro").size());

        assertEquals(2, repository.findByNameIgnoreCaseContaining("works").size());
    }
}
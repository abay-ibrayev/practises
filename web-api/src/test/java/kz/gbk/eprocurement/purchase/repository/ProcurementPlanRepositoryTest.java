package kz.gbk.eprocurement.purchase.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import kz.gbk.eprocurement.purchase.model.ProcurementItem;
import kz.gbk.eprocurement.purchase.model.ProcurementPlan;
import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceContextConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("procurementPlanSample.xml")
public class ProcurementPlanRepositoryTest {

    @Autowired
    private ProcurementPlanRepository repository;

    @Test
    public void shouldFindSinglePlanByStartDateAndEndDate() throws Exception {
        PurchasingParty party = new PurchasingParty();
        party.setId(1L);

        LocalDate start = LocalDate.of(2016, Month.JANUARY, 1);
        LocalDate finish = LocalDate.of(2016, Month.DECEMBER, 31);

        List<ProcurementPlan> plans = repository.findByOwnerAndStartDateAndFinishDate(party,
                Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(finish.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertNotNull(plans);
        assertEquals(1, plans.size());
    }

    @Test
    public void shouldFindOneActiveByPurchasingParty() throws Exception {
        PurchasingParty party = new PurchasingParty();
        party.setId(1L);

        ProcurementPlan plan = repository.findOneByOwnerAndActiveTrue(party);

        assertNotNull(plan);
    }

    @Test(expected = LazyInitializationException.class)
    public void shouldFailWhenTryingToFetchItems() throws Exception {
        List<ProcurementPlan> plans = repository.findAll();

        plans.get(0).getItems().toString();
    }

    @Test
    public void shouldFindOnePlanAndFetchItemsEagerly() throws Exception {
        ProcurementPlan plan = repository.findOne(1L);

        assertNotNull(plan);
        assertEquals(2, plan.getItems().size());

        assertThat(plan.getItems().stream()
                .map(ProcurementItem::getGswUniqueNumber)
                .collect(Collectors.toList()), contains("1000000", "1000001"));
    }

}
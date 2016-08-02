package kz.gbk.eprocurement.purchase.repository;

import kz.gbk.eprocurement.common.model.PhoneNumber;
import kz.gbk.eprocurement.purchase.model.PurchasingParty;
import kz.gbk.eprocurement.web.api.context.PersistenceContextConfig;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
@ContextConfiguration(classes = {PersistenceContextConfig.class})
public class PurchasingPartyRepositoryTest {

    @Autowired
    PurchasingPartyRepository repository;

    @Test
    public void shouldFindAllParents() {
        PurchasingParty parent = new PurchasingParty();
        parent.setShortName("JSC GoldStar");
        parent.setFullName("GoldStar JointStock Company");
        parent.setPhoneNumber(new PhoneNumber(PhoneNumber.PhoneType.WORK, "7", "7172", "2345678"));
        parent.setWebSiteAddress("www.gold-star.kz");

        repository.saveAndFlush(parent);

        PurchasingParty child = new PurchasingParty(parent);
        child.setShortName("JSC SilverStar");
        child.setPhoneNumber(new PhoneNumber(PhoneNumber.PhoneType.WORK, "7", "7172", "9876543"));
        child.setWebSiteAddress("www.gold-star.kz");

        repository.saveAndFlush(child);

        List<PurchasingParty> purchasingParties = repository.findByParentIsNull();

        assertEquals(1, purchasingParties.size());
    }
}
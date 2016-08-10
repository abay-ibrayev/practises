package kz.gbk.eprocurement.tenders.repository;

import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import kz.gbk.eprocurement.tenders.model.Tender;
import kz.gbk.eprocurement.tenders.model.TenderLot;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
@ContextConfiguration(classes = {PersistenceContextConfig.class})
public class TenderRepositoryTest {

    @Autowired
    private TenderRepository repository;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findByTenderId() throws Exception {
        Tender tender = createTender(100001L);
        repository.saveAndFlush(tender);

        Tender storedTender = repository.findByTenderId(tender.getTenderId());

        TenderLot lot = new TenderLot();
        lot.setLotName("Lot1");
        storedTender.addTenderLot(lot);

        repository.saveAndFlush(storedTender);

        storedTender = repository.findByTenderId(tender.getTenderId());

        System.out.println(storedTender.getTenderLots());

        assertNotNull(storedTender);
        assertNotNull(storedTender.getId());

        assertEquals(tender.getTenderId(), storedTender.getTenderId());
    }

    @Test
    public void getLastOneId() {
        assertNull(repository.getLastOneId());

        Tender tender = createTender(100001L);
        repository.saveAndFlush(tender);

        assertEquals(tender.getTenderId(), repository.getLastOneId());

        Tender newTender = createTender(100000L);
        repository.saveAndFlush(newTender);

        assertEquals(tender.getTenderId(), repository.getLastOneId());

        Tender stored = repository.saveAndFlush(createTender(1000002L));

        assertEquals(stored.getTenderId(), repository.getLastOneId());
    }
    @Test
    public void getAllTenderIds(){
        List<Long> tenderIds = new ArrayList<>();
        for(int i=0;i<10;i++){
            long a = i+ 100000L;
            Tender tender = createTender(a);
            tenderIds.add(a);
            repository.saveAndFlush(tender);
        }
        List<Long> stored = repository.getAllTenderIds();
        for(int i =0 ; i<stored.size();i++) {
            assertEquals(stored.get(i), tenderIds.get(i));
            System.out.println(stored.get(i));
        }



    }

    private Tender createTender(Long tenderId) {
        Tender tender = new Tender();
        tender.setTenderId(tenderId);
        tender.setTenderName("Tender");
        tender.setTenderStart(new Date());
        tender.setTenderEnd(new Date());

        return tender;
    }

}
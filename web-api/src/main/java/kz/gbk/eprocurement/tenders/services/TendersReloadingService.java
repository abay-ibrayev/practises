package kz.gbk.eprocurement.tenders.services;

import kz.gbk.eprocurement.tenders.model.Tender;
import kz.gbk.eprocurement.tenders.model.TenderLot;
import kz.gbk.eprocurement.tenders.repository.TenderRepository;
import kz.gbk.eprocurement.tenders.parsers.LotParser;
import kz.gbk.eprocurement.tenders.parsers.TenderParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by abai on 06.08.2016.
 */
@Component
public class TendersReloadingService {

    private static Logger logger = LoggerFactory.getLogger(TendersReloadingService.class);

    private static final String TENDER_URL = "http://tender.sk.kz/index.php/ru/negs/";
    private static final String TENDER_LOT_URL = "http://tender.sk.kz/index.php/ru/negs/show/";


    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private LotParser lotParser;

    @Autowired
    private TenderParser tenderParser;

    public void reload(int startPage, int endPage) throws IOException, ParseException {
        long lastId = tenderRepository.getLastOneId();
        tenderRepository.deleteAll();
        Tender lastOne= null;
        for (int i = startPage * 10; i <= endPage * 10; i += 10) {
            List<Tender> myList = tenderParser.parseTenders(TENDER_URL + i, lastOne, lastId);
            lastOne = myList.get(myList.size() - 1);
            for(Tender tender : myList){
                logger.info("SAVE TO DATABASE TENDER " +tender.getTenderId()+ " " + tender.getTenderName());
                tenderRepository.saveAndFlush(tender);
            }
        }
        for (long link : tenderRepository.getAllTenderIds()) {
            try {
                Tender tender = tenderRepository.findByTenderId(link);

                List<TenderLot> lots = lotParser.parseLots(TENDER_LOT_URL, link);

                lots.forEach(tenderLot -> { tender.addTenderLot(tenderLot);});

                tenderRepository.saveAndFlush(tender);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}

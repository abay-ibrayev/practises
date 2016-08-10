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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by abai on 06.08.2016.
 */
@Component
@Transactional
public class TendersReloadingService {

    private static final String TENDER_URL = "http://tender.sk.kz/index.php/ru/negs/";
    private static final String TENDER_LOT_URL = "http://tender.sk.kz/index.php/ru/negs/show/";
    private static Logger logger = LoggerFactory.getLogger(TendersReloadingService.class);
    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private LotParser lotParser;

    @Autowired
    private TenderParser tenderParser;

    public void reload(int startPage, int endPage) throws IOException, ParseException {
        tenderRepository.deleteAll();

        for (int pageNum = startPage * 10; pageNum <= endPage * 10; pageNum += 10) {
            List<Tender> myList = tenderParser.parseTenders(TENDER_URL, pageNum);

            for (Tender tender : myList) {
                logger.info("SAVE TO DATABASE TENDER " + tender.getTenderId() + " " + tender.getTenderName());
                System.out.println(tender.getTenderId());
                tenderRepository.saveAndFlush(tender);
            }
        }

        for (Long id : tenderRepository.getAllTenderIds()) {
            try {
                Tender tender = tenderRepository.findOne(id);

                List<TenderLot> lots = lotParser.parseLots(TENDER_LOT_URL, tender.getTenderId());

                lots.forEach(tenderLot -> tender.addTenderLot(tenderLot));

                tenderRepository.saveAndFlush(tender);


            } catch (Exception e) {
                logger.error("An error occurred while parsing tender lots", e);
            }
        }
    }
}

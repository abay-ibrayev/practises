package kz.gbk.eprocurement.tenders.services;

import kz.gbk.eprocurement.tenders.parsers.SKSiteLotParser;
import kz.gbk.eprocurement.tenders.parsers.SKSiteTenderParser;
import kz.gbk.eprocurement.tenders.repository.TenderRepository;
import kz.gbk.eprocurement.tenders.model.Tender;
import kz.gbk.eprocurement.tenders.model.TenderLot;
import kz.gbk.eprocurement.tenders.parsers.LotParser;
import kz.gbk.eprocurement.tenders.parsers.TenderParser;
import org.apache.poi.sl.draw.geom.IfElseExpression;
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
public class UpdateTendersService {

    private static Logger logger = LoggerFactory.getLogger(UpdateTendersService.class);

    private static final String TENDER_URL = "http://tender.sk.kz/index.php/ru/negs/";
    private static final String TENDER_LOT_URL = "http://tender.sk.kz/index.php/ru/negs/show/";

    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private SKSiteLotParser lotParser;

    @Autowired
    private SKSiteTenderParser tenderParser;

    public void update() throws IOException, ParseException {
        Long lastId = tenderRepository.getLastOneId();
        Tender lastOne = null;
        int i = -10;
        while (true) {
            List<Tender> myList = tenderParser.parseTenders(TENDER_URL ,(i+10));
            if (lastOne != null && lastOne.getTenderId().equals(myList.get(0).getTenderId())) {
                myList.remove(0);
            }
            lastOne = myList.get(myList.size() - 1);
            Boolean IWantToFindEnd = false;
            for(int j=0; j<myList.size();j++){
                if (lastId.equals(myList.get(j).getTenderId()) ) {//check lastId from database to current and stop
                    for(Tender tender : myList.subList(0,j)){
                        logger.info("SAVE TO DATABASE TENDER " + tender.getTenderId() + " " + tender.getTenderName());
                        System.out.println(tender.getTenderId());
                        tenderRepository.saveAndFlush(tender);
                        IWantToFindEnd = true;
                    }

                }
            }
            if(!IWantToFindEnd) {
                for (Tender tender : myList) {
                    logger.info("SAVE TO DATABASE TENDER " + tender.getTenderId() + " " + tender.getTenderName());
                    System.out.println(tender.getTenderId());
                    tenderRepository.saveAndFlush(tender);
                }
            }
            for (Long link : tenderRepository.getAllTenderIds()) {
                try {
                    Tender tender = tenderRepository.findByTenderId(link);

                    List<TenderLot> lots = lotParser.parseLots(TENDER_LOT_URL, link);

                    logger.info(lots.size()+ "aJHShlihsflshldiuhsaldhsaldgasldgastydffuugsaugKSHDSKDFSDFSDF");


                    lots.forEach(tender::addTenderLot);

                    tenderRepository.saveAndFlush(tender);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(IWantToFindEnd){
                return;
            }

        }

    }
}

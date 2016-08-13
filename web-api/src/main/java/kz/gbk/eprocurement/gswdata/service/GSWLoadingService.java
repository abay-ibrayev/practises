package kz.gbk.eprocurement.gswdata.service;

import kz.gbk.eprocurement.gswdata.model.GSWData;
import kz.gbk.eprocurement.gswdata.parsers.EnstruSiteParser;
import kz.gbk.eprocurement.gswdata.repository.GSWRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Created by abai on 11.08.2016.
 */
@Component
@Transactional
public class GSWLoadingService {

    private static final String GOODS_URL = "http://www.enstru.kz/?&t=&l=rus&s=tree&st=goods&r=&p=100&n=";
    private static final String WORK_URL = "http://www.enstru.kz/?&t=&l=rus&s=tree&st=work&r=&p=100&n=";
    private static final String SERVICE_URL = "http://www.enstru.kz/?&t=&l=rus&s=tree&st=service&r=&p=100&n=";

    @Autowired
    private GSWRepository gswRepository;

    @Autowired
    private EnstruSiteParser gswParser = new EnstruSiteParser();

    private static Logger logger = LoggerFactory.getLogger(GSWLoadingService.class);


    public void load(int startPage, int endPage) throws IOException, InterruptedException {
        gswRepository.deleteAll();
        for(int pageNum=startPage; pageNum<endPage;pageNum++){
            List<GSWData> gswList = gswParser.parseFullGSW(GOODS_URL+pageNum+"&k");
            for(GSWData oneItem : gswList){
                logger.info("SAVE TO DATABASE GOODS GSW " + oneItem.getName() + " " + oneItem.getId());
                System.out.println(oneItem.getUniqueCode());
                gswRepository.saveAndFlush(oneItem);
            }

        }
        getPartGSW(startPage, endPage,WORK_URL);
        getPartGSW(startPage, endPage,SERVICE_URL);


    }
    private void getPartGSW(int startPage, int endPage, String url) throws IOException, InterruptedException {
        for(int pageNum=startPage; pageNum<endPage;pageNum++){
            List<GSWData> gswList =  gswParser.parsePartGSW(url+pageNum+"&k");
            for(GSWData oneItem : gswList){
                logger.info("SAVE TO DATABASE WORK OR SERVICE GSW " + oneItem.getName() + " " + oneItem.getId());
                System.out.println(oneItem.getUniqueCode());
                gswRepository.saveAndFlush(oneItem);
            }

        }
    }


}

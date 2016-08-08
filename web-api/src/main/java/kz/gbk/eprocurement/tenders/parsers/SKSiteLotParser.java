package kz.gbk.eprocurement.tenders.parsers;

import kz.gbk.eprocurement.tenders.model.TenderLot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abai on 21.07.2016.
 */
@Component
public class SKSiteLotParser implements LotParser {

    private static Logger logger = LoggerFactory.getLogger(SKSiteLotParser.class);


    public List<TenderLot> parseLots(String url, long link) throws Exception{
        Document doc = Jsoup.connect(url+link).timeout(10*1000).get();
        List<TenderLot> manyLots = new ArrayList<>();
        for (Element table : doc.select("table[class=showtab]")) {
            if(table==doc.select("table[class=showtab]").first()){
                continue;
            }
            for (Element row : table.select("tr[bgcolor=#fafafa")) {
                if(row==table.select("tr[bgcolor=#fafafa").first()){
                    continue;
                }
                TenderLot oneLot = new TenderLot();
                Elements tds = row.select("td");
                oneLot.setLotNumber(Integer.parseInt(tds.get(0).text()));
                oneLot.setLotName(tds.get(1).text());
                oneLot.setLotDesc(tds.get(2).text());
                oneLot.setLotQuantity(new BigDecimal(tds.get(3).text().replaceAll(",","")));
                oneLot.setLotPrice(new BigDecimal(tds.get(4).text().replaceAll(",","").replaceAll(" ","")));
                oneLot.setLotSum(new BigDecimal(tds.get(4).text().replaceAll(",","").replaceAll(" ","")));
                oneLot.setLotPlace(tds.get(6).text());
                oneLot.setLotTimeframe(tds.get(7).text());
                oneLot.setLotCondition(tds.get(8).text());
                manyLots.add(oneLot);
                logger.info("SAVE TO tender_lots: "+ tds.get(0).text()+ " " + link+ " "+ tds.get(1).text());
            }
            logger.info("REPEATING.....");
        }
        return manyLots;
    }
}

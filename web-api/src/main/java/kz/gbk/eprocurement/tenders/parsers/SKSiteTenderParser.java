package kz.gbk.eprocurement.tenders.parsers; /**
 * Created by abai on 19.07.2016.
 */

import kz.gbk.eprocurement.tenders.model.Tender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class SKSiteTenderParser implements TenderParser {

    private static Logger logger = LoggerFactory.getLogger(SKSiteLotParser.class);

    public List<Tender> parseTenders(String url, Tender lastOne, Long lastID) throws IOException, ParseException {
        List<Tender> tendersInfo = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
        for (Element table : doc.select("table[width=100%]")) {
            for (Element row : table.select("tr[bgcolor=#fafafa")) {
                Tender oneTender = new Tender();
                Elements tds = row.select("td");
                if (lastID == null || lastID != Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7])) {
                    if (lastOne != null && Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7]) == lastOne.getTenderId()) {
                        logger.info("REPEATING.....");
                    } else {
                        oneTender.setTenderId(Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7]));
                        oneTender.setCompanyName(tds.get(1).text());
                        oneTender.setTenderName(tds.get(2).text());
                        oneTender.setTenderMethod(tds.get(3).text());
                        oneTender.setTenderStart(df.parse(tds.get(4).text()));
                        oneTender.setTenderEnd(df.parse(tds.get(5).text()));
                        oneTender.setTenderStatus(tds.get(6).text());
                        tendersInfo.add(oneTender);
                        logger.info("SAVE TO tenders: " + tds.get(0).text() + " " + tds.get(1).text() + " " + tds.get(2).text());
                    }
                }
//                else{
//                    tendersInfo.add(null);
//                    return tendersInfo;
//                }
            }
            // break;
        }
        return tendersInfo;
    }

    @Override
    public List<Tender> parseTenders(String baseUrl, int pageNum) throws IOException, ParseException {
        List<Tender> tendersList = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Document doc = Jsoup.connect(baseUrl + pageNum).timeout(10 * 1000).get();
        for (Element table : doc.select("table[width=100%]")) {
            for (Element row : table.select("tr[bgcolor=#fafafa")) {
                Elements tds = row.select("td");

                Tender tender = new Tender();
                tender.setTenderId(Long.parseLong(row.select("a[href]").first().attr("href").split("/")[7]));
                tender.setCompanyName(tds.get(1).text());
                tender.setTenderName(tds.get(2).text());
                tender.setTenderMethod(tds.get(3).text());
                tender.setTenderStart(dateFormat.parse(tds.get(4).text()));
                tender.setTenderEnd(dateFormat.parse(tds.get(5).text()));
                tender.setTenderStatus(tds.get(6).text());

                tendersList.add(tender);
            }
        }

        return tendersList;
    }
}

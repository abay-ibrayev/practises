package kz.gbk.eprocurement.gswdata.parsers;

import kz.gbk.eprocurement.gswdata.model.GSWData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abai on 11.08.2016.
 */
@Component
public class EnstruSiteParser {
    public List<GSWData> parseFullGSW(String url) throws IOException, InterruptedException {
        List<GSWData> goodsList = new ArrayList<>();
        Thread.sleep(1000);
        Document doc = Jsoup.connect(url).timeout(10*1000).get();
        for (Element table : doc.select("table[class=rows]")) {
            for (Element row : table.select("tr[class=row-normal]")) {
                Elements tds = row.select("td");
                GSWData oneItem = new GSWData();
                oneItem.setId(Long.parseLong(tds.get(0).text()));
                System.out.println(oneItem.getId()+ " "+ tds.get(1).text()+ " "+ tds.get(2).text());
                oneItem.setUniqueCode(tds.get(1).text());
                oneItem.setName(tds.get(2).text());
                oneItem.setDescription(tds.get(3).text());
                oneItem.setMeasurementUnits(tds.get(4).text());
                oneItem.setMKEIcode(tds.get(5).text());
                oneItem.setOldCode(tds.get(6).text());
                Boolean translation = false;
                if(tds.get(7).text().equals("есть")){translation=true;}
                oneItem.setTranslation(translation);
                goodsList.add(oneItem);

            }
        }
        return goodsList;
    }
    public List<GSWData> parsePartGSW(String url) throws IOException, InterruptedException {
        List<GSWData> gswList = new ArrayList<>();
        Thread.sleep(1000);
        Document doc = Jsoup.connect(url+3+"&k").timeout(10*1000).get();
        for (Element table : doc.select("table[class=rows]")) {
            for (Element row : table.select("tr[class=row-normal]")) {
                Elements tds = row.select("td");
                GSWData oneItem = new GSWData();
                oneItem.setId(Long.parseLong(tds.get(0).text()));
                System.out.println(oneItem.getId()+ " "+ tds.get(1).text()+ " "+ tds.get(2).text());
                oneItem.setUniqueCode(tds.get(1).text());
                oneItem.setName(tds.get(2).text());
                oneItem.setDescription(tds.get(3).text());
                oneItem.setOldCode(tds.get(4).text());
                Boolean translation = false;
                if(tds.get(5).text().equals("есть")){translation=true;}
                oneItem.setTranslation(translation);
                gswList.add(oneItem);

            }
        }
        return gswList;
    }

}

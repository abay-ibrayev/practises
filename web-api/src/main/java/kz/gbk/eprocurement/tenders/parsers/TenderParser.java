package kz.gbk.eprocurement.tenders.parsers;


import kz.gbk.eprocurement.tenders.model.Tender;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by abai on 06.08.2016.
 */
public interface TenderParser {
    List<Tender> parseTenders(String url, Tender lastOne, Long lastID) throws IOException, ParseException;
}

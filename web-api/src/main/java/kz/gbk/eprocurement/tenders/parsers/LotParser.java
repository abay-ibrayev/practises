package kz.gbk.eprocurement.tenders.parsers;

import kz.gbk.eprocurement.tenders.model.TenderLot;

import java.util.List;

/**
 * Created by abai on 06.08.2016.
 */
public interface LotParser {
    List<TenderLot> parseLots(String url, Long link) throws Exception;
}

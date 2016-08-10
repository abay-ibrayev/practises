package kz.gbk.eprocurement.api.controllers.tenders

import kz.gbk.eprocurement.api.model.TenderDto
import kz.gbk.eprocurement.tenders.model.Tender
import kz.gbk.eprocurement.tenders.repository.TenderRepository
import kz.gbk.eprocurement.tenders.services.TendersReloadingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by abai on 08.08.2016.
 */
@RestController
class TenderController {
    @Autowired
    TenderRepository tenderRepository

    @Autowired
    TendersReloadingService tendersReloadingService

    @RequestMapping(value = '/api/tender/{tenderId}', method = RequestMethod.GET)
    TenderDto getCurrentTender(@PathVariable Long tenderId) {
        Tender tender = tenderRepository.findByTenderId(tenderId);
        if (tender == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        return TenderDto.fromTender(tender)
    }

    @RequestMapping(value = '/api/tenders', method = RequestMethod.GET)
    List<TenderDto> getAllTenders(@RequestParam int pageNum, @RequestParam int pageSize) {
        List<Tender> thisTenders = tenderRepository.findAll(new PageRequest(pageNum, pageSize));
        if (thisTenders == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND)
        }

        return TenderDto.fromAllTenders(thisTenders)
    }

    @RequestMapping(value = '/api/tenders', method = RequestMethod.POST)
    def reloadTenders(@RequestParam int startPage, @RequestParam int endPage) {
        tendersReloadingService.reload(startPage, endPage)
        return new ResponseEntity<String>(HttpStatus.CREATED)
    }
}

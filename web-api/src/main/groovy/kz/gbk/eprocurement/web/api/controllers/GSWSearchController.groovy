package kz.gbk.eprocurement.web.api.controllers

import kz.gbk.eprocurement.web.api.model.GSWData
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class GSWSearchController {
    @RequestMapping(value = "/api/gsw/query")
    def @ResponseBody hello() {
        return new GSWData(uniqueCode: '12030', shortName: 'Product 1')
    }
}

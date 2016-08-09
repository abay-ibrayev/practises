package kz.gbk.eprocurement.api.controllers

import kz.gbk.eprocurement.api.model.GSWData
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class GSWSearchController {
    @RequestMapping(value = "/gsw/query")
    def @ResponseBody hello() {
        return new GSWData(uniqueCode: '12030', shortName: 'Product 1')
    }
}

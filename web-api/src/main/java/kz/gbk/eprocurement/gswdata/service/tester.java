package kz.gbk.eprocurement.gswdata.service;

import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import kz.gbk.eprocurement.tenders.services.UpdateTendersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by abai on 12.08.2016.
 */
public class tester {


    @ComponentScan(basePackages = "kz.gbk.eprocurement.gswdata")
    @Import(PersistenceContextConfig.class)
    private static final class BeanConfig {
    }

    public static void main(String args[]) throws IOException, InterruptedException, ParseException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);

        GSWLoadingService gsw = ctx.getBean(GSWLoadingService.class);
        //UpdateTendersService tendersService = ctx.getBean(UpdateTendersService.class);
        gsw.load(2,12);
        //tendersService.update();
    }
}

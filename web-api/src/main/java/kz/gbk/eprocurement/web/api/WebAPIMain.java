package kz.gbk.eprocurement.web.api;

import kz.gbk.eprocurement.web.api.context.PersistenceContextConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {
        "kz.gbk.eprocurement.purchase",
        "kz.gbk.eprocurement.web.api.controllers"
})
@Import(PersistenceContextConfig.class)
public class WebAPIMain {
    public static void main(String[] args) {
        SpringApplication.run(WebAPIMain.class, args);
    }
}

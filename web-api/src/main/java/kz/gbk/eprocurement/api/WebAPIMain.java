package kz.gbk.eprocurement.api;

import kz.gbk.eprocurement.api.context.OAuth2ServerConfig;
import kz.gbk.eprocurement.api.context.PersistenceContextConfig;
import kz.gbk.eprocurement.api.context.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;

@SpringBootApplication
@ComponentScan(basePackages = {
        "kz.gbk.eprocurement.purchase",
        "kz.gbk.eprocurement.api.controllers"
})
@Import({PersistenceContextConfig.class, WebSecurityConfig.class, OAuth2ServerConfig.class})
//@Import({PersistenceContextConfig.class})
public class WebAPIMain extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(WebAPIMain.class, args);
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}

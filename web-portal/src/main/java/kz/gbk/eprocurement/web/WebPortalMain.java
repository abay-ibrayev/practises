package kz.gbk.eprocurement.web;

import kz.gbk.eprocurement.web.config.WebMvcConfig;
import kz.gbk.eprocurement.web.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebMvcConfig.class, WebSecurityConfig.class})
public class WebPortalMain {

	public static void main(String[] args) {
		SpringApplication.run(WebPortalMain.class, args);
	}
}

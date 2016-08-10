package kz.gbk.eprocurement.web.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
//@EnableOAuth2Sso
@EnableZuulProxy
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/index.html", "/admin", "/admin.html", "/", "/webjars/**", "/client/**", "/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .disable();
    }

//    @Bean
//    public AuthenticationSuccessHandler successHandler() {
//        return new CustomAuthSuccessHandler();
//    }
}

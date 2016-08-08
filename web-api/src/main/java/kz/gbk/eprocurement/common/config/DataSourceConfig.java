package kz.gbk.eprocurement.common.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by abai on 02.08.2016.
 */
@Configuration
@PropertySource({"classpath:application.yml", "classpath:application-production.yml"})
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("org.postgresql.Driver"));
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenderdb");
        dataSource.setUsername("tender");
        dataSource.setPassword("tender");

        return dataSource;
    }
}

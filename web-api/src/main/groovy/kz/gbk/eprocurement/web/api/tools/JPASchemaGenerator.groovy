package kz.gbk.eprocurement.web.api.tools

import org.hibernate.cfg.Configuration
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.hibernate.tool.hbm2ddl.Target
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter

import javax.persistence.Entity

class JPASchemaGenerator {
    static main(args) {
        SchemaExport schemaExport = new SchemaExport(createCfg('org.hibernate.dialect.PostgreSQLDialect'))
        schemaExport.setFormat(true)
        schemaExport.setDelimiter(';')

        schemaExport.execute(Target.SCRIPT, SchemaExport.Type.CREATE)

    }

    static Configuration createCfg(String hibernateDialect) {
        Configuration cfg = new Configuration()

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class))

        for (BeanDefinition bd : scanner.findCandidateComponents("kz.gbk.eprocurement")) {
            String name = bd.getBeanClassName()
            try {
                System.out.println("Added annotated entity class " + bd.getBeanClassName())
                cfg.addAnnotatedClass(Class.forName(name))
            } catch (Exception e) {
                e.printStackTrace()
            }
        }

        cfg.setProperty("hibernate.dialect", hibernateDialect)

        return cfg
    }
}

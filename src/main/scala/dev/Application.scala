package dev

import java.util.Properties

import dev.service.UserService
import javax.inject.{Inject, Named}
import javax.sql.DataSource
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, Bean, ComponentScan, Configuration}
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.{HibernateTransactionManager, LocalSessionFactoryBean}
import org.springframework.transaction.annotation.EnableTransactionManagement


@ComponentScan class Application @Inject()(app : UserService){
  def run = app.test
}

@Configuration @EnableTransactionManagement class Config {
  @Bean def sf: LocalSessionFactoryBean = {
    val sf = new LocalSessionFactoryBean
    sf.setDataSource(ds)
    sf.setPackagesToScan("dev")
    sf.setHibernateProperties(p)
    sf
  }

  @Bean def tx: HibernateTransactionManager = {
    val tx = new HibernateTransactionManager
    tx.setSessionFactory(sf.getObject())
    tx
  }

  def ds: DataSource = {
    val ds = new DriverManagerDataSource
    ds.setDriverClassName("org.h2.Driver")
    ds.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
    ds.setUsername("sa")
    ds.setPassword("sa")
    ds
  }

  def p: Properties = {
    val p = new Properties
    p.setProperty("hibernate.hbm2ddl.auto", "create-drop")
    p.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
    p.setProperty("hibernate.show_sql", "true")
    p
  }
}
object Application extends App { new AnnotationConfigApplicationContext(classOf[Application]).getBean(classOf[Application]).run }
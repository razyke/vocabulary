package org.nice.soft.vocabulary.core.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = ["org.nice.soft.vocabulary.core"])
@EnableJpaRepositories(basePackages = ["org.nice.soft.vocabulary.core.repository"])
class VocabularyCoreConfiguration {
    private val dbPath = "${System.getProperty("user.home")}/vocabulary-db"

    @Bean
    fun jpaVendorAdapter() = HibernateJpaVendorAdapter().apply {
        setDatabase(Database.H2)
        setGenerateDdl(true)
    }

    @Bean
    fun transactionManager(factory: EntityManagerFactory) = JpaTransactionManager(factory)

    @Bean
    fun entityManagerFactory(dataSource: DataSource, jpaVendorAdapter: JpaVendorAdapter) =
        LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            this.jpaVendorAdapter = jpaVendorAdapter
            setPackagesToScan("org.nice.soft.vocabulary.core.model")
        }

    @Bean
    fun dataSource(): DataSource = HikariDataSource(HikariConfig().apply {
        username = "sa"
        password = "sa"
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:$dbPath;DB_CLOSE_DELAY=-1"
        addDataSourceProperty("cachePrepStmts", "true")
        addDataSourceProperty("prepStmtCacheSize", "250")
        addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    })

}

package com.moneyflow.gateway.configurations;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class FlywayConfig {

	@Autowired
	private DataSource dataSource;

	@Bean(initMethod = "migrate")
	public Flyway flyway() {

		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("classpath:migration/commons")
				.baselineDescription("Initial version testing mode")
				.baselineOnMigrate(true)
				.load();

		flyway.migrate();

		return flyway;
	}

}
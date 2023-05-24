package com.moneyflow.moneyflow.configurations;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class FlywayConfig {

	@Autowired
	private DataSource dataSource;

	@Bean(initMethod = "migrate")
	public Flyway flyway() {

		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("classpath:db/migration/commons", "classpath:db/migration/inserts", "classpath:db/migration/trigger")
				.baselineDescription("Initial version")
				.load();

		flyway.repair();
		flyway.migrate();

		return flyway;
	}
}
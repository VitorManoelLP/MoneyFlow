package com.moneyflow.gateway.configurations;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class FlywayConfig {

	@Autowired
	private final DataSourceProperties dataSourceProperties;

	public FlywayConfig (DataSourceProperties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {

		Flyway flyway = Flyway.configure()
				.dataSource(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword())
				.locations("classpath:migration/commons", "classpath:migration/trigger")
				.baselineDescription("Initial version")
				.load();

		flyway.repair();
		flyway.migrate();

		return flyway;
	}
}
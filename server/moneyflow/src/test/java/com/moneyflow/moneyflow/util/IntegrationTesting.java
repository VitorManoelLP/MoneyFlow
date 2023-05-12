package com.moneyflow.moneyflow.util;

import com.moneyflow.moneyflow.configurations.FlywayConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Transactional
@Rollback
@AutoConfigureMockMvc
@Import(FlywayConfig.class)
@DataJpaTest
@ActiveProfiles
public @interface IntegrationTesting {

	@AliasFor(annotation = ActiveProfiles.class, attribute = "profiles") String[] profiles() default "test";

}

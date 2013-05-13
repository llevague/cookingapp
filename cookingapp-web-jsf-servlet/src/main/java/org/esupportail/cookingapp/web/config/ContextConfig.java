package org.esupportail.cookingapp.web.config;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.cookingapp.domain.config.DomainConfig;
import org.esupportail.cookingapp.web.utils.JsfMessagesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@Import({ ExceptionHandlingConfig.class, DomainConfig.class,
		ControllerConfig.class, ConverterConfig.class })
public class ContextConfig {

	@Bean(name = "app_context")
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		final Resource[] resources = new Resource[] {
				new ClassPathResource("/properties/defaults.properties"),
				new ClassPathResource("/properties/config.properties") };
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}

	@Bean
	public JsfMessagesUtils jsfMessagesUtils() {
		return JsfMessagesUtils.getInstance();
	}
}

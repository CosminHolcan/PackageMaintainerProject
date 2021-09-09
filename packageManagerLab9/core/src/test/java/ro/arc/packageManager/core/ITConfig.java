package ro.arc.packageManager.core;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.arc.packageManager.core.config.JPAConfig;

@Configuration
@ComponentScan(value = "ro.arc.packageManager.core",
        excludeFilters = {@ComponentScan.Filter(value = {JPAConfig.class}, type = FilterType.ASSIGNABLE_TYPE)})
@Import({JPAConfigIT.class})
@PropertySources({@PropertySource(value = "classpath:db-h2.properties")})
public class ITConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

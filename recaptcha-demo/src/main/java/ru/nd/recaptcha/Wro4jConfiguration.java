package ru.nd.recaptcha;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;

import javax.servlet.Filter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

@Configuration
public class Wro4jConfiguration {
    // It bean knows that we need filter WroFilter and this filter callback by path /wro/*
    @Bean
    public FilterRegistrationBean webResourceOptimizer(Environment env) {
        // equivalent of web.xml notation <--
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(newWroFilter(env));
        registration.addUrlPatterns("/wro/*");
        registration.setName("WebResourceOptimizer"); // like beans name
        registration.setOrder(1);
        // -->

        return registration;
    }

    private Filter newWroFilter(@NonNull Environment env) {
        ConfigurableWroFilter filter = new ConfigurableWroFilter();
        Properties props = buildWroProps(env);
        filter.setProperties(props);
        filter.setWroManagerFactory(new Wro4jCustomXmlModelManagerFactory(props));

        return filter;
    }

    private Properties buildWroProps(@NonNull Environment env) {
        Properties props = new Properties();
        Stream.of(ConfigConstants.values())
                .map(c -> c.name())
                .forEach(name -> addProperty(env, props, name));
        OTHER_WRO_PROPS.forEach(name -> addProperty(env, props, name));

        return props;
    }

    private void addProperty(@NonNull Environment env, @NonNull Properties props, @NonNull String name) {
        String value = env.getProperty("wro." + name);
        if (value != null) {
            props.put(name, value);
        }
    }

    private static final List<String> OTHER_WRO_PROPS = ImmutableList.of(
            ConfigurableProcessorsFactory.PARAM_PRE_PROCESSORS,
            ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS);
}

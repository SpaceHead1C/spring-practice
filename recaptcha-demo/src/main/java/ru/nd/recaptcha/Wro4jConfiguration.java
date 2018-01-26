package ru.nd.recaptcha;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.http.WroFilter;

@Configuration
public class Wro4jConfiguration {
    // It bean knows that we need filter WroFilter and this filter callback by path /wro/*
    @Bean
    public FilterRegistrationBean webResourceOptimizer() {
        // equivalent of web.xml notation <--
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WroFilter());
        registration.addUrlPatterns("/wro/*");
        registration.setName("WebResourceOptimizer"); // like beans name
        registration.setOrder(1);
        // -->

        return registration;
    }
}

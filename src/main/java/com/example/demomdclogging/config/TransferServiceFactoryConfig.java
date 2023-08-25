package com.example.demomdclogging.config;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demomdclogging.business.TransferServiceFactory;

@Configuration
public class TransferServiceFactoryConfig {
    @Bean
    public ServiceLocatorFactoryBean slfbForTransferServiceFactory() {
        ServiceLocatorFactoryBean slfb = new ServiceLocatorFactoryBean();
        slfb.setServiceLocatorInterface(TransferServiceFactory.class);
        return slfb;
    }
}

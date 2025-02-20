package com.enigmacamp.mastermenu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.service.MidtransSnapApi;


@Configuration
public class MidtransConfig {
    
    @Value("${midtrans.serverkey}")
    private String serverKey;

    @Value("${midtrans.clientkey}")
    private String clientKey;

    @Bean
    public MidtransSnapApi midtransApi(){
        Config configOptions = Config.builder()
                    .setIsProduction(false)
                    .setServerKey(serverKey)
                    .setClientKey(clientKey)
                    .build();
        
         return new ConfigFactory(configOptions).getSnapApi();
    }

   
}

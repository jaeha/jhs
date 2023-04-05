package com.jtech.jhs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CustomizationBean implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${jhs.base.path}")
    private String basePath;

    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
       // container.setPort(8080);
       // container.setContextPath("/files");
     //   Map<String, String> params = new HashMap<>();
     //   params.put("listings", "false"); 
     //   container.setInitParameters(params); 
        container.setDocumentRoot(new File(basePath));
    }
}
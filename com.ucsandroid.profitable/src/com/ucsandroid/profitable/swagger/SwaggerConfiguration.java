package com.ucsandroid.profitable.swagger;

import javax.annotation.PostConstruct;

/*
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
*/

/**
 * Configuration bean to set up Swagger.
 */
//@Component
public class SwaggerConfiguration {

    private String resourcePackage = "com.ucsandroid.profitable.swagger";

    private String basePath = "http://127.0.0.1:8080/SpringWithSwagger/rest";

    private String apiVersion = "1.0";

    @PostConstruct
    public void init() {
        //final ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
        //scanner.setResourcePackage(resourcePackage);

        //ScannerFactory.setScanner(scanner);
        //ClassReaders.setReader(new DefaultJaxrsApiReader());

        //final SwaggerConfig config = ConfigFactory.config();
        //config.setApiVersion(apiVersion);
        //config.setBasePath(basePath);
    }

    public String getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
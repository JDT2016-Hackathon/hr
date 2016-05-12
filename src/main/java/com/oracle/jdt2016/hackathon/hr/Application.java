/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr;

//import java.net.URL;
//import java.net.URLClassLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        System.out.println("classpath : " + System.getProperty("java.class.path"));
//        URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//        System.out.println("---- BEGIN: URLs of URLClassLoader ----");
//        for (URL url : loader.getURLs()) {
//            System.out.println(url);
//        }
//        System.out.println("---- END: URLs of URLClassLoader ----");
        EntityManagerUtils.initialize();
    }

}

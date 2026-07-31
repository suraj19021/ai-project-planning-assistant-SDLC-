package com.aiplanner.aiprojectplanner.debug;

import org.hibernate.Version;
import org.hibernate.type.SqlTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HibernateDebug {

    @GetMapping("/debug/hibernate")
    public String debug() {

        return "Hibernate Version : " + Version.getVersionString()
                + "\nVECTOR SqlType : " + SqlTypes.VECTOR
                + "\nLoaded From : "
                + Version.class.getProtectionDomain()
                .getCodeSource()
                .getLocation();
    }
}
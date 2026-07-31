package com.aiplanner.aiprojectplanner.debug;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

@RestController
public class JdbcDebug {

    @GetMapping("/debug/jdbc")
    public String jdbc() {

        StringBuilder sb = new StringBuilder();

        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {

            Driver d = drivers.nextElement();

            sb.append(d.getClass().getName())
                    .append("\n")
                    .append(d.getClass()
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation())
                    .append("\n\n");
        }

        return sb.toString();
    }
}
package com.cv.aircraft.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UtilController {

    @Value("${build.timestamp}")
    private String buildTimestampVersion;

    @RequestMapping("/ping")
    public String ping() {
        return "App is up";
    }

    @RequestMapping("/version")
    public String version() {
        return buildTimestampVersion;
    }
}

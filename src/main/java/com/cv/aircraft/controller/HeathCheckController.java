package com.cv.aircraft.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HeathCheckController {

    @RequestMapping("/ping")
    public String ping() {
        return "App is up";
    }
}

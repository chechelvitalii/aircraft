package com.cv.aircraft.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class RequestUtils {

    public static HttpEntity<String> defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; Touch; rv:11.0) like Gecko");
        return new HttpEntity("parameters", headers);
    }
}

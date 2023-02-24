package com.tukorea.cogTest.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ResponseUtil {
    /**
     * ResponseEntity body를 만들어주는 메소드
     * @param statusCode : HttpStatus
     * @param msg : String
     * @param results : Map&lt;String, Object&gt;
     * @return ConcurrentHashMap&lt;String, Object&gt;
     */
    public static ConcurrentHashMap<String, Object> setResponseBody(HttpStatus statusCode, String msg, Map<String, Object> results) {
        ConcurrentHashMap<String, Object> body= new ConcurrentHashMap<>();
        body.put("statusCode", statusCode);
        body.put("msg", msg);
        if(results!=null){
            body.put("results", results);
        }
        return body;
    }
    public static ResponseEntity<Map<String, Object>> setWrongResponse(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(setResponseBody(HttpStatus.BAD_REQUEST, "Wrong Request", null), HttpStatus.BAD_REQUEST);
    }

}

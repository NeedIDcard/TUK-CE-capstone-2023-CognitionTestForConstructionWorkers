package com.tukorea.cogTest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class JsonResponse {
    private String msg;
    private int statusCode;
    private Map<String, Object> results;
}

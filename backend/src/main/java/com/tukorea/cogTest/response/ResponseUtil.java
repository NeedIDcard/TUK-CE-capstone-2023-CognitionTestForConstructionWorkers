package com.tukorea.cogTest.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ResponseUtil {

    private ResponseUtil(){}
    /**
     * ResponseEntity body를 만들어주는 메소드
     * @param statusCode : HttpStatus
     * @param msg : String
     * @param results : Map&lt;String, Object&gt;
     * @return ConcurrentHashMap&lt;String, Object&gt;
     */
    public static ConcurrentHashMap<String, Object> setResponseBody(HttpStatus statusCode, String msg, Map<String, Object> results) {
        ConcurrentHashMap<String, Object> body= new ConcurrentHashMap<>();
        body.put("statusCode", statusCode.value());
        body.put("msg", msg);
        if(results!=null){
            body.put("results", results);
        }
        return body;
    }
    public static ResponseEntity<Map<String, Object>> returnWrongRequestErrorResponse(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(
                setResponseBody(HttpStatus.BAD_REQUEST, e.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map<String, Object>> setUnAuthenticatedErrorResponse(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(
                setResponseBody(HttpStatus.UNAUTHORIZED, e.getMessage(), null),
                HttpStatus.UNAUTHORIZED
        );
    }

    public static ResponseEntity<Map<String, Object>> setInternalErrorResponse(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(
                setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity<Map<String, Object>> setInternalErrorResponse(){
        return new ResponseEntity<>(
                setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Map<String, Object>> setUnauthorizedResponse(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(
                setResponseBody(HttpStatus.UNAUTHORIZED,
                        e.getMessage(),
                        null),
                HttpStatus.UNAUTHORIZED
        );
    }

    public static void setJsonResponse(HttpServletResponse response, Exception exception, ObjectMapper objectMapper) throws IOException {
        JsonResponse body = new JsonResponse(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(body));
        writer.flush();
    }
    public static void writeObjectOnResponse(HttpServletResponse response,
                                      Map<String, Object> result,
                                      ObjectMapper objectMapper) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(result));
        writer.flush();
    }

    public static void setRestResponseHeader(HttpServletResponse response){
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }
}

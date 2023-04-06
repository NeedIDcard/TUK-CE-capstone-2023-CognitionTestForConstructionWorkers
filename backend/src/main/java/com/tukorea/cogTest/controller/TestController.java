package com.tukorea.cogTest.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/admin/login")
    public String test(){
        return "login";
    }
    @GetMapping("/subject/login")
    public String test2(){
        return "hello";
    }
    @GetMapping("/")
    public String test3(){
        return "hell";
    }
}

package com.angular.todo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//used for the index Controller of the api
@RestController
public class HomeController {

    //injecting the app version from the application.properties file
    @Value("${app.version}")

    private String appVersion;
    private String appName = "Todo Restful API";

    //creating a get request
    @GetMapping
    @RequestMapping("/")
    public Map getStatus(){
        //instantiating a new hash map
        Map map = new HashMap<String, String>();
        map.put("app-name", appName);
        map.put("app-version", appVersion);

        return map;
    }
}

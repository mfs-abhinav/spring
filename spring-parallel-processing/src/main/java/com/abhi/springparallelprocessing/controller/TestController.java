package com.abhi.springparallelprocessing.controller;

import com.abhi.springparallelprocessing.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "TestController")
public class TestController {

    @Autowired
    TestService testService;

    @ApiOperation(value = "test welcome", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() throws InterruptedException {
        System.out.println("main thread starts");
        testService.print();
        System.out.println("main thread end");
        return "success";
    }

}

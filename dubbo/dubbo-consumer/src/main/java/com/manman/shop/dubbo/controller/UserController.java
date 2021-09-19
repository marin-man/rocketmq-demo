package com.manman.shop.dubbo.controller;

import com.manman.shop.service.IUService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: UserController
 * @Author manman
 * @Description
 * @Date 2021/9/19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference
    private IUService iuService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return iuService.sayHello("aa");
    }
}

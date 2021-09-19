package com.manman.shop.dubbo.service;

import com.manman.shop.service.IUService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Title: UserServiceImpl
 * @Author manman
 * @Description
 * @Date 2021/9/19
 */
@DubboService(interfaceClass = IUService.class)
public class UserServiceImpl implements IUService {
    public String sayHello(String name) {
        return "hello: " + name;
    }
}

package com.cathy.resiliiencedemo.services.impl;

import com.cathy.resiliiencedemo.services.HelloWorldService;
import org.springframework.stereotype.Service;

/**
 * 创建：陈敬
 * 日期：2018/12/5.
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public String sayHello() {
        throw  new RuntimeException();
//        return "hello";
    }

    @Override
    public String sayHelloWithName(String name) {
        return "hello,"+name;
    }
}

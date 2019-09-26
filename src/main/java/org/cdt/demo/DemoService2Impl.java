package org.cdt.demo;

import com.alibaba.dubbo.config.annotation.Service;

@Service
//@Component
public class DemoService2Impl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello222 " + name;
    }

    @Override
    public String sayHello(Integer name) {
        return "sayhello interger";
    }

    @Override
    public String sayHello2(Integer name) {
        return null;
    }
}
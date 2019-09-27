package org.cdt.demo;

import com.alibaba.dubbo.config.annotation.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class DemoService2Impl implements DemoService {
    @Override
    public String sayHello(String name) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return "ip: " + ip + " Hello2" + name;
        } catch (UnknownHostException e) {
            return "UnknownHostException Hello2";
        }
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
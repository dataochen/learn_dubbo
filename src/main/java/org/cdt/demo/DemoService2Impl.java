package org.cdt.demo;

import com.alibaba.dubbo.config.annotation.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class DemoService2Impl implements DemoService,Cloneable  {
    @Override
    public String sayHello(String name) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return "ip: " + ip + " Hello2" + name;
        } catch (UnknownHostException e) {
            return "UnknownHostException Hello2";
        }
    }

    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String sayHello(Integer name) {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayhello interger";
    }

    @Override
    public String sayHello2(Integer name) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}